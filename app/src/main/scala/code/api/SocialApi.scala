package code
package api

import code.model._
import code.util._
import code.service._

import net.liftweb._
import common._
import http._
import rest._
import json._
import http.js._
import JE._
import net.liftweb.util.Helpers

import scala.xml._

import java.text.ParseException
import java.util.Date
import java.util.Calendar

import net.liftweb.json._
//implicit val formats = DefaultFormats // Brings in default date formats etc.

object SocialApi extends RestHelper with net.liftweb.common.Logger  {
	val DAY_IN_MILESECOUNDS = 86400000;
	serve {
		case "social" :: "teste_auth" :: Nil Get _ =>{
			JString(FacebookUtil.getPermanentToken)
		}
		case "social" :: "teste_auth_c" :: Nil Get _ =>{
			JString(FacebookUtil.getNewAccessTokey("AAAGJsH1W00wBAEfWKvd5isIJroxRfUMbG7wYYIZBtmXibyWd7TcAd76wpY21o0V2tf4Y1pYLO5Q7McGqft6nZCLugFJAKh1rIvaZAOJgZBZCq61hCM9Nd"))
		}		
		
		case "social" :: "teste" :: companyId ::  Nil Get _ =>{
			val customer = Customer.findByKey(21736).get
			val company = Company.findByKey(companyId.toLong).get
			company.treatmentsToDay.foreach((n) => { NotificationQueeue.enqueeue(NotificationDto(n.id.is)) })
			JInt(1)
		}


		case "social" :: "facebook_register" :: Nil Post _ =>{
			def facebookId = S.param("facebookId") openOr ""
			def facebookAccessToken = S.param("facebookAccessToken") openOr ""
			def facebookUsername = S.param("facebookUsername") openOr ""
			def customerId = (S.param("customerId") openOr "").toLong
			Customer.findByKey(customerId).get.facebookId(facebookId).facebookAccessToken(facebookAccessToken).facebookUsername(facebookUsername).save
			JInt(1)
		}

		case "social" :: "user_by_facebook" :: Nil Post _ =>{
			def facebookId = S.param("facebookId") openOr ""
			def facebookAccessToken = S.param("facebookAccessToken") openOr ""
			def facebookUsername = S.param("facebookUsername") openOr ""
			val users = User.findByFacebook(facebookId, facebookAccessToken)
			JsArray(
				users.map((u) =>{
					JsObj(
							("name", u.name.is),
							("id", u.id.is),
							("company", u.company.is), 
							("facebookId", u.facebookId.is), 
							("facebookAccessToken", u.facebookAccessToken.is)
						)
				})
			)
		}
	}
}