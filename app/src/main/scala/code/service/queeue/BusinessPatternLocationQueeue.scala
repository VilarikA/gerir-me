package code
package service

import code.model._
import code.util._


object BusinessPatternLocationQueeue extends Queeue[BusinessPatternQueeueDto]{
    override val queueName = "ebelle.business_pattern_location"
    def dequeeue(c:BusinessPatternQueeueDto){
    	val customer = Customer.findByKey(c.objId).get
        GoogleGeocoderUtil.geocoder(customer)
    }
}