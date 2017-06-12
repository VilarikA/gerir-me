var emailValidation = /^([a-z0-9._%\-+]+@(?:[a-z0-9\-]+\.)+[a-z]{2,4}$)/;
var nameInvalidChars = ["(",")","/","*","-",".",",","|","+","?",":","0","1","2","3","4","5","6","7","8" , "9"];
var CompanyValidations = {
	validate : function(contact, email, phone, companyName){
		var emailTest = function (email) {
			//alert ("vaiii " + email)
			var str_array = email.split(',');
			if (str_array.length == 1) {
				str_array = email.split(';');
			}
			//alert ("vaiii array " + str_array)
			for(var i = 0; i < str_array.length; i++) {
				if (!emailValidation.test(str_array[i])){
					return false;
				}
			}
			return true;
		}
		var emailValid = emailTest (email); //emailValidation.test(email);
		//var emailValid = emailValidation.test(email);
		var nameValid =  !nameInvalidChars.some(function(charapter){
			return contact.indexOf(charapter) != -1;
		});
		var contactLength =  contact.trim().length > 3;
		var companyNameLength =  companyName.trim().length > 3;
		var phoneValid =  phone.trim().length > 13;
		return {email: emailValid, contact : nameValid, phone : phoneValid, contactLength : contactLength,  companyNameLength: companyNameLength }
	}
};