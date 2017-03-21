package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import code.util._
import net.liftweb.common.{Box,Full}


trait PerCity {
	self: BaseMapper =>
	object cityRef extends MappedLongForeignKey(this.asInstanceOf[MapperType], City){
		override def dbIndexed_? = true
		override def renderJs_? = false
		override def defaultValue = cityrefDefaultValue
	}
	object stateRef extends MappedLongForeignKey(this.asInstanceOf[MapperType], State){
		override def dbIndexed_? = true
		override def renderJs_? = false
		override def defaultValue = staterefDefaultValue
	}	
    def cityrefDefaultValue:Long = if(AuthUtil.? ){
          AuthUtil.unit.getPartner.cityRef.is
      }else{
          1821
      }
    def staterefDefaultValue:Long = if(AuthUtil.? ){
          AuthUtil.unit.getPartner.stateRef.is
      }else{
           1
      }          
}
object PerCity {
	val BELO_HORIZONTE = 1821
	val MINAS_GERAIS = 1
}

trait PerCompany {
	self: BaseMapper =>
	object company extends MappedLongForeignKey(this.asInstanceOf[MapperType],Company){
		override def dbIndexed_? = true
		override def renderJs_? = false
		override def defaultValue = {
			if(AuthUtil ? )
				AuthUtil.company.id.is
			else 
				0
		}		
	}
}

trait PerUnit { 
	self: BaseMapper =>
	object unit extends MappedLongForeignKey(this.asInstanceOf[MapperType],{CompanyUnit}){
		override def dbIndexed_? = true
		override def defaultValue = {
			if(AuthUtil ? )
				AuthUtil.unit.id.is
			else 
				0
		}
	}
}

trait OnlyCurrentCompany[A <: LongKeyedMapper[A]] {
	self: A with OnlyCurrentCompany[A] with PerCompany with MetaMapper[A] => 
	val DEFAULT_COMPANY_ID = 26l
	def findAllInCompany:List[A] = findAllInCompany()
	def findAllInCompany(params: QueryParam[A]*): List[A] = { 
		self.findAll(By(self.company,AuthUtil.company.id) :: params.toList :_*)
	}

	def countInCompany(params: QueryParam[A]*): Long = {
		self.count(By(self.company,AuthUtil.company.id) :: params.toList :_*)
	}
	def createInCompany = self.create.asInstanceOf[PerCompany].company(AuthUtil.company.id).asInstanceOf[A]

	def findAllInCompanyOrDefaultCompany:List[A] = findAllInCompanyOrDefaultCompany()
	def findAllInCompanyOrDefaultCompany(params: QueryParam[A]*): List[A] = {
		self.findAll(ByList[A,Long](self.company, List[Long](AuthUtil.company.id.is.toLong, DEFAULT_COMPANY_ID)) :: params.toList :_*)
	}	
}

trait OnlyCurrentUnit[A <: LongKeyedMapper[A]] { 
	self: A with OnlyCurrentCompany[A] with PerUnit with MetaMapper[A] => 
	def fildAllInUnit(params: QueryParam[A]*): List[A] = { 
		self.findAll(By(self.unit,AuthUtil.unit.id) :: params.toList :_*) 
	}

	def countInUnit(params: QueryParam[A]*): Long = { 
		self.count(By(self.unit,AuthUtil.unit.id) :: params.toList :_*) 
	}	
	
} 