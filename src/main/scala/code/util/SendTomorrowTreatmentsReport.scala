package code
package util 

import org.quartz._;
import org.quartz.impl._;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import java.util.Date;
import code.model._
import code.service._
import _root_.java.util.Calendar


class SendTomorrowTreatmentsReport extends org.quartz.Job with net.liftweb.common.Logger{
    def  execute(context:JobExecutionContext){
        SendTomorrowTreatmentsReport.send 
    }
}

object SendTomorrowTreatmentsReport{
    def tomorrow = {
      val cal = Calendar.getInstance()
      cal.setTime(new Date()); 
      cal.add(java.util.Calendar.DATE, 1);
      cal.getTime()
    }
    def send = {
        code.daily.DailyReport.sendTreatmentReport (tomorrow, Company.notifyForTomorrow);
    }
}


