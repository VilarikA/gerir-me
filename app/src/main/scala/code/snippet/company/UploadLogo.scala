package code
package snippet

import code.util._

import net.liftweb._
import util._
import common._
import mapper._

import Helpers._
import http._
import S._
import js.JsCmds.Noop



class UploadLogo extends SnippetUploadImage {
  
  def setImageToEntity(homeName:String, thumbName:String){
    AuthUtil.company.image(homeName).imagethumb(thumbName).save
  }
  def imageFolder = {
    AuthUtil.company.imagePath;
  }
  def thumbToShow = AuthUtil.company.thumb
}