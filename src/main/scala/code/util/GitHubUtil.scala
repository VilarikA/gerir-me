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


object GitHubUtil extends net.liftweb.common.Logger  {
	implicit val formats = DefaultFormats // Brings in default date formats etc.
	lazy val host = "github.com"
	lazy val apiv2 = :/(host)/"api"/"v2"/"json"

	def createIssue(title:String,body:String,label:String,user:String,repo:String,token:String):String = {
		val http = new Http
		val requestIssue = GitHubUtil.apiv2/"issues"/"open"/user/repo
		val issue = http(requestIssue <<? authParams(token) << mapParams(title,body) as_str)
		info(issue)
		val json = parse(issue)
		val ghIssue = (json.extract[GhIssueRequest]).issue
		val requestLabel = GitHubUtil.apiv2/"issues"/"label"/"add"/user/repo/label/ghIssue.number.toString
		http(requestLabel <<?  authParams(token) << Map() as_str)

	}

	def authParams (token:String) = Map("access_token"->token)

	def mapParams(title:String,body:String) = Map( "title" -> title,"body" -> body)

	}

case class GhIssueRequest(issue:GhIssue)
case class GhIssue(number:Int)
//{"issue":{"gravatar_id":"79be5389a59b1fb15363f477681adfe3","position":1.0,"number":62,"votes":0,"created_at":"2012/05/07 05:38:50 -0700","comments":0,"body":"dasda{Company:1, User:Admin}","title":"Compra do Mes","updated_at":"2012/05/07 05:38:50 -0700","html_url":"https://github.com/mateusfreira/E-belle-Ligth/issues/62","user":"mateusfreira","labels":[],"state":"open"}}