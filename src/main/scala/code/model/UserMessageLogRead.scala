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

class UserMessageLogRead extends LongKeyedMapper[UserMessageLogRead] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy { 
    def getSingleton = UserMessageLogRead 
    object to extends MappedLongForeignKey(this,User)

    object message extends MappedLongForeignKey(this,UserMessage){
    	override def dbIndexed_? = true
    }

	object read_? extends MappedBoolean(this){
		override def dbColumnName = "read"
		override def defaultValue = false
    }

	object hide_? extends MappedBoolean(this){
		override def dbColumnName = "hide"
		override def defaultValue = false
    }
    object expirationDate extends EbMappedDate(this)
}
object UserMessageLogRead extends UserMessageLogRead with LongKeyedMapperPerCompany[UserMessageLogRead]  with  OnlyCurrentCompany[UserMessageLogRead]{
	def markAsRead(message:Long, user:Long) = {
		def logUserMessage:UserMessageLogRead = UserMessageLogRead.findAllInCompany(By(UserMessageLogRead.message,message), By(UserMessageLogRead.to,user)) match {
			case us::Nil => us.asInstanceOf[UserMessageLogRead]
			case _ => UserMessageLogRead.create.company(AuthUtil.company)
		}
		logUserMessage.read_?(true).save
	}

	def markAsHide(message:Long, user:Long) = {
		def logUserMessage:UserMessageLogRead = UserMessageLogRead.findAllInCompany(By(UserMessageLogRead.message,message), By(UserMessageLogRead.to,user)) match {
			case us::Nil => us.asInstanceOf[UserMessageLogRead]
			case _ => UserMessageLogRead.create.company(AuthUtil.company)
		}
		logUserMessage.hide_?(true).save
	}	

	def findAllUnread(user:User) = findAllInCompany(By(read_?,false),By(hide_?,false),By(to,user),OrderBy(createdAt, Ascending))

	def findAllHide(user:User) = findAllInCompany(By(read_?,false), By(hide_?,true),By(to,user),OrderBy(createdAt, Ascending))

}