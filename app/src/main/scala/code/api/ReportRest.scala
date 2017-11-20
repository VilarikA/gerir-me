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

import scala.xml._

import java.text.ParseException
import java.util.Date

import net.liftweb.json._
import net.liftweb.mapper._ 

import java.util.Calendar
import java.util.HashMap
import java.sql.Connection
import java.sql.DriverManager
import net.sf.jasperreports.engine.JasperRunManager
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.design.JasperDesign
import net.sf.jasperreports.engine.xml.JRXmlLoader
import net.sf.jasperreports.view.JasperViewer
import net.sf.jasperreports.engine.JasperExportManager



trait ReportRest extends net.liftweb.common.Logger{
	/*
	def init() : Unit = {
	    LiftRules.statelessDispatch.append(Reunite)
	}*/
	def dateOrEmpty (date:Date) = {
		if(date == null)
			""
		else
			date.getTime.toString
	}
	def startParam:Date = S.param("start") match {
		case Full(p) => Project.strToDateOrToday(p)
		case _ => new Date()
	}
	def endParam:Date = S.param("end") match {
		case Full(p) => Project.strToDateOrToday(p)
		case _ => new Date()
	}	
	def filterSqlIn(paramName:String, sqlToFormat:String):String = {
		val realName = paramNameMultiple(paramName)
		S.param(realName) match {
			case Full(p) => {
				val listValues = S.params(realName).filter(_ != "").map(_.toLong).map(_.toString)
				if(listValues.isEmpty){
					"1 = 1"
				}else{
					sqlToFormat.format(listValues.reduceLeft(_+","+_))
				}
			}
			case _ => "1 = 1"
		}
	}

	def paramNameMultiple(name:String)={
		S.param(name) match {
			case Full(p) => name
			case _ => name+"[]"
		}
	}
	def toResponse(sql:String, params:List[Any]) = {
		JsonResponse(toJArray(sql, params))
	}

	def toResponseWithHeader(sql:String, params:List[Any]) = {
		JsonResponse(toJArrayWithHeader(sql, params))
	}	

	def toJArrayWithHeader(sql:String, params:List[Any]) = {
		val r = DB.performQuery(sql,params)
		val headers = r._1.map((v:String) => if(v!=null){JString(v.toString)}else{JString("")})
		val data = r._2.map((p:List[Any])=> JArray(p.map((v:Any) => if(v!=null){JString(v.toString)}else{JString("")})))
		val everything = JArray(headers) :: data

		JArray( everything )
	}
	def toJArray(sql:String, params:List[Any]) = {
		//info(sql)
		val r = DB.performQuery(sql,params)
		JArray(r._2.map((p:List[Any])=> JArray(p.map((v:Any) => if(v!=null){JString(v.toString)}else{JString("")}))))
	}
	
	def toCsv(sql:String, params:List[Any]) = {
		//info(sql)
		val r = DB.performQuery(sql,params)
		r._2.map(
			(p:List[Any])=> p.map(
				(v:Any) =>
					if(v !=  null){ 
						v.toString 
					}else{
						""
					}
				).reduceLeft(_+";"+_)
			).reduceLeft(_+"\n"+_)
	}	
} 