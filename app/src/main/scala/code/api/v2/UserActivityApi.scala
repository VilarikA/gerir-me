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
import net.liftweb.json.Serialization.{read, write}
import net.liftweb.json.Extraction._


object UserActivityApi extends RestHelper with ReportRest with net.liftweb.common.Logger {

		serve {
			
			case "api" :: "user" :: "useractivity_by_category" :: userId :: category :: Nil JsonGet _ =>{
				val user = User.findByKey(userId.toLong).get
				JArray(Activity.findAllInCompany(By(Activity.typeProduct, category.toLong),
					OrderBy (Activity.name, Ascending)).map((a:Activity) =>{
						val salePrice = user.activityPrice(a).toDouble
						val commission = user.activityCommission(a).toDouble
						val commissionAbs = user.activityCommissionAbs(a).toDouble

    					val auxPrice = user.activityAuxPrice(a).toDouble
    					val auxPercent = user.activityAuxPercent(a).toDouble
    					val auxHousePrice = user.activityAuxHousePrice(a).toDouble
    					val auxHousePercent = user.activityAuxHousePercent(a).toDouble

						val duration = user.activityDuration(a)
						val userActivityId = user.activityId(a)
						val due = userActivityId != 0
						val userActivity = UserActivity.findByKey(userActivityId)
						val use_product_price:Boolean  = if(due) {
							userActivity.get.use_product_price_?
						}else{
							true
						}
						val use_product_commission:Boolean = if(due) {
							userActivity.get.use_product_commission_?
						}else{
							true
						}
						decompose(UserActivityApiDto(a.id.is, user.id.is,a.name.is, salePrice, 
							commission, commissionAbs, auxPercent, auxPrice,
							auxHousePercent, auxHousePrice, 
							duration, due ,userActivityId, use_product_price, 
							use_product_commission))
				 }))
			}
			case "api" :: "user" :: "userproduct_by_category" :: userId :: category :: Nil JsonGet _ =>{
				val user = User.findByKey(userId.toLong).get
				JArray(Product.findAllInCompany(By(Product.typeProduct, category.toLong),
					OrderBy (Product.name, Ascending)).map((a:Product) =>{
						val salePrice = user.activityPrice(a).toDouble
						val commission = user.activityCommission(a).toDouble
						val commissionAbs = user.activityCommissionAbs(a).toDouble
    					val auxPrice = user.activityAuxPrice(a).toDouble
    					val auxPercent = user.activityAuxPercent(a).toDouble
    					val auxHousePrice = user.activityAuxHousePrice(a).toDouble
    					val auxHousePercent = user.activityAuxHousePercent(a).toDouble
						val userActivityId = user.activityId(a)
						val due = userActivityId != 0
						val userActivity = UserActivity.findByKey(userActivityId)
						val use_product_price:Boolean  = if(due)  userActivity.get.use_product_price_? else true

						val use_product_commission:Boolean = if(due) userActivity.get.use_product_commission_? else true

						decompose(UserActivityApiDto(a.id.is, user.id.is,a.name.is, salePrice, 
							commission, commissionAbs, auxPercent, auxPrice,
							auxHousePercent, auxHousePrice, "", due ,userActivityId, use_product_price, 
							use_product_commission))
				 }))
			}			
			case "api" :: "user" :: "useractivity" :: userId :: Nil JsonGet _ =>{
				val user = User.findByKey(userId.toLong).get
			 	JArray(UserActivity.findAll(By(UserActivity.user, userId.toLong),
			 		OrderBy (UserActivity.id, Ascending)).map((obj:UserActivity) =>{
			 		val a = obj.activity.obj.get
					val salePrice = user.activityPrice(a).toDouble
					val commission = user.activityCommission(a).toDouble
					val commissionAbs = user.activityCommissionAbs(a).toDouble
					val auxPrice = user.activityAuxPrice(a).toDouble
					val auxPercent = user.activityAuxPercent(a).toDouble
					val auxHousePrice = user.activityAuxHousePrice(a).toDouble
					val auxHousePercent = user.activityAuxHousePercent(a).toDouble
					val duration = user.activityDuration(a)
					val userActivityId = obj.id.is
					val userActivity = obj
					val use_product_price:Boolean  = obj.use_product_price_?
					val use_product_commission:Boolean = obj.use_product_commission_?

					decompose(UserActivityApiDto(a.id.is, user.id.is, a.name.is, salePrice, 
						commission, commissionAbs, auxPercent, auxPrice,
							auxHousePercent, auxHousePrice, duration, true,userActivityId, use_product_price, use_product_commission))
			 	}))
			}
			case "api" :: "user" :: "useractivity" :: userId ::  id :: Nil JsonGet _ =>{
			 	UserActivity.findByKey(id.toLong).get.asJs
			}
			case "api" :: "user" :: "useractivity" :: userId ::  id :: "delete":: Nil JsonGet _ =>{
			 	UserActivity.findByKey(id.toLong).get.delete_!
			 	JInt(1)
			}			
			case "api" :: "user" :: "useractivity" :: userId :: id :: Nil JsonPost json -> _ =>{
				if(id == "0")
					UserActivity.createFromJson(json.asInstanceOf[net.liftweb.json.JsonAST.JObject]).save
				else
					UserActivity.findByKey(id.toLong).get.updateFromJson(json.asInstanceOf[JsonAST.JObject])
			 	JInt(1)
			}

			case "api" :: "user" :: "useractivity" :: userId :: Nil JsonPost json -> _ =>{
				UserActivity.createFromJson(json.asInstanceOf[net.liftweb.json.JsonAST.JObject]).save
				JString("")
			}			
		}

}
case class UserActivityApiDto(activity:Long, user:Long, name:String, price:Double, 
	commission:Double, commissionAbs:Double, 
	auxPercent:Double, auxPrice:Double, 
	auxHousePercent:Double, auxHousePrice:Double, duration:String, enabled:Boolean, id:Long, use_product_price:Boolean, use_product_commission:Boolean)
