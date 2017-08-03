package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import code.util._
import code.service._
import java.util.Calendar
import net.liftweb.common.{Box,Full}

class AccountCompanyUnit extends Audited[AccountCompanyUnit] with PerCompany with IdPK 
    with CreatedUpdated with CreatedUpdatedBy 
    with ActiveInactivable[AccountCompanyUnit] {
    def getSingleton = AccountCompanyUnit
    object account extends MappedLongForeignKey(this,Account)
    object unit extends MappedLongForeignKey(this,CompanyUnit) 
    object obs extends MappedPoliteString(this,255)
    object value extends MappedCurrency(this) with LifecycleCallbacks {
        override def beforeSave() {
          super.beforeSave;
          if(!balanceControl_?){
            this.set(0.00);
          }
        }
    }

    object lastValue extends MappedCurrency(this) with LifecycleCallbacks {
        override def defaultValue = fieldOwner.value
        override def beforeSave() {
          super.beforeSave;
          this.set(this.defaultValue);
        }
    }
    object bank extends MappedLong(this);
    def balanceControl_? : Boolean = {
        account.obj match {
            case Full(t) => t.balanceControl_?
            case _ => false
        }
    }

  private def saveWithoutHistory() = {
    super.save
  }
  private def createDefaultHistory(movementValue:Double, obs:String) = {
  AccountHistory.create
        .company(this.company)
        .account(this.account)
        .currentValue(this.value.is)
        .description(obs)
        .value(movementValue)        
  }
  def valueChange:Boolean = this.value.is != this.lastValue.is
  override def save() = {
    if (valueChange) {
      createDefaultHistory(this.value.is-this.lastValue.is, "Alt Saldo Conta").save
    }
    val result = super.save    
    result    
  }

  private def _register(accountP: AccountPayable, registerValue: Double, obs: String = "", isRealValue:Boolean = false) = {
    DB.use(DefaultConnectionIdentifier) {
      conn =>
        try {
            val realValue  = if (accountP.typeMovement.is == AccountPayable.IN || isRealValue ) {
              registerValue
            } else {
              registerValue*(-1.00)
            }
            this.value(this.value.is + realValue)
            this.saveWithoutHistory
            accountP.debted_?(true)
            createDefaultHistory(realValue, obs).accountPayable(accountP).save
        } catch {
          case e: Exception => {
            conn.rollback
            throw e
          }
        }
    }
  }

  def removeRegister(accountP: AccountPayable, obs: String = "") = {
    _register(accountP, accountP.lastValue.is*(-1), obs, true)
  }

  def registerDiference(accountP: AccountPayable) = {
    if (accountP.realValue != accountP.lastValue.is ) {
      _register(accountP, accountP.realValue - accountP.lastValue, "Alt Valor Lanç", true)
    }
  }
  def register(accountP: AccountPayable) = {
    if (accountP.typeMovement.is == AccountPayable.IN) {
        _register(accountP, accountP.value, "Crédito")
    } else {
        _register(accountP, accountP.value, "Débito")
    }
//    _register(accountP, accountP.value, "Lançamento")
  }

}

object AccountCompanyUnit extends AccountCompanyUnit with LongKeyedMapperPerCompany[AccountCompanyUnit] with OnlyCurrentCompany[AccountCompanyUnit]{
    //override def dbTableName = "bpaccount"
}

