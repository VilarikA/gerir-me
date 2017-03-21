package code
package api

import code.model._
import code.util._
import code.service._
import code.actors._

import net.liftweb._
import common._
import http._
import rest._
import json._
import http.js._
import JE._
import net.liftweb.util.Helpers
import net.liftweb.mapper._ 
import scala.xml._

import java.text.ParseException
import java.util.Date

import net.liftweb.json._
//implicit val formats = DefaultFormats // Brings in default date formats etc.


object OffSaleApi extends RestHelper with ReportRest with net.liftweb.common.Logger {

		serve {
			case "offsale" :: Nil JsonGet _ =>{
				JsArray(OffSale.findAllInCompany.map((a) =>{
					JsObj(
						("id", a.id.is),
						("name", a.name.is)
						)
				}))
			}

			case "offsale" :: "offsaleProducts" :: id :: Nil Delete _ =>{
				OffSaleProduct.findByKey(id.toLong).get.delete_!
				JInt(1)
			}

			case "offsale" :: "products" :: offSaleId  ::  Nil JsonGet (r) =>{
				JsArray(
						OffSaleProduct
						.findAllInCompany(By(OffSaleProduct.offsale, offSaleId.toLong))
						.map((ofp:OffSaleProduct) => offSaleProductToJson(ofp))
				)
			}

			case "offsale" :: "offsaleProducts" ::  Nil JsonPost json -> _ =>{
				val offSaleCase = json.extract[OffSaleCase]
				lazy val offSaleObj:OffSaleProduct = if(offSaleCase.id > 0){
					OffSaleProduct.findByKey(offSaleCase.id).get
				}else{
					OffSaleProduct.createInCompany
				}
				offSaleObj.product(offSaleCase.product_id)
						  .productLine(offSaleCase.line_id)
						  .productType(offSaleCase.category_id)
						  .percentOff(offSaleCase.percentOff)
						  .offPrice(offSaleCase.offPrice)
						  .minimum(offSaleCase.minimum)
						  .limitAmount(offSaleCase.limitAmount)
						  .offsale(offSaleCase.offsale)
						  .delivery_?(offSaleCase.delivery)
						  .save;

				JString(offSaleCase.id.toString)
			}
		}

		case class OffSaleCase(id:Long,product_id:Long, product:String, line_id:Long, line:String, category_id:Long, category:String, percentOff:Double, offPrice:Double, minimum:Int, limitAmount:Int, offsale:Long,delivery:Boolean)

		def offSaleProductToJson(product:OffSaleProduct) = {
			JsObj(
				  ("id",product.id.is),
				  ("product_id",product.product.is),
				  ("product",product.product.obj match {
				  	case Full(p) => p.name.is
				  	case _ => ""
				  }
				  ),
				  ("line_id",product.productLine.is),
				  ("line",product.productLine.obj match {
				  	case Full(p) => p.name.is
				  	case _ => ""
				  }),
				  ("category_id",product.productType.is),
				  ("category",product.productType.obj match {
				  	case Full(p) => p.name.is
				  	case _ => ""
				  }),
				  ("percentOff",product.percentOff.is),
				  ("offPrice",product.offPrice.is),
				  ("minimum",product.minimum.is),
				  ("limitAmount",product.limitAmount.is),
				  ("delivery",product.delivery_?.is)
				  
				 )
		}

}