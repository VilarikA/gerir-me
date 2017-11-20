module("Login ",{
  setup : function(){
    S.open('/login')
  }
});

test("Simple login",function(){
  S('#user').exists().click().type("mateus");
  S('#password').exists().click().type("1234");
  S('#company').exists().click().type("8");
  S(".primary").exists().click();
  // wait until we have some results
  S('#calendar').visible(function(){
    equal(S(".user").text(),"","Ao abrir a agenda usuario nao vem selecionado!")
  })
});