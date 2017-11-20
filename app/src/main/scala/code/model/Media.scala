package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class Media extends Audited[Media] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy with Siteble  with MultiEntityRelated with PerUnit{ 
    def getSingleton = Media 
    def imagePath = "media"
    override def logo_web = Props.get("photo.urlbase").get+imagePath+"/"+image.is
}

object Media extends Media with LongKeyedMapperPerCompany[Media]  with  OnlyCurrentCompany[Media]{
	
}
