package code
package util

import dispatch._


import code.model._
import code.util._
import code.service._
import code.actors._

import net.liftweb._
import mapper._
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

//implicit def formats = net.liftweb.json.DefaultFormats

object GoogleGeocoderUtil {
	implicit val formats = net.liftweb.json.DefaultFormats
	//http://?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=false

	def isAllDigits(x: String) = x.map(Character.isDigit(_)).reduce(_&&_)

	def geocoder(c:Customer) {
		//println("Running Test")
		val http = new Http
		val req = :/("maps.googleapis.com") / ("maps/api/geocode/json")
		val response = http(req.secure <<? Map(("address"," "+c.full_address), ("sensor", "true")) as_str)
		response
		val json = parse(response)
		val geocoder = json.extract[GeocoderServiceResult]
		if(geocoder.isOk){
			val location = geocoder.results(0).geometry.location
			if (c.postal_code.isEmpty || c.postal_code == null) {
				var cep = geocoder.results(0).formatted_address
				//println ("=============== " + cep);
				if (cep.length > 7) {
					if (cep.substring(cep.length-6,cep.length) == "Brazil") {
						cep = cep.substring (cep.length-17,cep.length-17 + 9)
						val cepaux = cep.substring (0,2);
						if (isAllDigits(cepaux)){
							c.postal_code(cep).lat(location.lat.toString).lng(location.lng.toString).insecureSave
						} else {
							//println ("=============== falhou cep nao veio " + geocoder.results(0).formatted_address);
							c.lat(location.lat.toString).lng(location.lng.toString).insecureSave
						}
					}
				}
			} else {
				c.lat(location.lat.toString).lng(location.lng.toString).insecureSave
			}
		} else {
			// println ("=============== falhou " + c.full_address);
		}
		
	}

}

case class GeocoderServiceResult(results:List[GeocoderResult]=Nil, status:String="Error"){
	def isOk = status == "OK"
}
case class GeocoderResult(formatted_address:String, geometry:Geometry)
case class Geometry(location:GeocoderLocation,location_type:String)
case class GeocoderLocation(lat:Double, lng:Double)