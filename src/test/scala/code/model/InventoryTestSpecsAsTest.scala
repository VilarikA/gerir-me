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

class InventoryTestSpecsAsTest extends JUnit4(InventoryTestSpecs)
object InventoryTestSpecsRunner extends ConsoleRunner(InventoryTestSpecs)

object InventoryTestSpecs extends Specification  with Contexts  {
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
  " Inventory Movement " should {

    doFirst  { InMemoryDB.init }  

    " Add 10 bananas and remove 9 banana of the Invetory " in {
      var p = Product.create
      p.name("Banana")
      p.save
      val movement1 = InventoryMovement.create
      movement1.product(p)
      movement1.amount(10)
      movement1.typeMovement(InventoryMovement.InventoryMovementType.In)
      movement1 from AuthUtil.unit

      val movement = InventoryMovement.create
      movement.product(p)
      movement.amount(9)
      movement.typeMovement(InventoryMovement.InventoryMovementType.Out)
      movement from AuthUtil.unit

      p = Product.findByKey(p.id).get
      p.currentStock  must_== 1
    }

    " Add 10 bananas and remove 10 banana of the Invetory " in {
      var p = Product.create
      p.name("Banana")
      //p.currentStock(10)
      p.save
      val movement1 = InventoryMovement.create
      movement1.product(p)
      movement1.amount(10)
      movement1.typeMovement(InventoryMovement.InventoryMovementType.In)
      movement1 from AuthUtil.unit

      p.currentStock  must_== 10

      val movement = InventoryMovement.create
      movement.product(p)
      movement.amount(9)
      movement.typeMovement(InventoryMovement.InventoryMovementType.Out)
      movement from AuthUtil.unit
      p = Product.findByKey(p.id).get
      p.currentStock  must_== 1
    }

    " Add 10 bananas and remove 11 banana of the Invetory " in {
      var p = Product.create
      p.name("Banana")
      p.save
      var movement = InventoryMovement.create
      movement.product(p)
      movement.amount(11)
      movement.typeMovement(InventoryMovement.InventoryMovementType.Out)
      movement from AuthUtil.unit must throwAn[InsufficientInventoryException]
    } 
    
    " Add 10 bananas and add more 11 banana of the Invetory " in {
      var p = Product.create
      p.name("Banana")
      p.save
      InventoryMovement.create.product(p).amount(10).typeMovement(InventoryMovement.In).from(AuthUtil.unit)
      var movement = InventoryMovement.create
      movement.product(p)
      movement.amount(11)
      movement.typeMovement(InventoryMovement.In)
      movement from AuthUtil.unit
      p = Product.findByKey(p.id).get
      p.currentStock  must_== 21      
    }

    " Add 10 bananas and add more 0 banana of the Invetory " in {
      var p = Product.create
      p.name("Banana")
      p.save
      var movement = InventoryMovement.create
      movement.product(p)
      movement.amount(0)
      movement.typeMovement(InventoryMovement.InventoryMovementType.In)
      movement from AuthUtil.unit
      p = Product.findByKey(p.id).get
      p.currentStock  must_== 0
    }

    " Add 10 bananas and add more -1 banana of the Invetory " in {
      var p = Product.create
      p.name("Banana")
      p.save
      var movement = InventoryMovement.create
      movement.product(p)
      movement.amount(-1)
      movement.typeMovement(InventoryMovement.InventoryMovementType.In)
      movement from AuthUtil.unit must throwAn[InvalidAmountInventoryException]
    }    

    " Add 10 bananas and remove 9 banana of the Invetory with DSL " in {
      var bananas = Product.create
      bananas.name("Banana")
      bananas.save
      InventoryMovement.create.product(bananas).amount(10).typeMovement(InventoryMovement.In).from(AuthUtil.unit)
      remove(9) item bananas from AuthUtil.unit
      bananas = Product.findByKey(bananas.id).get
      bananas.currentStock  must_== 1
    }



    " Add 10 bananas and add more 9 banana of the Invetory with DSL" in {
      var bananas = Product.create
      bananas.name("Banana")
      bananas.save
      add(9) item bananas from AuthUtil.unit
      bananas = Product.findByKey(bananas.id).get
      bananas.currentStock  must_== 9
    } 


    " Add 10 bananas and add more 9 banana of the Invetory with DSL whith OBS" in {
      var bananas = Product.create
      bananas.name("Banana")
      bananas.save
      add(9) item bananas obs("Teste") from AuthUtil.unit
      bananas = Product.findByKey(bananas.id).get
      bananas.currentStock  must_== 9
    }        
  }
}
