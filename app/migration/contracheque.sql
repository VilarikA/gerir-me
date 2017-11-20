select pe.name,bppr.qtd,bppr.value,0.00 as discount from      
businesspatternpayroll bppr 
inner join business_pattern bp on(bp.id = bppr.business_pattern)
inner join payrollevent pe on (pe.id=bppr.event)
where pe.eventtype=0 and business_pattern = 17798
union all 
select pe.name,bppr.qtd, 0.00, bppr.value as discountfrom      
businesspatternpayroll bppr 
inner join business_pattern bp on(bp.id = bppr.business_pattern)
inner join payrollevent pe on (pe.id=bppr.event)
where pe.eventtype=1  and business_pattern = 17798