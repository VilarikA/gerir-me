package code
package util

import code.model._
import dispatch._
import net.liftweb._
import json._
import common._
import http.js._
import JE._
import net.liftweb.util.Helpers

import java.util.Date


object FacebookUtil extends net.liftweb.common.Logger  {
	lazy val access_token = "432866086802252|Zaaoz76Hp1cIK8rNRY7dsPqjLEk"
	implicit val formats = DefaultFormats // Brings in default date formats etc.
	lazy val host = "graph.facebook.com"
	lazy val apiv2 = :/(host)

	def createEvent(access_token:String,profile_id:String,name:String,start_time:Date,end_time:Date,description:String):FacebookEventReturn = {
		val http = new Http
		val request = FacebookUtil.apiv2/profile_id/"events"
		val ret = http(request.secure <<? authParams(access_token) << mapParams(access_token,name,formatDate(start_time),formatDate(end_time),description) as_str)
		info(ret)
		val json = parse(ret)
		val faceEvent = (json.extract[FacebookEventReturn])
		/*
			val requestInvited = FacebookUtil.apiv2/faceEvent.id/"invited"/profile_id
			val retInvite = http(requestInvited.secure <<? authParams(access_token) << Map() as_str)
			info(retInvite)
			*/
		
		faceEvent
	}



	def getNewAccessTokey(lastTokey:String) = {
		val http = new Http
		val request = FacebookUtil.apiv2/"oauth"/"access_token"
		val ret = http(request.secure <<? Map("grant_type" -> "fb_exchange_token", "client_id"->"432866086802252", "client_secret"->"1e93b0335e1b171d91954a81146edb33", "fb_exchange_token" -> lastTokey) as_str)
		info(ret)
		ret
	}


	def getPermanentToken = {
		val http = new Http
		val request = FacebookUtil.apiv2/"oauth"/"access_token"
		val ret = http(request.secure <<? Map("type" -> "client_cred", "client_id"->"432866086802252", "client_secret"->"1e93b0335e1b171d91954a81146edb33") as_str)
		info(ret)
		ret
	}

	def authParams (token:String) = Map("access_token"->token)

	def mapParams(token:String,name:String,start_time:String,end_time:String,description:String) = Map( "name" -> name,"start_time" -> start_time, "end_time" -> end_time,"description" -> description, "access_token"->token, "privacy"->"SECRET")
		def formatDate(date:Date)= {
	        val formatted = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date);
	        formatted.substring(0, 22) + ":" + formatted.substring(22);
	    }	
	}

case class FacebookEventReturn(id:String)