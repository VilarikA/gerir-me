require 'rubygems'
require 'watir' 
require "watir-webdriver"
require "rspec"
require "./testUtil"

describe "#Logun testes" do
  it "Sucesso quando mateus senha 1234 empresa 8" do
  	browser = getBrowser
  	login(browser,"mateus","1234","8")
    browser.select_list(:id, "units").value.should eq("1")
    browser.close
  end
  it "Falha quando mateus senha 12345 empresa 8" do
  	browser = getBrowser
  	login(browser,"mateus","12345","8")
    browser.li(:class, "alert-message").text.should eq("Usuario ou senha invalida!")
    browser.close
  end
  it "Falha quando mateus1 senha 1234 empresa 8" do
  	browser = getBrowser
  	login(browser,"mateus1","1234","8")
    browser.li(:class, "alert-message").text.should eq("Usuario ou senha invalida!")
    browser.close
  end
  it "Falha quando sem empresa" do
  	browser = getBrowser
  	login(browser,"mateus1","1234","")
    browser.li(:class, "alert-message").text.should eq("O campo empresa deve ser numerico!")
    browser.close
  end
  it "ja preenchi empresa fica empresa" do
  	browser = getBrowser
  	login(browser,"mateus1","","8")
    browser.text_field(:id, "_companyForLogin").value.should eq("8")
    browser.text_field(:id, "_userForLogin").value.should eq("mateus1")
    browser.close
  end  
  it "Falha quando mateus senha 1234 empresa 9" do
  	browser = getBrowser
  	login(browser,"mateus1","1234","9")
    browser.li(:class, "alert-message").text.should eq("Usuario ou senha invalida!")
    browser.close
  end    
end