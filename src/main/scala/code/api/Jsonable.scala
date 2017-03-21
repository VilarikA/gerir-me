
package code
package api

trait Jsonable {
	private implicit val formats = net.liftweb.json.DefaultFormats
 	def toJson = net.liftweb.json.Extraction.decompose(this)
 }