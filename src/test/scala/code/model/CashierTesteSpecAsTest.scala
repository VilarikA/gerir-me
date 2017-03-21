/*package code
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
import code.actors._
import code.util._

class CashierTestSpecsAsTest extends JUnit4(CashierTestSpecs)
object CashierTestSpecsRunner extends ConsoleRunner(CashierTestSpecs)

object CashierTestSpecs extends Specification {
  " Cashier operations " should {

	    doFirst  { 
	    	InMemoryDB.init 
			val compan = Company.create; compan.save; val use = User.create.company(compan); use.save; AuthUtil << use
			UserCreateActors.createPaymentFormsStart(compan)	    	
	    }  
	    def moneyId = PaymentType.PaymentMoneyIds(1)
	    " Open first  Cashier " in {
	    	val company = Company.create
	    	company.save
	     	val c = Cashier.open openerAt(new Date()) startValue(10) from(company)
	     	c.idForCompany.is must_==1
	    }

	    " Open first  Cashier this is open" in {
	    	val company = Company.create
	    	company.save
	     	val c = Cashier.open openerAt(new Date()) startValue(10) from(company)
	     	c.status.is must_== Cashier.CashierStatus.Open
	    }	

	    " Open Cashier and close afer" in {
	    	val company = Company.create
	    	company.save
	     	val c = Cashier.open openerAt(new Date()) startValue(10) from(company)
	     	c.close
	     	c.status.is must_== Cashier.CashierStatus.Closed
	    }	
	    
	    " Open Cashier and close afer 1 payment" in {
	    	val company = Company.create
	    	company.save
	     	val c = Cashier.open openerAt(new Date()) startValue(10) from(company)
	     	val detail = PaymentDetail.create.value(10.0).typePayment(1.toLong)
	     	val p1 = Payment.create.value(10.00).cashier(c)
	     	detail.payment(p1)
	     	p1.save
	     	detail.save
	     	c.close
	     	c.paidValue must_== 10.00
	     	c.endValue.is must_== 20.00
	    }

	    def savePayment(value:BigDecimal,c:Cashier) = {
			val detail = PaymentDetail.create.value(value).typePayment(1.toLong)
	     	val p1 = Payment.create.value(value).cashier(c)
	     	detail.payment(p1)
	     	p1.save
	     	detail.save
	    }

	    " Open Cashier and close afer 3 payment" in {
	    	val company = Company.create
	    	company.save
	     	val c = Cashier.open openerAt(new Date()) startValue(10) from(company)
	     	savePayment(10.0,c)
	     	savePayment(10.0,c)
	     	savePayment(10.5,c)
	     	c.close
	     	c.endValue.is must_== 40.50
	    }
	    " Open Cashier and close afer 3 payment and 1 Retreat" in {
	    	val company = Company.create
	    	company.save
	     	val c = Cashier.open openerAt(new Date()) startValue(10) from(company)
	     	savePayment(10.0,c)
	     	savePayment(10.0,c)
	     	savePayment(10.5,c)
	     	CashierRetreat.create.value(10.0).cashier(c).obs("Teste supitante").save
	     	c.close
	     	c.endValue.is must_== 30.50
	    }	    

	    " Open 2  Cashier " in {
	    	val company = Company.create
	    	company.save
	     	val c = Cashier.open openerAt(new Date()) startValue(10) from(company)
	     	val c2 = Cashier.open openerAt(new Date()) startValue(10) from(company)
	     	c2.idForCompany.is must_==2
	    }	    
	}
}
*/