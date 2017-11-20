package code
package util

import net.liftweb.http._
import net.liftweb.sitemap.Loc._
import net.liftweb.util.Helpers._
import net.liftweb.util._
import net.liftweb.common._
import S._
import code.model._

object TreeModelsUtil{
  def treatTree(parrents: List[TreeObject], accValue: Long): Long = {
     if (parrents.isEmpty) accValue
     else {
        val item = parrents.head
         val maxTree = item
                    .minTree(accValue)
                    .maxTree(treatTree(item.directyChilds, accValue + 1))
                    .saveWithoutUpdateTree
                    .maxTree 
        treatTree(parrents.tail, maxTree + 1)
    }
}
}

trait TreeObject {
  def directyChilds:List[TreeObject];
  def minTree(value:Long):TreeObject;
  def maxTree(value:Long):TreeObject;
  def minTree:Long;
  def maxTree:Long;
  def saveWithoutUpdateTree:TreeObject;
}