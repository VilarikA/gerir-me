require 'rubygems'
require 'watir' 
require "watir-webdriver"
require "rspec"
require "./testUtil"

def includProfissional(browser,name,phone)
  	browser.link(:text, "Profissional").click
  	browser.link(:text, "Inserir novo").click
  	browser.text_field(:id, "name").set name
  	browser.text_field(:id, "phone").set phone
    browser.div(:id, "exposeMask").click
  	browser.form(:class, "crud_form").submit
  	browser.text_field(:id, "name").value.should eq(name)
  	browser.text_field(:id, "phone").value.should eq(phone)
end 
def searchByName(browser,name)
	browser.goto($url)
  	browser.link(:text, "Profissional").click
  	browser.text_field(:class, "name_customer").set name
  	browser.button(:text, "Buscar").click
end

def searchClienteByPhone(browser,name)
	browser.goto($url)
  	browser.link(:text, "Profissional").click
  	browser.text_field(:class, "document").set name
  	browser.button(:text, "Buscar").click
end 

describe "#Cadastro de profissionais" do
  it "Cadastrando profissional simples" do
  	browser = getBrowser
  	login(browser,"mateus","1234","8")
  	name = "mateus "+Time.now.to_s
  	phone = "(31) 99536408"
  	includProfissional(browser,name,phone)
    browser.close
  end
  it "Cadastrando profissional simples e buscar pelo nome" do
  	browser = getBrowser
  	login(browser,"mateus","1234","8")
  	name = "mateus "+Time.now.to_s
  	phone = "(31) 99536408"
  	includProfissional(browser,name,phone)   	
  	searchByName(browser,name)
  	browser.link(:text, "Edit").click
  	browser.text_field(:id, "name").value.should eq(name)
  	browser.text_field(:id, "phone").value.should eq(phone)
  	browser.close
  end
  it "Cadastrando profissional simples e buscar pelo nome edita o telefone" do
  	browser = getBrowser
  	login(browser,"mateus","1234","8")
  	name = "mateus "+Time.now.to_s
  	phone = "(31) 99536408"
  	includProfissional(browser,name,phone)   	
  	searchByName(browser,name)
  	browser.link(:text, "Edit").click
  	browser.text_field(:id, "name").value.should eq(name)
  	browser.text_field(:id, "phone").value.should eq(phone)  	
  	phone  = "(31) 1111-1111"
  	browser.text_field(:id, "phone").set phone
  	browser.form(:class, "crud_form").submit
  	searchByName(browser,name)
  	browser.link(:text, "Edit").click
  	browser.text_field(:id, "phone").value.should eq(phone)
  	searchByName(browser,name)
  	browser.button(:class, "danger").click
  	browser.close
  end

end