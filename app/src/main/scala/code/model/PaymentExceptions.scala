package code
package model 

import net.liftweb._ 
import scala.xml._
import mapper._ 
import http._ 
import SHtml._ 
import util._
import code.util._
import _root_.java.math.MathContext
import net.liftweb.mapper.DB
import net.liftweb.common._
import java.util.Date


class PaymentIsNotEnough extends RuntimeException
class PaymentCustomerIsNotAUser(val paymentTypeName:String,val customerName:String) extends RuntimeException(paymentTypeName:String)
class ProductNotAllowSaleByUser(message:String) extends RuntimeException(message)
class PaymentDeliveryWithoutDetails extends RuntimeException
class InvalidPaymentValue extends RuntimeException
class NotAllowCommandRepeat extends RuntimeException
class PaymentNotFound extends RuntimeException
class CashierIsClosed extends RuntimeException
case class CustomerNotHasValueInCredit(value:Double) extends RuntimeException
case class HaveDeliveriesUsed(deliveriesUsed:List[DeliveryDetail]) extends RuntimeException
class CommandIsNotValid extends RuntimeException
class PaymentTypeNotAvailableToReturn extends RuntimeException
class PaymentDeliveryNotEnough(message:String) extends RuntimeException(message)
case class PaymentMonthlyNotEnough(message:String) extends RuntimeException(message)
class SessionValueWrong(message:String) extends RuntimeException(message)
