require 'rubygems'
require 'watir' 
require "watir-webdriver"
require "rspec"
$url = "http://localhost:7171"
def getBrowser
	return Watir::Browser.new :chrome
end

def login(browser,user,password,company)
	browser.goto($url)
	browser.text_field(:id, "_userForLogin").set(user)
	browser.text_field(:id, "_passwrodForLogin").set(password)
	browser.text_field(:id, "_companyForLogin").set(company)
	browser.button(:id, "_btnIn").click
end