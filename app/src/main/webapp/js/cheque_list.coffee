class Cheque

Cheque.getListFromServer = ()->
		$.get("/payment/getCheques",(results)->
				$("#grid tbody").html("");
				eval("results = "+results)
				total = 0
				for cheque in results
					total += cheque.value
					cheque.date = getDateBr(new Date(cheque.date))
					$("#grid tbody").append("<tr><td>#{cheque.id}</td><td>#{cheque.banc}</td><td>#{cheque.agency}</td><td>#{cheque.acount}</td><td>#{cheque.number}</td><td>#{(cheque.value).formatMoney()}</td><td>#{cheque.date}</td><td>#{cheque.customer}</td><td><a href='#' class='_popover danger marker' rel='popover' data-content='Marcar como recebido!'  data-original-title='Recebido?' data-id='#{cheque.id}' data-confirm-message='que deseja marcar o cheque como descontado'><img src='/images/good.png'></a></td></tr>");
					$(".marker").click(()->
						$.get("/payment/cheque/makeAsReceived/"+$(this).attr("data-id"),()->
							alert "Marcado com sucesso!";
							Cheque.getListFromServer()
						)
					)
				$("#total").val((total).formatMoney())
		)
$ ()->
	Cheque.getListFromServer()