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
import java.util.Date;
import net.liftweb._
import Helpers._
import common._
import mapper._
import code.model._
import code.util._
import code.service._
import code.service.PaymentService._
import code.actors._

/*
class PaymenteServiceTestSpecsAsTest extends JUnit4(PaymenteServiceTestSpecs)
object PaymenteServiceTestSpecsRunner extends ConsoleRunner(PaymenteServiceTestSpecs)

object PaymenteServiceTestSpecs extends Specification with Contexts {
		  // run any block of code in a Lift session
		  val session = new LiftSession("", StringHelpers.randomString(20), Empty)
		  def inSession(a: =>Any) = {
		    S.initIfUninitted(session) { a }
		  }
		  // function to create and log-in a user
		  def loginUser = inSession {
				val compan = Company.create; compan.save; val use = User.create.company(compan); use.save; AuthUtil << use
				UserCreateActors.createPaymentFormsStart(compan)
		  }
		  // specify what to do before/after each example
		  // specify that each example must run in the context of a session
		  new SpecContext {
		    beforeExample {
		      /* setup db here */
		      loginUser
		    }
		    afterExample { /* teardown db here */}	
		    aroundExpectations(inSession(_))
		  }
  " PaymenteService calls " should {
  		    doFirst  { InMemoryDB.init;}  

	    " process simple payment " in {
	    	val pr = PaymentRequst(List(),List(),"1","01/01/2011","3");
	    	processPaymentRequst(pr).id.is.toInt must be >= 1
	     	
	    }

	    " process simple payment whith 1 treatment" in {
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(),false,0)),List(),"1","01/01/2011","3");
	    	processPaymentRequst(pr).treatments.size must_== 1	     	
	    }

	    " process simple payment whith 2 treatment" in {
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(),false,0),TreatmentDTO(1,1,"open",List(),false,0)),List(),"1","01/01/2011","3");
	    	processPaymentRequst(pr).treatments.size must_== 2     	
	    }

	    " process simple payment whith 3 treatment" in {
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(),false,0),TreatmentDTO(1,1,"open",List(),false,0), TreatmentDTO(1,1,"open",List(),false,0)),List(),"1","01/01/2011","3");
	    	processPaymentRequst(pr).treatments.size must_== 3     	
	    }	 

	    " process simple payment whith 3 treatment with 1 removed" in {
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(),false,0),TreatmentDTO(1,1,"open",List(),false,0), TreatmentDTO(1,1,"open",List(),true,0)),List(),"1","01/01/2011","3");
	    	processPaymentRequst(pr).treatments.size must_== 2     	
	    }	 	 


	    " process simple payment whith 3 treatment with 2 removed" in {
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(),false,0),TreatmentDTO(1,1,"open",List(),true,0), TreatmentDTO(1,1,"open",List(),true,0)),List(),"1","01/01/2011","3");
	    	processPaymentRequst(pr).treatments.size must_== 1   	
	    }	 


	    " process simple payment whith 1 treatment and 1 payment" in {
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(),false,0)),List(PaymentDTO(1,0.0.toDouble,false,null)),"1","01/01/2011","3");
	    	processPaymentRequst(pr).details.size must_== 1
	    }

	    " process simple payment whith 1 treatment and 1 payment with value 10.00" in {
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(),false,0)),List(PaymentDTO(1,0.0.toDouble,false,null)),"1","01/01/2011","3");
	    	processPaymentRequst(pr).details(0).value.is must_== 0.00
	    }	 

	    " process simple payment whith 1 treatment and 2 payment" in {
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(),false,0)),List(PaymentDTO(1,0.0.toDouble,false,null),PaymentDTO(1,0.0.toDouble,false,null)),"1","01/01/2011","3");
	    	processPaymentRequst(pr).details.size must_== 2
	    }

	    " process simple payment whith 1 treatment and 2 payment each one with value of 10.0 " in {
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(),false,0)),List(PaymentDTO(1,0.0.toDouble,false,null),PaymentDTO(1,0.0.toDouble,false,null)),"1","01/01/2011","3");
	    	processPaymentRequst(pr).details.size must_== 2
	    	processPaymentRequst(pr).details(0).value.is must_== 0.0
	    	processPaymentRequst(pr).details(1).value.is must_== 0.0
	    }	    

	    " process simple payment whith 1 treatment and 2 payment each one with value of 10.0, with one payment removed " in {
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(),false,0)),List(PaymentDTO(1,0.0.toDouble,false,null),PaymentDTO(1,0.0.toDouble,true,null)),"1","01/01/2011","3");
	    	processPaymentRequst(pr).details.size must_== 1
	    }	    
	    

	    " process simple payment whith 1 treatment and 1 activity with value of 10.00 " in {
	    	val a = Activity.create
	    	a.salePrice(10.0)
	    	a.save
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(ActivityDTO(a.id.is.toInt,"activity",0,a.salePrice.is.toDouble,false)),false,0)),List(PaymentDTO(1,10.0.toDouble,false,null)),"1","01/01/2011","3");
	    	processPaymentRequst(pr).value.is must_== 10.0
	    }
	    
	    " process simple payment whith 1 treatment and 2 payment each one with value of 10.0 validate final " in {
	    	val a = Activity.create
	    	a.salePrice(20.0)
	    	a.save	    	
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(ActivityDTO(a.id.is.toInt,"activity",0,a.salePrice.is.toDouble,false)),false,0)),List(PaymentDTO(1,10.0.toDouble,false,null),PaymentDTO(1,10.0.toDouble,false,null)),"1","01/01/2011","3");
	    	processPaymentRequst(pr).details.size must_== 2
	    	processPaymentRequst(pr).details(0).value.is must_== 10.00
	    	processPaymentRequst(pr).details(1).value.is must_== 10.00
	    	processPaymentRequst(pr).value.is must_== 20.00
	    }

	    " process simple payment whith 1 treatment and 1 activity with value of 20.00 and payments total equals 10.00 PaymentIsNotEnough" in {
	    	val a = Activity.create
	    	a.salePrice(20.0)
	    	a.save
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(ActivityDTO(a.id.is.toInt,"activity",0,a.salePrice.is.toDouble,false)),false,0)),List(PaymentDTO(1,10.0.toDouble,false,null)),"1","01/01/2011","3");
	    	processPaymentRequst(pr) must throwAn[PaymentIsNotEnough]
	    }  	 

	    " process simple payment whith 1 treatment and 1 activity with value of 20.00 and payments total equals 30.00" in {
	    	val a = Activity.create
	    	a.salePrice(20.0)
	    	a.save
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(ActivityDTO(a.id.is.toInt,"activity",0,a.salePrice.is.toDouble,false)),false,0)),List(PaymentDTO(1,30.0.toDouble,false,null)),"1","01/01/2011","3");
	    	processPaymentRequst(pr).value.is must_== 20.00
	    }
	    
	    " process payment whith 1 treatment with 1 activity of value of 20.00 and payments total equals 30.00 in 2 payments one in Card and other in Money" in {
	    	val a = Activity.create
	    	a.salePrice(20.0)
	    	a.save
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(ActivityDTO(a.id.is.toInt,"activity",0,a.salePrice.is.toDouble,false)),false,0)),List(PaymentDTO(3,10.0.toDouble,false,null),PaymentDTO(1,20.0.toDouble,false,null)),"1","01/01/2011","3");
	    	processPaymentRequst(pr).value.is must_==20.00
	    	processPaymentRequst(pr).details(0).value.is must_==10.00
	    	processPaymentRequst(pr).details(1).value.is must_==10.00
	    }
  		  	
	    " process payment whith 1 treatment with 1 activity of value of 20.00 and payments total equals 30.00 in 2 payments one in Money and other in Card" in {
	    	val a = Activity.create
	    	a.salePrice(20.0)
	    	a.save
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(ActivityDTO(a.id.is.toInt,"activity",0,a.salePrice.is.toDouble,false)),false,0)),List(PaymentDTO(1,10.0.toDouble,false,null),PaymentDTO(3,20.0.toDouble,false,null)),"1","01/01/2011","3");
	    	val p = processPaymentRequst(pr)
	    	p.value.is must_==20.00
	    	p.details(0).value.is must_==0.00
	    	p.details(1).value.is must_==20.00
	    }

	    " Process payment whith 1 treatment with 1 activity of value of 20.00 and payments total equals 30.00 in 2 payments one in Card and other in Card " in {
	    	val a = Activity.create
	    	a.salePrice(20.0)
	    	a.save
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(ActivityDTO(a.id.is.toInt,"activity",0,a.salePrice.is.toDouble,false)),false,0)),List(PaymentDTO(3,10.0.toDouble,false,null),PaymentDTO(3,20.0.toDouble,false,null)),"1","01/01/2011","3");
	    	val p = processPaymentRequst(pr)  must throwAn[PaymentTypeNotAvailableToReturn]
	    }

		"Payment percend in money" in {
	    	val a = Activity.create
	    	a.salePrice(20.0)
	    	a.save
	    	val pr = PaymentRequst(List(TreatmentDTO(1,1,"open",List(ActivityDTO(a.id.is.toInt,"activity",0,a.salePrice.is.toDouble,false)),false,0)),List(PaymentDTO(1,10.0.toDouble,false,null),PaymentDTO(3,10.0.toDouble,false,null)),"1","01/01/2011","3");
	    	val p = processPaymentRequst(pr)
	    	p.value.is must_==20.00
	    	p.details(0).value.is must_==10.00
	    	p.details(1).value.is must_==10.00
	    	
	    }

	}
}*/