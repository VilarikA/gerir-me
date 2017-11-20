package code.util  
  
import net.liftweb.mapper._  
import net.liftweb.common._  
import net.liftweb.util._ 
import code.model._
import code.service._
import java.sql._  
import bootstrap.liftweb._
  
object InMemoryDB {
  
 val vendor = new MyStandardDBVendor("org.postgresql.Driver",   
    "jdbc:postgresql://localhost/e_belle_ligth_test", Full("mateus"), Full("775072"))  
    Logger.setup = Full(net.liftweb.util.LoggingAutoConfigurer())  
    Logger.setup.foreach { _.apply() }  
 def l(o:AnyRef){
   
 }
 def init {
  DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)  
  DB.runUpdate("""
      delete from account;
      delete from accountcategory;
      delete from accountpayable;
      delete from activitytype;
      delete from bpconsideration;
      delete from business_pattern;
      delete from busyevent;
      delete from car;
      delete from cashier;
      delete from cheque;
      delete from commision;
      delete from company;
      delete from companyunit;
      delete from deliverycontrol;
      delete from deliverydetail;
      delete from imagecustomer;
      delete from inventorycurrent;
      delete from inventorymovement;
      delete from locationhistory;
      delete from logmailsend;
      delete from logobj;
      delete from payment;
      delete from paymentdetail;
      delete from product where productclass in (0,1);
      delete from productbom;
      delete from productline;
      delete from productlinetag;
      delete from producttype;
      delete from recurrence;
      delete from treatment;
      delete from treatmentdetail;
      delete from useractivity;
      delete from usergroup;
      delete from usermessage;
      delete from usermessagelogread;
      delete from workhouer;
    """,Nil)  
  //Schemifier.destroyTables_!!(l _,  Product,InventoryMovement,Company,Cashier,Payment,PaymentDetail,Treatment,TreatmentDetail,Activity,PaymentType,CashierRetreat,CompanyUnit, InventoryCurrent, Commision, AuditMapper, User)
  //Schemifier.schemify(true, l _,  Product,InventoryMovement,Company,User,Cashier,Payment,PaymentDetail,Treatment,TreatmentDetail,Activity,PaymentType,CashierRetreat,CompanyUnit, InventoryCurrent, Commision, AuditMapper)

  val c = Company.create
  val unit = CompanyUnit.create
  c.save
  unit.save
  //AuthUtil << unit
  val u = User.create.company(c).unit(unit)
  u.save
  AuthUtil << u
  CommisionQueeue.start
  DeliveryQueeue.start
 }  
 def simple_init {
  DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)  
  Schemifier.destroyTables_!!(l _,  Company,CompanyUnit, AuditMapper, Customer)
  Schemifier.schemify(true, l _,  Company,CompanyUnit, Customer, AuditMapper,User)
  val c = Company.create
  val unit = CompanyUnit.create
  c.save
  unit.save
  //AuthUtil << unit
  val u = User.create.company(c).unit(unit)
  u.save
  AuthUtil << u
 }   
  
 def shutdown {  
  // TODO: figure out if anything goes here  
 }  
}  