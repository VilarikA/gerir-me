package code
package util

import net.liftweb._ 
import net.liftweb.http._
import net.liftweb.sitemap.Loc._
import net.liftweb.util.Helpers._
import net.liftweb.util._
import net.liftweb.common._
import mapper._ 
import S._
import code.model._
import scala.xml._
import scala.collection.JavaConverters._

import java.io.{File,FileInputStream}
import java.util.Calendar;
import java.util.TimeZone;
import scala.io.Source._

import InventoryMovement._
import java.util.Date


object ContaAzulUtil extends net.liftweb.common.Logger {
  val START_DETAILS = 2
  val MONTHLY_ID = (1, 10)
  val PAYMENT_DATE = (1, 10)
  val EFETIVE_DATE = (1, 10)
  val PAID_VALUE = (1, 10)
  val INCREASE_VALUE = (1, 10)
  val LIQUID_VALUE = (1, 10)



  def execute(file:File){
    val lines = fromFile(file).getLines.toList
    val separator:String = if (lines(1).count(_ == '.') >
      lines(1).count(_ == ';')) {
        "."
      } else if (lines(1).count(_ == ';') >
      lines(1).count(_ == ',')) {
        ";"
      } else {
        ","
      }
    val details = for(i <- 1 to (lines.size)-1 ) yield {
      println ("vaiii ====== " + lines(i) + " === " + i + " serapardor " + separator)
      factory(i, lines, separator)
    }
    details.map((d) => {
      removeInventory(d.customer.id, d.purchasePrice, 0l, d.price, d.amount, 
        d.product, "venda importada", " sem doc", d.unit, 
        d.iCause, d.today)
      if (d.product.salePrice == 0 && d.amount != 0) {
        d.product.salePrice (d.price / d.amount).save
      }

    })
    // inserir produtos no offsaleproduct
    UtilSqlOffSaleProdutcs.updateOffSaleProdutcs(AuthUtil.company);
  }
  def removeInventory(bp:Long, purchaseprice:Double, treatmentdetail:Long, price:Double, amount:Float, product:Product, obs:String, invoice:String, unit:CompanyUnit, inventorycause:InventoryCause, efetivedate:Date) {
    //AuthUtil.company.inventoryCauseSale.obj.get
    // remove(amount) item product company(AuthUtil.company) obs(obs) invoice(invoice) cause(AuthUtil.company.inventoryCauseSale.obj.get) efetiveDate(new Date()) from unit
    remove(amount) item product company(AuthUtil.company) purchasePrice (purchaseprice) treatment_detail (treatmentdetail) totalSalePrice (price) obs(obs) business_pattern (bp) invoice(invoice) cause(inventorycause) efetiveDate(efetivedate) from unit
  }

  def factory(i:Int, lines:List[String], separator:String):DetailContaAzul ={
    //val dataLine = i+1
    var index = lines(i).indexOfSlice(separator)
    var index_pr = lines(i).indexOfSlice(separator, index+1)
    var index_qt = lines(i).indexOfSlice("R$", index_pr+1)
    var index_ul = lines(i).indexOfSlice("R$", index_qt+1)
    DetailContaAzul(
            lines(i).substring(0, index),
            lines(i).substring(index + 1, index_pr),
            lines(i).substring(index_pr +1, index_qt-1),
            lines(i).substring(index_ul+3, ((lines(i).length)))
          )
  }
}


case class DetailContaAzul(
                  private val _customer:String,
                  private val _product:String, 
                  private val _qtty:String, 
                  private val _value:String){


  println("vaiii >>>>>>> " + _customer + " >>> " + _product)
  println("vaiii >>>>>>> qt " + _qtty + " >>>> val " + _value)

  val today = Project.date_format_db.parse(Project.date_format_db.format(new Date()));
  def iCause = AuthUtil.company.inventoryCauseSale.obj.get
  def unit = AuthUtil.unit
  def purchasePrice = 0.0

  def price = BusinessRulesUtil.clearStrNum (_value).toDouble;

  def amount:Float = BusinessRulesUtil.clearStrNum (_qtty).toFloat;

  def product = Product.findByName (_product);

  def customer = Customer.findByName (_customer);

/*
    def cnabDataToDate(dateStr:String) = {
      val date = Calendar.getInstance
      date.set(Calendar.DATE, dateStr.substring(0, 2).toInt)
      date.set(Calendar.MONTH, dateStr.substring(2, 4).toInt)
      date.set(Calendar.YEAR, dateStr.substring(4, 8).toInt)
      date.set(Calendar.HOUR, 0)
      val tz = TimeZone.getTimeZone("GMT");
      date.setTimeZone(tz);      
      date.getTime
    }

    def valueCnabToValue(value:String) = {
      value.trim.toFloat / 100
    }
*/
}

object UtilSqlOffSaleProdutcs {
  val SQL_UPDATE_OFFSALEPRODUCT = """
  insert into offsaleproduct
  select 1, nextval('offsaleproduct_id_seq'), null, pr.id, os.id, os.company, 1,1,now(),now(),
  1,0, null, pr.saleprice, 0,0,false, '', '', indic1, indic2, indic3, indic4, indic5
  from offsale os 
  left join product pr on pr.company = ? and productclass = 1 and lower (pr.name) not like '%exemplo%'
  where os.company = ?
  and 0 >= (select count(*) from offsaleproduct op1 where op1.product = pr.id and op1.offsale = os.id);
  """
  def updateOffSaleProdutcs(company:Company) {
    DB.runUpdate(SQL_UPDATE_OFFSALEPRODUCT, company.id.is::company.id.is::Nil)
  }

}
