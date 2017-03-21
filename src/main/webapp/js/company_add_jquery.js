$(function(){
	$("#companyAddForm").submit(function(){
		var message = "";
		var companyValidate = CompanyValidations.validate($('#contact').val(), $('#email').val(), $('#phone').val(), $("#name").val());
		if(!companyValidate.contact){
			message  += 'Existem caracteres especiais no nome do contato, remova-os antes de continuar!\n';
		}
		if(!companyValidate.contactLength){
			message  += 'Contato não pode conter menos de 4 caracteres!!\n';
		}
		if(!companyValidate.companyNameLength){
			message  += 'Nome da empresa não pode conter menos de 4 caracteres!!\n';
		}
		if(!companyValidate.email){
			message += 'E-mail inválido!\n';
		}
		if(!companyValidate.phone){
			message += 'Telefone inválido!\n';
		}
		if(message){
			alert(message);
		}
		return 	companyValidate.contact 
				&& companyValidate.email 
				&& companyValidate.phone 
				&& companyValidate.companyNameLength 
				&& companyValidate.contactLength;
	})
});