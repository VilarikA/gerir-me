package code
package snippet

import scala.xml.{NodeSeq, Text, Elem}

import java.io.{File,FileOutputStream,ByteArrayInputStream}
import javax.imageio._
import code.util._

import net.liftweb._
import util._
import common._
import mapper._

import Helpers._
import http._
import S._
import js.JsCmds.Noop

import code.model._



class UploadImage extends SnippetUploadImage {
  override def resize_? = false
  def setImageToEntity(homeName:String, thumbName:String){
    Customer.findByKey(S.param("id").get.toLong).get.image(homeName).imagethumb(thumbName).save
  }
  def imageFolder:String = Customer.imagePath
  def thumbToShow:NodeSeq = Customer.findByKey(S.param("id").get.toLong).get.thumb("128")
}