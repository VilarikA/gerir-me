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



class UploadMedia extends SnippetUploadImage {
  override def resize_? = false
  def setImageToEntity(imageName:String, thumbName:String)= {
    Media.createInCompany.image(imageName).imagethumb(thumbName).save
  }  

  def imageFolder = Media.imagePath
  def thumbToShow = <div></div>
}