package code
package model 

import net.liftweb._ 
import mapper._ 
import net.liftweb.common._
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 
import net.liftweb.widgets.gravatar.Gravatar
import code.util._
import _root_.java.util.Calendar
import _root_.java.util.Date

class PayrollEvent extends  Audited[PayrollEvent] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy with NameSearchble[PayrollEvent] with ActiveInactivable[PayrollEvent]{
    def getSingleton = PayrollEvent
    override def updateShortName = false

    object isCommition_? extends MappedBoolean(this){
        override def dbColumnName = "iscommition"
    }

    object isAdvance_? extends MappedBoolean(this){//adiantamento//
        override def dbColumnName = "isadvance"
    }

    object isLiquid_? extends MappedBoolean(this){
        override def dbColumnName = "isliquid"
    }

    object repeat_? extends MappedBoolean(this){
        override def dbColumnName = "repeat"
    }
    object eventType extends MappedInt(this){
    }    
    
}

object PayrollEvent extends PayrollEvent with LongKeyedMapperPerCompany[PayrollEvent] with OnlyActive[PayrollEvent] {
        lazy val PROVISION=0
        lazy val DISCOUNT =1
        val SQL_LIQUID = """
                select * from (
                select ba.short_name, bp.name, bpa.agency, bpa.account, 
                sum(round (to_number (to_char (value, '9999999.99999999'), '999999.9999'),2))  as valor_liquido
                from businesspatternpayroll bpr
                left join business_pattern bp on bpr.business_pattern = bp.id
                left join bpaccount bpa on bpa.business_pattern = bp.id
                left join bank ba on ba.id = bpa.bank
                where bpr.company = ? and date_c >= date(?) and date_c <= date(?) %s 
                group by ba.short_name, bp.name, bp.id, bpa.agency, bpa.account
                order by ba.short_name, bp.name, bp.id, bpa.agency, bpa.account) as data1 where valor_liquido between ? and ? %s;        
        """
        val SQL_PAYSHIP = """select pe.name,bppr.qtd,bppr.value,0.00 as discount from      
            businesspatternpayroll bppr 
            inner join business_pattern bp on(bp.id = bppr.business_pattern)
            inner join payrollevent pe on (pe.id=bppr.event)
            where pe.eventtype=0 and business_pattern = ? and date_c between date(?) and date(?)
            union all 
            select pe.name,bppr.qtd, 0.00, bppr.value as discount
            from      
            businesspatternpayroll bppr 
            inner join business_pattern bp on(bp.id = bppr.business_pattern)
            inner join payrollevent pe on (pe.id=bppr.event)
            where pe.eventtype=1  and business_pattern = ? and date_c between date(?) and date(?)
            """ 
}

