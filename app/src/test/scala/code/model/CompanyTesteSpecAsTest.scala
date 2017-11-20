package code
package model

import org.specs._
import org.specs.runner.JUnit4
import org.specs.runner.ConsoleRunner
import net.liftweb._
import http._
import net.liftweb.util._
import net.liftweb.common._
import org.specs.matcher._
import org.specs.specification._
import Helpers._
import lib._

import net.liftweb._
import Helpers._
import common._
import mapper._
import code.model._
import code.util._

class CompanyTesteSpecAsTest extends JUnit4(CompanyTesteSpecs)
object CompanyTesteSpecAsTestRunner extends ConsoleRunner(CompanyTesteSpecs)
object CompanyTesteSpecs extends Specification {
    
    " Company recorde" should {  
      doFirst  { InMemoryDB.simple_init }  
      " Store company  " in {
        var c = Company.create;
        c.name("asd")
        c.email("asdm@asdm.com.br")
        c.save
        c.id.is.toInt must be >= 1
      }
      
      "trow exception email validation " in {
        var c = Company.create;
        c.name("asd")
        c.email("asasdm.com.br")
        c.validate.size must_== 1
      }

      "trow exception email unic validation " in {
        var c = Company.create;
        c.name("asd")
        c.email("aasd@asasdm.com.br")
        c.save
        c = Company.create;
        c.name("asd2")
        c.email("aasd@asasdm.com.br")
        c.save
        c.validate.size must_== 1
      }      
    } 
}
