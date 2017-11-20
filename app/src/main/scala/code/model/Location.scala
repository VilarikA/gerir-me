package code
package model 

import net.liftweb._ 
import mapper._ 
import json._ 
import http._ 
import SHtml._ 
import util._
import _root_.java.math.MathContext
import net.liftweb.mapper.DB

class LocationHistory extends LongKeyedMapper[LocationHistory]  with PerCompany  with IdPK with CreatedUpdated with Localizable{
    def getSingleton = LocationHistory
    object user extends MappedLongForeignKey(this,User)
    object deviceDate extends EbMappedDateTime(this)
    object distance extends MappedDecimal(this,MathContext.DECIMAL64,10);
}

object LocationHistory extends LocationHistory with OnlyCurrentCompany[LocationHistory] with LongKeyedMapperPerCompany[LocationHistory]{

}

class Car extends LongKeyedMapper[Car]  with PerCompany with IdPK with CreatedUpdated with Localizable{ 
    def getSingleton = Car 
    object name extends MappedPoliteString(this,255) 
    object userName extends MappedString(this,255) 
    object password extends MappedPoliteString(this,20)
    object icon extends MappedString(this,255)
    //def locations = LocationHistory.findAll(By(LocationHistory.car,this))
    /*
    def updateLocation(lat:String,lng:String){
        LocationHistory.create.car(this).lat(lat).lng(lng).save
        this.lat(lat).lng(lng).save
     }*/    
}

object Car extends Car with LongKeyedMapperPerCompany[Car] with OnlyCurrentCompany[Car]{
  	def login (userName:String,passWord:String,company:Company):CarLoginStatus = {
        val list = Car.findAll(By(Car.userName,userName),
            By(Car.company,company.id.is))
        if(list.size >0)
          CarLoginStatus(true, list(0))
        else
           CarLoginStatus(false,Car.create)
    }      
}

trait Localizable{ 
    self: BaseMapper =>
    lazy val lat = new MyLat(this)

    protected class MyLat(obj: self.type) extends MappedPoliteString(this.asInstanceOf[MapperType],20){
    }
    
    lazy val lng = new MyLng(this)
    protected class MyLng(obj: self.type) extends MappedPoliteString(this.asInstanceOf[MapperType],20){
    }
}

case class CarLoginStatus(status:Boolean,user:Car){
    
}