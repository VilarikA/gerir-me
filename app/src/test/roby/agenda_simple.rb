require 'rubygems'
require 'watir' 
require "watir-webdriver"
require "rspec"
require "./testUtil"

describe "#Cadastrando agendamento simples" do
  it "Cadastrando agendamento simples" do
  	browser = getBrowser
  	login(browser,"mateus","1234","8")
    browser.execute_script("$('#treatment_add').modal({'show':true,'keyboard':true,'backdrop':true});getActivities();")
    command = Random.rand(1999).to_s
    sleep(2)
    browser.text_field(:id, "command_treatment").set command
    browser.select_list(:id, "user_treatment").select_value '2'
    browser.text_field(:id, "date_treatment").set browser.text_field(:id, "data_calendar").value
    browser.text_field(:id, "hour_treatment").set "08:00"
    browser.text_field(:id, "cutomer_id_treatment").set "129"
    browser.button(:id, "treatment_div").click
    browser.select_list(:id, "activitys").select_value "11301"
    browser.button(:id, "add_detail_button").click
    browser.goto($url+"/financial_simple/register_payment?command="+command)

    browser.button(:class, "b_payment").click
    sleep(1)
    browser.button(:class, "b_payment_finalize").click

    browser.close
  end
end