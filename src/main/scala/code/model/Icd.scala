package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class Icd extends LongKeyedMapper[Icd] 
	with IdPK {
    def getSingleton = Icd
    object name extends MappedPoliteString(this,255)
    object search_name extends MappedPoliteString(this,255)
    object namecomp extends MappedPoliteString(this,255)
    object section extends MappedPoliteString(this,1)
    object subsection extends MappedPoliteString(this,1)

} 

object Icd extends Icd with LongKeyedMetaMapper[Icd]{
    val SQL_SEARCH = """
                select 
                p.name,
                p.id
                from 
                icd p
                where
                (p.search_name like ?)
                order by p.name
                limit 30
                OFFSET ?;
    """
    def findAllForSearch(name:String, startPage:Int, where:String = "1=1") = {
        val r = DB.performQuery(SQL_SEARCH.format(where),name::(startPage*30)::Nil)
        r._2.map(
                (p:List[Any]) => IcdSearch(p(0).toString, p(1).asInstanceOf[Long])
            );
    }
    def findAllForSearch(params: QueryParam[Icd]*) = {
        //val PreviousDebts = Value("Debitos Anteriores")
        //val CustomerCredits = Value("Compra de Credito")
        super.findAll( params.toList :_*)
    }
}

case class IcdSearch(name:String, id:Long){
}



