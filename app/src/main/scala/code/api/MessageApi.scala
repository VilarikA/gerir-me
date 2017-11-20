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
import java.util._

import net.liftweb.json._
//implicit val formats = DefaultFormats // Brings in default date formats etc.

object MessageApi extends RestHelper {
	val DAY_IN_MILESECOUNDS = 86400000;
	serve {
		case "messages" :: Nil Post _ => {
			def subject = S.param("subject") openOr ""
			def message = S.param("message") openOr ""
			lazy val group = S.param("group") match {
				case Full(s) if(s != "") => s.toLong
				case _ => 0l
			}

			lazy val users = S.param("users") match {
				case Full(users_str) if(users_str != "") => users_str.split(",").map(_.toLong).toList
				case _ => Nil
			}
			lazy val expirationdate:Date = S.param("expirationdate") match {
				case Full(p) => Project.strToDateOrToday(p)
				case _ => new Date() 
			}
			
			UserMessage.build(subject,message,AuthUtil.user,group,users, AuthUtil.company, UserMessage.NORMAL, expirationdate)
			//.create.subject(subject).message(message).of(AuthUtil.user).company(AuthUtil.company).for_all_?(true).save
			JInt(1)
		}

		case "messages" :: "hided" :: Nil JsonGet _ => {
			JsArray(UserMessageLogRead.findAllHide(AuthUtil.user).map(um => {
				lazy val message = um.message.obj.get
				JsObj(("id",message.id.is),("message", message.message.is),("subject", message.subject.is), ("of",message.of.obj.get.name.is), ("sended", message.createdAt.toString()))
			}));
		}
		case "messages" :: Nil JsonGet _ => {
			JsArray(UserMessageLogRead.findAllUnread(AuthUtil.user).map(um => {
				lazy val message = um.message.obj.get
				JsObj(
						("id",message.id.is),
						("message", message.message.is),
						("subject", message.subject.is), 
						("of",message.of.obj.get.name.is),
						("messageType",message.messageType.is),
						("sended", message.createdAt.toString()) 
					)
			}));
		}

		case "messages" :: "read" ::  messageId :: Nil JsonGet _ => {
			UserMessageLogRead.markAsRead(messageId.toLong, AuthUtil.user.id.is)
			JInt(1);
		}

		case "messages" :: "hide" ::  messageId :: Nil JsonGet _ => {
			UserMessageLogRead.markAsHide(messageId.toLong, AuthUtil.user.id.is)
			JInt(1);
		}		

	}
}