package code
package model

import code.util._

import net.liftweb._
import mapper._
import util._
import common._

object ExtSession extends ExtSession with MetaEbProtoExtendedSession[ExtSession] with CreatedUpdated{
  override def dbTableName = "ext_session" // define the DB table name

  def logUserIdIn(uid: String, company:Long, unit:Long): Unit = AuthUtil << (User.findByKey(uid.toLong).get).unit(unit).company(company)

  def recoverUserId: Box[String] = if(AuthUtil.?) {
  			Full(AuthUtil.user.id.is.toString)
  		}else{
  			Empty
  		}

  type UserType = User
}
class ExtSession extends EbProtoExtendedSession[ExtSession] {
  def getSingleton = ExtSession // what's the "meta" server
}