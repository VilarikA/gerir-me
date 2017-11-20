
  $(function() {
    $("#product").productSearch({
      createName: false,
      iconElement: ".add-on",
      userThuch: true,
      userFieldSelector: '#user'
    });
    setTimeout(function(){    
  		if($("#add_td").length > 0){
  	   	$("#add_td").attr("href", $("#add_td").attr("href").replace("##", $("#offsale").val()));
  		}
  		if($("#add_td1").length > 0){
  		  $("#add_td1").attr("href", $("#add_td1").attr("href").replace("##", $("#offsale").val()));
  		}

      $("#product").change();
      
    },600);
  });
