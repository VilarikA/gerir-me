package code
package model 

import net.liftweb.http.js.JE._
import net.liftweb.http.js.JsExp
import net.liftweb._ 
import mapper._ 
import util._ 
import net.liftweb.util.{FieldError}
import code.util

import http._ 
import SHtml._ 
import util._
import util._ 

import net.liftweb.common.{Box,Full,Empty,Failure,ParamFailure}


/**
 * For cloning a mapper instance.
 * Copies everything from an existing instance to a new instance (except the primary key)
 * @tparam M The [[net.liftweb.mapper.Mapper]] type
 */
 trait CanClone[M <: KeyedMapper[_, M]] {
  self: KeyedMetaMapper[_, M] with KeyedMapper[_, M] =>

  def cloneInstance(in: M): M = {
    // the new instance
    val out = create

    // copy from existing instance to new instance
    def cp(bmf: BaseMappedField) {
      for (imf:BaseMappedField <- in.fieldByName(bmf.name); omf:BaseMappedField <- out.fieldByName(bmf.name)) omf.set(imf.get.asInstanceOf[omf.ValueType])
    }
    mappedFields.filterNot(bmf => columnPrimaryKey_?(bmf.name)).foreach(cp)

    out // return the new instance
  }

}
/**
 * The companion to [[com.myleadconverter.model.CanClone]]
 * @tparam M The [[net.liftweb.mapper.Mapper]] type
 */
 trait CanCloneThis[M <: KeyedMapper[_, M]] {
  self: KeyedMapper[_, M] {
    def getSingleton: KeyedMetaMapper[_, M] with KeyedMapper[_, M] with CanClone[M]
    } =>

    override def clone() = getSingleton.cloneInstance(this)
  }