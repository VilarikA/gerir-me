$(()->
	$("#send").click(()-> 
						$.post(
							  "/system/createIssue",$("#form_issue").serializeObject(),(t)->
							  			if(t =='1')
							  				alert("Enviado com sucesso!")
							  			else
							  				alert("Erro ao enviar, tente novamente!")
							  	,
							  	"text"
							  )
					)
)