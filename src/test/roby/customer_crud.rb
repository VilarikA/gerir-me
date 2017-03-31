require 'rubygems'
require 'watir' 
require "watir-webdriver"
require "rspec"
require "./testUtil"

def includCliente(browser,name,phone)
  	browser.link(:text, "Clientes").click
  	browser.link(:text, "Inserir novo").click
  	browser.text_field(:id, "name").set name
  	browser.text_field(:id, "phone").set phone
    browser.div(:id, "exposeMask").click
  	browser.form(:class, "crud_form").submit
  	browser.text_field(:id, "name").value.should eq(name)
  	browser.text_field(:id, "phone").value.should eq(phone)
end 
def searchClienteByName(browser,name)
	browser.goto($url)
  	browser.link(:text, "Clientes").click
  	browser.text_field(:class, "name_customer").set name
  	browser.button(:text, "Buscar").click
end

def searchClienteByPhone(browser,name)
	browser.goto($url)
  	browser.link(:text, "Clientes").click
  	browser.text_field(:class, "document").set name
  	browser.button(:text, "Buscar").click
end 

describe "#Cadastro de clientes" do
  it "Cadastrando cliente simples" do
  	browser = getBrowser
  	login(browser,"mateus","1234","8")
  	name = "mateus "+Time.now.to_s
  	phone = "(31) 99536408"
  	includCliente(browser,name,phone)
    browser.close
  end
  it "Cadastrando cliente simples e buscar pelo nome" do
  	browser = getBrowser
  	login(browser,"mateus","1234","8")
  	name = "mateus "+Time.now.to_s
  	phone = "(31) 99536408"
  	includCliente(browser,name,phone)   	
  	searchClienteByName(browser,name)
  	browser.link(:text, "Edit").click
  	browser.text_field(:id, "name").value.should eq(name)
  	browser.text_field(:id, "phone").value.should eq(phone)
  	browser.close
  end
  it "Cadastrando cliente simples e buscar pelo nome edita o telefone" do
  	browser = getBrowser
  	login(browser,"mateus","1234","8")
  	name = "mateus "+Time.now.to_s
  	phone = "(31) 99536408"
  	includCliente(browser,name,phone)   	
  	searchClienteByName(browser,name)
  	browser.link(:text, "Edit").click
  	browser.text_field(:id, "name").value.should eq(name)
  	browser.text_field(:id, "phone").value.should eq(phone)  	
  	phone  = "(31) 1111-1111"
  	browser.text_field(:id, "phone").set phone
  	browser.form(:class, "crud_form").submit
  	searchClienteByName(browser,name)
  	browser.link(:text, "Edit").click
  	browser.text_field(:id, "phone").value.should eq(phone)
  	searchClienteByName(browser,name)
  	browser.button(:class, "danger").click
  	browser.close
  end
  
end