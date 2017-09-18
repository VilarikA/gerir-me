package code
package api

import code.model._
import code.util._
import code.service._

import net.liftweb._
import common._
import http._
import rest._
import json._
import http.js._
import JE._
import net.liftweb.util.Helpers

import scala.xml._

import java.text.ParseException
import java.util.Date
import java.util.Calendar

import java.util.Random

import net.liftweb.json._
//implicit val formats = DefaultFormats // Brings in default date formats etc.

object SiteApi extends RestHelper with net.liftweb.common.Logger  {
	val DAY_IN_MILESECOUNDS = 86400000;
	serve {
		
		case "site" :: "cities" :: Nil JsonGet _ =>{
			JsArray(
				City.findAllToSiteWithUnit.map((c:City) => {
					JsObj(
						("id",c.id.is),
						("name",c.name.is),
						("url_name",c.nameToUrl)
					)
				})
			)
		}
		case "site" :: "companies" :: Nil JsonGet _ =>{
			JsArray(Company.findAllToSite .map( (company) =>{
				JsObj(
					("id",company.name.is),
					("name",company.name.is),
					("logo",company.logo_web)
				)
			}))
		}
		
		case "site" :: "unitByName" :: Nil JsonGet _ =>{
			val name = S.param("name").get
			var companyUnit = CompanyUnit.findAllBySiteName(name)
			JsObj(
					("id",companyUnit.name.is),
					("name",companyUnit.name.is),
					("logo",companyUnit.logo_web),
					("lat",companyUnit.getPartner.lat.is),
					("lng",companyUnit.getPartner.lng.is),
					("full_address",companyUnit.getPartner.full_address),
					("title",companyUnit.siteTitle.is),
					("siteName",companyUnit.name.is),
					("company",companyUnit.company.is),
					("description",companyUnit.siteDescription.is),
					("users",
						JsArray(
								companyUnit.findUserToSite.map( (user) =>{
									JsObj(
										("name", user.name.is),
										("logo",user.logo_web),
										("description", user.siteDescription.is)
									)
								})
							)
					),
					("media",
						JsArray(
								companyUnit.findMediaToSite.map( (user) =>{
									JsObj(
										("name", user.siteTitle.is),
										("logo",user.logo_web),
										("description", user.siteDescription.is)
									)
								})
							)
					),
					("activities",
						JsArray(
								companyUnit.findActivitiesToSite.map( (activity) =>{
									JsObj(
										("name", activity.siteTitle.is),
										("logo",activity.logo_web),
										("description", activity.siteDescription.is)
									)
								})
							)
					)
				)
		}
		case "site" :: "job_requisitions" :: Nil JsonGet _ =>{
			val rand = new Random(System.currentTimeMillis());
			val jobs = JobRequisition.findAllToSite map( (rand.nextInt(1000),_) ) sortBy(_._1) map(_._2)
			JsArray( jobs.map( (job) =>{
				JsObj(
					("id",job.name.is),
					("name",job.name.is),
					("logo",job.logo_web),
					("title",job.siteTitle.is),
					("siteName",job.name.is),
					("description",job.siteDescription.is),
					("obs",job.obs.is),
					("essential",job.essential.is),
					("wish",job.wish.is),
					("benefits",job.benefits.is)
				)

			}))
		    
		}
		case "site" :: "unities" :: Nil JsonGet _ =>{
			val cityName = S.param("cityName") openOr ""
			val rand = new Random(System.currentTimeMillis());
			val unitList:List[CompanyUnit] = if(cityName == ""){
								CompanyUnit.findAllToSite
							}else{
								CompanyUnit.findAllToSiteByCityName(cityName)
							}
			val unities = unitList.map( (rand.nextInt(1000),_) ) sortBy(_._1) map(_._2)
			
			JsArray( unities.map( (company) =>{
				JsObj(
					("id",company.name.is),
					("name",company.name.is),
					("logo",company.logo_web),
					("title",company.siteTitle.is),
					("siteName",company.name.is),
					("description",company.siteDescription.is)
				)
			}))
		}		
	}
}