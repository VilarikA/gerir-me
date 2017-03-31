select 'delete from '||table_name||' where company = ?' from information_schema.tables 
where table_schema='public' 
and table_name  not like 'qrtz%' 
and  table_name  not like 'tab%' 