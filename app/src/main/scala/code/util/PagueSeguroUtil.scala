package code
package util

import code.model._
import dispatch._


object PagueSeguroUtil{

	def createMonthly(c:Company,month:Int) = {
		println("Running Test")
		val http = new Http
		val req = :/("ws.pagseguro.uol.com.br") / "v2/checkout/"
		val response = http(req.secure << mapParams as_str)
		println(response.toString)
	}

	def mapParams = Map(
		"email" -> "mateus.freira@gmail.com",
		"token" -> "95112EE828D94278BD394E91C4388F20",
		"currency" -> "BRL",
		"itemId1" -> "1",
		"itemDescription1" -> "Teste",
		"itemAmount1" -> "0.4",
		"itemQuantity1" -> "1",
		"itemWeight1" -> "1",
		"reference" -> "REF123",
		"senderName" -> "Empresa Teste"

		)
}

//import code.util._
//PagueSeguroUtil.createMonthly(null,1)