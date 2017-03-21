package code
package snippet

import net.liftweb._
import http._
import code.util._
import model._
import http.js._
import JE._
import JsCmds._
import net.liftweb.util._
import net.liftweb.common._
import Helpers._
import scala.xml.{ NodeSeq, Text }
//import InventoryMovement._
import net.liftweb.mapper._
import net.liftweb.mapper.{StartAt, MaxRows} 

class  InventorySnippet extends Logger with BootstrapPaginatorSnippet[Product] {

	
	def types = Seq(
				"In"-> "Entrada",
				"Out"-> "Saída",
				"Transfer"-> "Transferência"
			)
	/**
	* Pagination Methods
	*/
	def pageObj = Product

	def units:List[Long] = S.param("units") match {
		case Full(s) if(s != "") => {
			S.params("units").map(_.toLong)
		}
		case _ => {
			Nil
		}
	}

	def filters:List[QueryParam[Product]] = {
		def id = S.param("id") match {
			case Full(s) if(s != "") => {
				By(Product.id,s.toLong)
			}
			case _ => {
				BySql[code.model.Product]("1 =1",IHaveValidatedThisSQL("",""))
			}
			
		}
		
		def line = S.param("line_select") match {
			case Full(s) if(s != "") => {
				BySql[code.model.Product]("id in (select product from productlinetag where line in("+S.params("line_select").foldLeft("0")(_+","+_)+"))",IHaveValidatedThisSQL("",""))
			}
			case _ => {
				BySql[code.model.Product]("1 =1",IHaveValidatedThisSQL("",""))
			}
			
		}	
		def category = S.param("category_select") match {
			case Full(s) if(s != "") => {
				BySql[code.model.Product]("typeproduct in("+S.params("category_select").reduceLeft(_+","+_)+")",IHaveValidatedThisSQL("",""))
			}
			case _ => {
				BySql[code.model.Product]("1 =1",IHaveValidatedThisSQL("",""))
			}
			
		}

		def brand = S.param("brands") match {
			case Full(s) if(s != "") => {
				BySql[code.model.Product]("brand in("+S.params("brands").reduceLeft(_+","+_)+")",IHaveValidatedThisSQL("",""))
			}
			case _ => {
				BySql[code.model.Product]("1=1",IHaveValidatedThisSQL("",""))
			}
			
		}		

		def name = S.param("name") match {
			case Full(s) if(s != "") => {
				Like(Product.search_name,"%"+BusinessRulesUtil.clearString(s)+"%")
			}
			case _ => {
				BySql[code.model.Product]("1 =1",IHaveValidatedThisSQL("",""))
			}
			
		}

		def is_inventory_control = BySql[code.model.Product]("is_inentory_control = true ",IHaveValidatedThisSQL("",""))

		def qtd_query = S.param("qtd_start") match {
			case Full(s) if(s != "") => {
				def end =  S.param("qtd_end") match {
					case Full(se) if(se != "") => se.toInt
					case _ => 10000
				}
				BySql[code.model.Product]("id in (select product from inventorycurrent where company=? group by product having sum(currentstock) between ? and ?)",IHaveValidatedThisSQL("",""), AuthUtil.company.id.is,s.toInt,  end)
			}
			case _ => {
				BySql[code.model.Product]("1 =1",IHaveValidatedThisSQL("",""))
			}
			
		}
		/*
		def price_query = S.param("price_start") match {
			case Full(s) if(s != "") => {
				def end =  S.param("price_end") match {
					case Full(se) if(se != "") => se.toDouble
					case _ => 100000.00
				}
				
				BySql[code.model.Product]("purchaseprice between ? and ?",IHaveValidatedThisSQL("",""), s.toDouble,  end)
			}
			case _ => {
				BySql[code.model.Product]("1 =1",IHaveValidatedThisSQL("",""))
			}
			
		}*/	
		//
		//
		qtd_query :: id  :: line :: category :: name :: brand :: is_inventory_control :: Nil
	}

	def order_query = OrderBy(Product.name, Ascending)

	def findForListParamsWithoutOrder: List[QueryParam[Product]] =  MaxRows[code.model.Product](itemsPerPage) :: filters

	def findForListParams: List[QueryParam[Product]] = order_query :: MaxRows[code.model.Product](itemsPerPage) :: StartAt[code.model.Product](curPage*itemsPerPage) :: filters


	def products = AuthUtil.company.products.map(t => (t.id.is.toString,t.name.is))
	def products(xhtml: NodeSeq): NodeSeq = {
		page.flatMap(p => 
		bind("f", xhtml,"name" -> Text(p.name.is),
						"obs" -> Text(p.obs.is),
						"type" -> Text(p.typeName),
						"purchaseprice" -> {
											if(AuthUtil.user.isInventoryManager) { 
												Text(p.purchasePrice.is.toString)
											} else{
											 	NodeSeq.Empty 
											}
										},
						"saleprice" -> Text(p.salePrice.is.toString),
						"actions" -> <a class="btn" href={"/product_admin/edit?id="+p.id.is}>Editar</a>,
						"minstock" -> Text(p.minStock.is.toString),
						"status" -> stockStatus(p),
						"totalcurrentstock" -> Text(p.totalCurrentStock.toString),
						"currentstock" -> <table>{p.inventoryUnits.filter((ip) => { units.size ==0 || units.contains(ip.unit.is) }).map((ip) => <tr><td>{ip.unit.obj.get.name} </td> <td>{ip.currentStock.is.toString}</td></tr>)}</table>,
						"ex_id" ->Text(p.external_id.is.toString),
						"id" ->Text(p.id.is.toString)
			)
		)
	}

	def stockStatus(p:Product):NodeSeq ={
		p.statusStock match {
			case (Product.StockStatus.Bad) => <a href="#" class="_popover" rel="popover" data-content="Estoque está abaixo da quantidade mínima recomendável, providenciar a compra!" data-original-title="Estoque"> <img  src={"/images/bad.png"}/></a>
			case (Product.StockStatus.Warning) => <a href="#"	class="_popover" rel="popover" data-content="Estoque está em estado de alerta, muito próximo do valor recomendado como mínimo!" data-original-title="Estoque"> <img src={"/images/warning.png"}/></a>
			case _ => <a href="#"	class="_popover" rel="popover" data-content="A quantidade de estoque é suficiente para operação atual!" data-original-title="Estoque"><img src={"/images/good.png"}/></a>
		}
		
	}  	
}