package code
package util 

import org.quartz._;
import org.quartz.impl._;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import java.util.Date;
import code.model._
import code.service._


class SendDailyTreatmentsReport extends org.quartz.Job with net.liftweb.common.Logger{
    def  execute(context:JobExecutionContext){
        SendDailyTreatmentsReport.send 
    }
}

object SendDailyTreatmentsReport{
    def send = {
        code.daily.DailyReport.sendTreatmentReport (new Date(), Company.notifyForToday1Am);
    }
/*
    def send (date: Date, notify: Int) = { 
        Company.findAllActiveToSendNotification (notify).foreach( company => {
            if (company.customerNotification == notify) {
                val customers = TreatmentService.customersWithTreatments(company, date)
                customers.foreach((customer)=>{
                    var cunit = if (!customer.unit.isEmpty) {
                            CompanyUnit.findByKey(customer.unit).get
                        } else {
                            // unidade vilarika
                            CompanyUnit.findByKey(7).get
                        }
                    if(customer.email.is != ""){
                        val mail = code.daily.DailyReport.treatmentsTodayCustomer(customer,company, date)
                        //EmailUtil.sendMailTo(customer.email.is.toString,mail,"Atendimento "+company.name.is,company)
                        EmailUtil.sendMailToCustomer(cunit,
                            Company.findByKey (customer.company).get, 
                            customer.email.is, mail, "Atendimento "+company.name.is, 
                            customer.id.is)
                    }
                })
            }
            if (company.userNotification == notify) {
                val users = TreatmentService.usersWithTreatments(company, date)
                users.foreach((user)=>{
                    var uunit = if (!user.unit.isEmpty) {
                            CompanyUnit.findByKey(user.unit).get
                        } else {
                            // unidade vilarika
                            CompanyUnit.findByKey(7).get
                        }
                    if(user.email.is != ""){
                        val mail = code.daily.DailyReport.treatmentsTodayUser(user,company, date)
                        //EmailUtil.sendMailTo(user.email.is.toString,mail,"Agenda "+company.name.is,company)
                        EmailUtil.sendMailToCustomer(uunit,
                            Company.findByKey (user.company).get, 
                            user.email.is, mail, "Agenda "+company.name.is, 
                            user.id.is)
                    }
                })
            }
        })        
    }
*/
}


