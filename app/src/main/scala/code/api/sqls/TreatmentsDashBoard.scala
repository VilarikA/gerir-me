package code
package api


object TreatmentsDashBoard {
	val sql = """
			select
			dateevent,
			count(1),
			(
				select 
				count(1)
				from
				treatment ti
				inner join business_pattern c on(c.id = ti.customer)
				where ti.hasDetail=true and ti.status=4 and ti.company=t.company
				and ti.dateevent = t.dateevent
				and date(c.createdat) = t.dateevent
				and %s
			) as new_customer
			from
			treatment t
			inner join business_pattern c on(c.id = t.customer)
			where hasDetail=true and t.status=4 and t.company=?
			and dateevent between date(?) and date(?)
			and %s
			group by dateevent, t.company
			order by dateevent
		"""
	val sql_customers = """
			select count(id), sum(returns) from (
				select 
				count(1) as returns,c.id, dateevent
				from
				treatment t
				inner join treatmentdetail td on(td.treatment = t.id)
				inner join business_pattern c on(c.id = t.customer)
				where hasDetail=true and t.status=4 and t.company=?
				and dateevent between date(?) and date(?)
				and %s
				group by t.id, c.id,dateevent
				) as data1 group by id
	"""
	val sql_avg_duraton = """select to_char(avg((t.end_c-t.start_c)),'HH24:MI') from
			treatment t
			inner join business_pattern c on(c.id = t.customer)
			where hasDetail=true and t.status=4 and t.company=?
			and dateevent between date(?) and date(?)
			and %s
		"""
}