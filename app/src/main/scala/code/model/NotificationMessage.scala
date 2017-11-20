package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._
import _root_.java.math.MathContext
import net.liftweb.mapper.DB

class NotificationMessage extends Audited[NotificationMessage] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = NotificationMessage
    object message extends MappedPoliteString(this,40000)
    object subject extends MappedPoliteString(this,255)  with LifecycleCallbacks {
      override def beforeSave() {
          super.beforeSave;
          if(this.get.length > 0){
          	// diferente do camel, coloca só a primeira letra maiúscula
            this.set(this.get.slice (0,1).toUpperCase + this.get.slice (1,this.get.length))
          }
      } 
    }
}
object NotificationMessage extends NotificationMessage with LongKeyedMapperPerCompany[NotificationMessage] with OnlyCurrentCompany[NotificationMessage]