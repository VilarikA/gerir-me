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
import java.util.Calendar

class Recurrence extends Audited[Recurrence] with IdPK with CreatedUpdated with PerCompany 
  with FinancialMovement {
  def getSingleton = Recurrence
  lazy val WEEKLY = 1

  lazy val MONTHLY = 2

  lazy val YEARLY = 3

  object typeRecurrence extends MappedInt(this)
  object generatedParcels extends MappedInt(this){
    override def defaultValue = 1
  }
  object startDate extends EbMappedDate(this)
  object endDate extends EbMappedDate(this)
  object lastExecuted extends EbMappedDate(this) {
    override def defaultValue = startDate.is
  }
  def nextParcel = {
    generatedParcels(generatedParcels.is+1)
    generatedParcels.is
  }
  object isFullExecuted extends MappedBoolean(this) {
    override def dbIndexed_? = true
    override def defaultValue = false
  }

  def movementsBigerThan(parcel:Int) 
                = AccountPayable
                .findAllInCompany(
                  By(AccountPayable.recurrence, this), 
                  By_>(AccountPayable.parcelNum, parcel), 
                  OrderBy(AccountPayable.dueDate, Ascending)
                )
  lazy val movements = AccountPayable.findAllInCompany(By(AccountPayable.recurrence, this), OrderBy(AccountPayable.dueDate, Ascending))

  def updateByAccount(source: AccountPayable) {
      Recurrence.updateByFinancial(source, this)
      this.save
      movementsBigerThan(source.parcelNum.is).foreach( (ap) => {
        Recurrence.updateByFinancial(this, ap)
        ap.save
      })

  }

  def deleteByAccount(source: AccountPayable) {
      movementsBigerThan(source.parcelNum.is).foreach( (ap) => {
        ap.delete_!
      })
      val ap = AccountPayable.findByKey(source.id).get
      ap.delete_!
      this.lastExecuted(endDate)
      this.isFullExecuted(true)
      this.save
  }

}

object Recurrence extends Recurrence with LongKeyedMapperPerCompany[Recurrence] with OnlyCurrentCompany[Recurrence] {
  def execureRecorenc(to: Date) {
    // and date(updatedAt) < date(?)
    execureRecorenc(Recurrence.findAllInCompany(
      BySql("date(lastexecuted) <= date(?) and isfullexecuted=false", IHaveValidatedThisSQL("lastexecuted", "01-01-2012 00:00:00"), to)), to)
  }
  def #:#(d1: Date, d2: Date) = {
    Scalendar(d1.getTime()) to Scalendar(d2.getTime())
  }
  def execureRecorenc(rcs: List[Recurrence], toDate: Date) {
    // limita a recorrencia pq nenguinho erra a data fim e gera trocentos lançamentos
    val dateAux = Project.strToDateOrToday ("31/12/2035");
    val toDateAux = if (toDate > dateAux) {
        dateAux
    } else {
        toDate
    }

    DB.use(DefaultConnectionIdentifier) {
      conn =>
        try {
          rcs.foreach((r: Recurrence) => {
            lazy val dateToUser:Date = if (toDateAux.getTime >= r.endDate.is.getTime) {
                r.endDate.is 
              } else {
                val c = Calendar.getInstance
                val sd = Calendar.getInstance
                c.setTime(toDateAux)
                sd.setTime(r.endDate.is)
                c.set(Calendar.DAY_OF_MONTH, sd.get(Calendar.DAY_OF_MONTH))
                c.getTime
              }


            val duration = #:#(r.lastExecuted.is, dateToUser)

            var apAuxId = 0l;

            duration.by(division(r)).foreach((d) => {
              // para nao gerar duplicado
              val apcount = AccountPayable.countApByRecurrence (r, d.start)
              if (apcount < 1) {
                val ap = createMovementByRecurenc(r)
                  .dueDate(d.start)
                  .parcelNum(r.nextParcel)
                  .parcelTot(r.parcelTot);
                ap.save
                apAuxId = ap.id.is;
              } else {
                val ap = AccountPayable.findAllInCompany (
                          By(AccountPayable.recurrence, r.id.is), 
                          By(AccountPayable.dueDate, d.start))(0)
                apAuxId = ap.id.is
              }

            })
            if (dateToUser.getTime() >= r.endDate.is.getTime()) {
              r.isFullExecuted(true)
            }
            if (apAuxId != 0) {
              val ap = AccountPayable.findByKey(apAuxId).get
              // antes setava aqui a data fim da projecao, qdo recomeçava data erro
              r.lastExecuted(ap.dueDate).save
            }
          })

        } catch {
          case e: Exception => {
            conn.rollback
            throw e
          }
        }
    } 
    
  }

  def division(r: Recurrence) = {
    if (r.typeRecurrence.is == MONTHLY) {
      1.month
    } else if (r.typeRecurrence.is == WEEKLY) {
      1.week
    } else {
      1.year
    }
  }

  def createMovementByRecurenc(r: Recurrence) = {
    AccountPayable.create
      .typeMovement(r.typeMovement)
      .value(r.value)
      .company(r.company)
      .paid_?(false)
      .recurrence(r)
      .category(r.category)
      .invoice(r.invoice)
      .account(r.account)
      .user(r.user)
      .unit(r.unit)
      .obs(r.obs)
      .amount(r.amount)
      .costCenter(r.costCenter)
      .paymentType(r.paymentType)

  }

  def updateByFinancial(source: FinancialMovement, dest:FinancialMovement) {
      dest.typeMovement(source.typeMovement.is)
      dest.value(source.value.is)
      dest.category(source.category.is)
      dest.invoice(source.invoice.is)
      dest.account(source.account.is)
      dest.user(source.user.is)
      dest.unit(source.unit.is)
      dest.obs(source.obs.is)
      dest.amount(source.amount.is)
      dest.costCenter(source.costCenter.is)
      dest.paymentType(source.paymentType.is)
  }  
}
