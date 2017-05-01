package code
package snippet

import net.liftweb._
import http._
import code.actors._
import code.util._
import model._
import http.js._
import net.liftweb.util._
import net.liftweb.common._
import Helpers._
import scala.xml.{ NodeSeq, Text }
import java.util.Random

object LoginSnippet extends net.liftweb.common.Logger {
/* PARECE QUE NAO USA MAIS - 07/2016
  def unLoggedMail(in: NodeSeq): NodeSeq = {

      for {
          r <- S.request if r.post_?
            email <- S.param("email")
            password <- S.param("password")
        } {
        LogActor ! "Tentativa de Login "+email
        try {
            val loginStatus: LoginStatus = User.loginEmail(email, password)
            loginStatus.status match{
                case true =>  {
                  LogActor ! "Login succeed "+email        
                  AuthUtil << loginStatus.user
                  if (AuthUtil.company.appType.isEgrex) {
                    println ("vaiii egrex =========== ");
                    if (AuthUtil.user.isCustomer) {
                      S.redirectTo("/customer/list")
                    } else {
                      S.redirectTo("/financial/account_register")
                    }
                  } else if(PermissionModule.treatment_? && (AuthUtil.user.isSimpleUserCalendar || AuthUtil.user.isCalendarUser)){
                    S.redirectTo("/calendar")
                  } else if(PermissionModule.treatment_? && (AuthUtil.user.isSimpleUserCommand || AuthUtil.user.isCommandUser)){
                    S.redirectTo("/command/user_command")
                  } else if (AuthUtil.user.isSimpleUserCommission) {
                    S.redirectTo("/commission_conference_user")
                  }else if(PermissionModule.inventory_?){
                    S.redirectTo("/product/control_panel")
                  }else if(PermissionModule.financial_?){
                    S.redirectTo("/financial/account_register")
                  } else if (PermissionModule.peopleManager_?) {
                    S.redirectTo("/user/list")
                  }else{
                    S.redirectTo("/customer/list")
                  }
                }
                case _ =>{
                      LogActor ! "Login failed "+email;    
                      S.error("Usuário ou senha inválida!")
                      S.redirectTo("/login_mail")
                }
          }
        } catch {
          case e: NoSuchElementException => { S.error("E-mail não existe!");   }
          case _ => { S.error("Erro desconhecido no login!");   }
        }
      }
    
    if (!AuthUtil.?)
      in
    else
      NodeSeq.Empty
  }
*/  
  val rand = new Random(System.currentTimeMillis())

  def randomNumber = rand.nextInt(4)

  def logOut(in: NodeSeq): NodeSeq = {
   
    val isegrex = AuthUtil.company.appType.isEgrex
    val website = "http://"+AuthUtil.company.website
    val user = AuthUtil.user.id.is
    AuthUtil >>;

/*  voltar quando estabilizar o login por e-mail
    colocar tb o portal ebelle na lista
*/
    val straux = "https://www.facebook.com/VilaRikaSolucoes"::
    "https://pt-br.facebook.com/ebellegestao"::
    "http://www.vilarika.com.br/"::
    "https://www.facebook.com/ephysiogestao"::
    "http://www.gerirme.com.br"::
    Nil
      // para diferenciar ebelle e gerirme
      if (!isegrex) {
        // se local ou meu usuario rigel - volta pro login
        if (S.hostName.contains ("local")) {
          S.redirectTo("http://"+S.hostName+":7171/v2/login")
        } else if (user == 3 /* rigel*/) {
          S.redirectTo("http://ebelle.vilarika.com.br/v2/login")
        } else {
          S.redirectTo(straux(randomNumber))
          //S.redirectTo("http://"+S.hostName+"/v2/login")
        }
      } else {
        S.redirectTo(website)
      }
//       AuthUtil >>;
    in
  }

  def userName (in: NodeSeq): NodeSeq = {
    Text(AuthUtil.user.short_name.substring(0,scala.math.min(AuthUtil.user.short_name.length, 10)))
  }

  def companyInfos (in: NodeSeq): NodeSeq = {
    val straux1 = if  (AuthUtil.company.name.is != AuthUtil.unit.name.is) AuthUtil.unit.short_name.is else ""
    val straux0 = if (AuthUtil.user.isSuperAdmin) AuthUtil.company.id.is+" " else " - "
    Text(straux0 +AuthUtil.company.short_name.is+ " " + straux1)
  }

  def imageGravatar (in: NodeSeq): NodeSeq = {
    val email = AuthUtil.email
    val mail = email match {
      case mail:String if(mail != null) => Project.md5(mail.trim.toLowerCase)
      case _ => ""
    }
    
    
    <img id="avatar" style="width:24px" src={AuthUtil.user.thumbPath}/>
  }

}