package code
package model

import code.util._

import net.liftweb._
import mapper._
import http._
import SHtml._
//import util._
import _root_.java.math.MathContext;
import scalendar._
import Month._
import Day._
import net.liftweb.common.{ Box, Full, Empty }

import java.util.Date
import java.util.Calendar

class Account extends Audited[Account] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy with NameSearchble[Account] with ActiveInactivable[Account]{
  def getSingleton = Account
  override def updateShortName = false
  object value extends MappedCurrency(this) with LifecycleCallbacks {
    override def beforeSave() {
      super.beforeSave;
      if(!balanceControl_?.is){
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
  object bank extends MappedLong(this)

  object allowCashierOut_? extends MappedBoolean(this) {
    override def defaultValue = false
    override def dbColumnName = "allowCashierOut"
  }

  object balanceControl_? extends MappedBoolean(this) {
    override def defaultValue = true
    override def dbColumnName = "balancecontrol"
  }

  private def saveWithoutHistory() = {
    super.save
  }
  private def createDefaultHistory(movementValue:Double, obs:String) = {
  AccountHistory.create
        .company(this.company)
        .account(this)
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

  override def delete_! = {
      if(AccountPayable.count(By(AccountPayable.account,this.id)) > 0){
          throw new RuntimeException("Existe lançamento financeiro para esta conta! ")
      }
      if(PaymentType.count(By(PaymentType.defaltAccount,this.id)) > 0){
          throw new RuntimeException("Existe forma de pagamento faturada nesta conta! ")
      }
      super.delete_!
  }

}

object Account extends Account with LongKeyedMapperPerCompany[Account] with OnlyActive[Account]