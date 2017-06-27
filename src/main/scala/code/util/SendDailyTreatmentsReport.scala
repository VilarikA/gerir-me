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
}


