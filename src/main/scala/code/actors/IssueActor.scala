package code
package actors

import net.liftweb._
import util._
import http._
import actor._
import model._
import code.model._
import code.util._
import java.util.Random

object IssueActor extends LiftActor with net.liftweb.common.Logger  {
  lazy val  tokey = Props.get("git.secretTokey") openOr ""
  lazy val  user = Props.get("git.user") openOr ""
  lazy val  repo = Props.get("git.repo") openOr ""
  lazy val  label = Props.get("git.label") openOr ""
  def treat(issue:Issue) {
    info(GitHubUtil.createIssue(issue.title,issue.body,label,user,repo,tokey))
  }

  protected def messageHandler = {
    case a:Issue => treat(a)
    case a:String => treat(Issue("Erro",a))
    case _ => 
  }
}


case class Issue(title:String,body:String)