package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import code.util._
import _root_.java.math.MathContext; 

import java.util.Date

class UserMessage extends LongKeyedMapper[UserMessage] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = UserMessage 
    object message extends MappedPoliteString(this,4000)
    object subject extends MappedPoliteString(this,255)
    object messageType extends MappedInt(this){
    	override def defaultValue = UserMessage.NORMAL
    }
    object of extends MappedLongForeignKey(this,User)
	object for_all_? extends MappedBoolean(this){
		override def dbColumnName = "forall"
		override def defaultValue = false
	}
    object expirationDate extends EbMappedDate(this)
	override def save() = {
		val r = super.save()
		if(for_all_?.is){
			UserMessage.prepareUserMessages(User.findAllInCompanyOrdened.map(_.id.is),this, this.company.obj.get, this.expirationDate.is)
		}
		r
	}

}
object UserMessage extends UserMessage with LongKeyedMapperPerCompany[UserMessage]  with  OnlyCurrentCompany[UserMessage]{
	val NORMAL = 1
	val SYSTEM = 2
	private def prepareUserMessages(users:List[Long], message:UserMessage, company:Company, expirationdate:Date) = {
		users.foreach((u) => {
			UserMessageLogRead.create.to(u).company(company).message(message).expirationDate(expirationdate).save
		});
	}

	def findAllUnread = findAllInCompany(By(for_all_?,true))

	def build(subject:String, message:String, of:User, group:Long, to:List[Long], company:Company = AuthUtil.company, messageType:Int = UserMessage.NORMAL, expirationdate:Date) {
		def for_all = group == 0 && to.size ==0
		val messageObj = UserMessage.create.subject(subject).message(message).of(of).company(company).for_all_?(for_all)
		messageObj.expirationDate(expirationdate).save
		if(!for_all){
			if(group != 0){
				prepareUserMessages(User.findAllInCompany(By(User.group,group)).map(_.id.is),messageObj, company, expirationdate)
			}else{
				prepareUserMessages(to,messageObj, company, expirationdate);
			}
		}
	}

}

