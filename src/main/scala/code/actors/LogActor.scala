package code
package actors

import net.liftweb._
import http._
import actor._
import model._
import code.model._
import code.util._
import java.util.Random
import java.net.InetAddress;

object LogActor extends LiftActor  with net.liftweb.common.Logger  {

  def treat(message:LogMessage) {
    val company = if (AuthUtil.?) {
      AuthUtil.company.id.is
    } else {
      0
    }
    info(message.message)
    LogObj.create.company(company).message(message.message).typeLog(message.typeLog).save;
  }

  def treat(message:String) {
/*
    val inetAddr:InetAddress = InetAddress.getLocalHost();
            val addr = inetAddr.getAddress();
            // Convert to dot representation
            var ipAddr = "";
            for (i <- 1 to addr.length) {
                if (i-1 > 0) {
                    ipAddr += ".";
                }
                ipAddr += addr(i-1) & 0xFF;
            }
            val hostname = inetAddr.getHostName();
//            println("IP Address: " + ipAddr);
//            println("Hostname: " + hostname);
  println ("vaiii ================= " + ipAddr + " " + hostname)
    val company = if (AuthUtil.?) {
      println ("vaiiii ================== company " + AuthUtil.company.id.is)
      AuthUtil.company.id.is
    } else {
      println ("vaiiii ================== zero " + AuthUtil.company.id.is)
      AuthUtil.company.id.is
    }
*/
    info(message)
    LogObj.create.message(message).typeLog("Info").save;
  }  

  protected def messageHandler = {
    case a:LogMessage => treat(a)
    case a:String => treat(a)
    case _ =>
  }
}
