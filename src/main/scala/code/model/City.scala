package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class City extends LongKeyedMapper[City] with IdPK {
    def getSingleton = City
    object name extends MappedPoliteString(this,255)
    object state extends MappedLong(this)
    object urlName extends MappedPoliteString(this, 255)
    object official_code extends MappedPoliteString(this, 255)
    def nameToUrl = {
        if(urlName.is == "" || urlName.is == null){
            urlName(java.text.Normalizer.normalize(this.name.is.toLowerCase.replaceAll(" ", "_"), java.text.Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")).save
        }
        urlName.is   
    }
} 

object City extends City with LongKeyedMetaMapper[City]{
	def findAllToSiteWithUnit = {
		findAll(
			BySql(" id in (select distinct up.cityref from companyunit u inner join business_pattern up on(u.partner = up.id)  where u.allowshowonsite=true)",IHaveValidatedThisSQL("",""))
		)
	}
}

class State extends LongKeyedMapper[State] with IdPK {
    def getSingleton = State
    object name extends MappedPoliteString(this,255)
    object short_name extends MappedPoliteString(this,255)
} 

object State extends State with LongKeyedMetaMapper[State]{

}
