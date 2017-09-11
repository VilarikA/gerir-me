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

import java.util.Date


object ContactsUtil extends net.liftweb.common.Logger {

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
      //println ("vaiii ====== " + lines(i) + " === " + i + " serapardor " + separator)
      factory(i, lines, separator)
    }
    details.map((d) => {
/*
      removeInventory(d.customer.id, d.purchasePrice, 0l, d.price, d.amount, 
        d.product, "venda importada", " sem doc", d.unit, 
        d.iCause, d.today)
      if (d.product.salePrice == 0 && d.amount != 0) {
        d.product.salePrice (d.price / d.amount).save
      }
*/
    })
    // verificar email no bp e ligar
    // UtilSqlOffSaleProdutcs.updateOffSaleProdutcs(AuthUtil.company);
  }

  def saveContacts (listCol:Array[String]) = {
    val length = 5
    var temp = Array.ofDim[String](length)
    var maxLen = if (5 < listCol.length) {
      5 
    } else {
      listCol.length
    }
    for (i <- 0 to maxLen -1) {
      temp (i) = BusinessRulesUtil.convertChars(listCol (i))
    }  
    if (maxLen < 5) {
      for (i <- maxLen to 4) {
        temp (i) = "";
      }
    }
    val ac = Contact.createInCompany
    .name (BusinessRulesUtil.toCamelCase (temp(0)))
    .email ((temp(1)).toLowerCase)
    .phone (temp(2))
    .date1 (BusinessRulesUtil.mgrDate (temp(3)))
    try {
      ac.save
      if (ac.email != "") {
        val bp = Customer.findAllInCompany (By(Customer.email, ac.email))
        if (bp.length > 0) {
          ac.business_pattern (bp(0).id.is)
          ac.save
        }
      }
    } catch {
      case e:Exception => {
        // gerar log obj e mostrar 
        println ("vaiiiii ====== " + e.getMessage)
        //JString(e.getMessage)
      }
    }
  }

/*
  def removeInventory(bp:Long, purchaseprice:Double, treatmentdetail:Long, price:Double, amount:Float, product:Product, obs:String, invoice:String, unit:CompanyUnit, inventorycause:InventoryCause, efetivedate:Date) {
    //AuthUtil.company.inventoryCauseSale.obj.get
    // remove(amount) item product company(AuthUtil.company) obs(obs) invoice(invoice) cause(AuthUtil.company.inventoryCauseSale.obj.get) efetiveDate(new Date()) from unit
    remove(amount) item product company(AuthUtil.company) purchasePrice (purchaseprice) treatment_detail (treatmentdetail) totalSalePrice (price) obs(obs) business_pattern (bp) invoice(invoice) cause(inventorycause) efetiveDate(efetivedate) from unit
  }
*/

  def factory(i:Int, lines:List[String], separator:String):DetailContacts ={
    val listCol = lines(i).split(separator)
    listCol.foreach((column) => {
//      println ("vaiii ====== " + column);
    });

    //println ("vaiii ======== " + listCol(0) + " ----- " +listCol (1));
    saveContacts (listCol);

    DetailContacts("","","","")
/*
            lines(i).substring(0, index),
            lines(i).substring(index + 1, index_pr),
            lines(i).substring(index_pr +1, index_qt-1),
            lines(i).substring(index_ul+3, ((lines(i).length)))
          )
*/
  }
}


case class DetailContacts(
                  private val _customer:String,
                  private val _product:String, 
                  private val _qtty:String, 
                  private val _value:String){

  val today = Project.date_format_db.parse(Project.date_format_db.format(new Date()));
  def iCause = AuthUtil.company.inventoryCauseSale.obj.get
  def unit = AuthUtil.unit
  def purchasePrice = 0.0

  def price = BusinessRulesUtil.clearStrNum (_value).toDouble;

  def amount:Float = BusinessRulesUtil.clearStrNum (_qtty).toFloat;

  def product = Product.findByName (_product);

  def customer = Customer.findByName (_customer);

}

