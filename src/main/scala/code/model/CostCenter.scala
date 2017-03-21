package code
package model

import code.util._

import net.liftweb._
import mapper._
import http._
import SHtml._
import util._
import _root_.java.math.MathContext;
import scalendar._
import Month._
import Day._
import net.liftweb.common.{ Box, Full, Empty }

import java.util.Date

class CostCenter extends TreeObject with Audited[CostCenter] with PerCompany with IdPK with PerUnit with CreatedUpdated with CreatedUpdatedBy with NameSearchble[CostCenter] with ActiveInactivable[CostCenter]{
  def getSingleton = CostCenter
  override def updateShortName = false
  object obs extends MappedPoliteString(this, 255)
  object parent_? extends MappedBoolean(this) {
    override def dbColumnName = "isparent"
  }
  def nameStatus =  {
    if (this.isInactive) {
      name.is + " (* inativa)"
      } else {
        name.is
      }
    }
    private def _fullName =  {
      def parrentNames = parentReg.obj match {
        case Full(parent) => parent.name.is+" -> "
        case _ => "";
      }
      parrentNames+name.is
    }
    object fullName extends MappedPoliteString(this,255) with LifecycleCallbacks {
      override def beforeSave() {
        super.beforeSave;
        this.set(BusinessRulesUtil.toCamelCase(_fullName))
      }      
    }  
    object parentReg extends MappedLongForeignKey(this, CostCenter) {
      override def dbIndexed_? = true
    }
    object minTreeNode extends MappedLong(this) {
      override def defaultValue = 1
    }
    object maxTreeNode extends MappedLong(this) {
      override def defaultValue = 2
    }
    object orderInReport extends MappedInt(this)

    def hasChilds = directyChilds.size > 0

    def saveWithoutUpdateTree = {
      super.save
      this
    }
    override def save = {
      CostCenter.validateCostCenter(this)
      val r = super.save
      CostCenter.updateTreeNodes
      r
    }

    lazy val directyChilds = CostCenter.findAllInCompany(By(parentReg, this.id.is))
    def minTree(value:Long):TreeObject = minTreeNode(value);
    def maxTree(value:Long):TreeObject= maxTreeNode(value);
    def minTree:Long = minTreeNode.is;
    def maxTree:Long = maxTreeNode.is;

    def costcentersCount: Long = AccountPayable.count(By(AccountPayable.costCenter, this))
    def hasAccount: Boolean = costcentersCount > 0l
    override def delete_! = {
      if (hasAccount) {
        throw new RuntimeException("Existem lançamentos para esse centro de custo, ele não pode ser excluído!")
      }
      super.delete_!;
    }
  }

  object CostCenter extends CostCenter with LongKeyedMapperPerCompany[CostCenter] with OnlyActive[CostCenter] {
    val SQL_REPORT_GRAPHIC = """
    select cc.name,sum(ap.value) from accountpayable ap
    inner join costcenter cc on(cc.id = ap.costcenter)
    where ap.company  =?
    and ap.duedate between ? and ?
    group by cc.name;"""
    val SQL_TREE = """
    select id,name, mintreenode, maxtreenode, parentreg,isparent
    from 
    costcenter ac
    where company=?
    and status = 1
    order by orderinreport
    """

    type Validation = (CostCenter) => Unit

  /**
   * Validate has child
   */
/*  def vhc = (c: CostCenter) => {
    if (!c.parent_?.is && c.hasChilds)
      throw new RuntimeException("Contas que possuem filhas não podem ser desmarcadas como pai!")
  }
  */
  /**
   * Validate parent parent
   */
   def vpp = (c: CostCenter) => {
    c.parentReg.obj match {
      case Full(pa) => if (pa.saved_? && !pa.parent_?.is)
      throw new RuntimeException("Conta pai não aceita conta filha!")
      case _ =>
    }
  }

  /**
   * Validate parent not this
   */
   def vpt = (c: CostCenter) => {
    c.parentReg.obj match {
      case Full(pa) =>
      if (pa.saved_? && pa.id.is == c.id.is)
      throw new RuntimeException("Conta não pode ser pai dela mesma!")
      case _ =>
    }
  }

  /**
   * Validate parent not child of this
   */
   def vpncot = (c: CostCenter) => {
    c.parentReg.obj match {
      case Full(pa) => {
        if (pa.saved_? && (pa.minTreeNode.is > c.minTreeNode.is && pa.minTreeNode.is < c.maxTreeNode.is))
        throw new RuntimeException("Conta pai não pode ser filha de uma conta filha da conta atual")
      }
      case _ =>
    }
  }

  def validateCostCenter(acc: CostCenter) {
    listValidation.foreach(_(acc))
  }
  /**
   * Validate has child
   */
   def vhc = (c: CostCenter) => {
    if (!c.parent_?.is && c.hasChilds)
    throw new RuntimeException("Contas que possuem filhas não podem ser desmarcadas como pai!")
  }  

  /**
   * Validate parent desabled when has childs enabled
   */

   def vphce = (c: CostCenter) => {
    if(c.isInactive && c.hasChilds){
      throw new RuntimeException("Não é possível inativar conta que possui filhos ativos")
    }
  }
  def listValidation: List[Validation] = vpp :: vpt :: vpncot :: vhc :: vphce :: Nil

  def updateTreeNodes = {
    TreeModelsUtil.treatTree(findAllInCompanyWithInactive(OrderBy(createdAt, Ascending), BySql("parentReg IS NULL", IHaveValidatedThisSQL("", ""))), 1)
  }
}



