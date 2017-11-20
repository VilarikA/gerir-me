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
import InventoryMovement._

class CrudsTestSpecsAsTest extends JUnit4(CrudsTestSpecs)
object CrudsTestSpecsRunner extends ConsoleRunner(CrudsTestSpecs)

object CrudsTestSpecs extends Specification  with Contexts  {
        // run any block of code in a Lift session
      val session = new LiftSession("", StringHelpers.randomString(20), Empty)
      def inSession(a: =>Any) = {
        S.initIfUninitted(session) { a }
      }
      // function to create and log-in a user
      def loginUser = inSession {
        val compan = Company.create; compan.save;
        val unit = CompanyUnit.create
        unit.save
        val use = User.create.company(compan).unit(unit);
        use.save;
        AuthUtil << use
        AuthUtil << unit
      }

      new SpecContext {
        beforeExample {
          loginUser
        }
        afterExample { }  
        aroundExpectations(inSession(_))
      }
  " Customer crude" should {

    doFirst  { InMemoryDB.init }  

    "Create a simple Customer without problems" in {
      val a  = Customer.createInCompany.name("mateus").document("Nine");
      a.save
      a.id.is.toInt must be >= 1
    }

    "Create a simple Customer without problems and this is a customer" in {
      val a  = Customer.createInCompany.name("mateus").document("Nine");
      a.save
      a.is_customer_?.is must_== true
    }

    "Create a simple Customer without problems and search_name is due" in {
      val a  = Customer.createInCompany.name("Mateus").document("Nine");
      a.save
      a.search_name.is must_== "mateus"
    }    

    "Create a simple Customer with 'extranho' name and seach simple name find result" in {
      val a  = Customer.createInCompany.name("EscaLaFaBeTuSa").document("Nine");
      a.save
      Customer.searchCustomer(0,"escala","",10).size  must be >= 1
    }

  }
}
