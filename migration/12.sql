-- DROP INDEX status_index;
CREATE INDEX status_index
  ON treatment
  USING btree
(status  NULLS FIRST);
update treatment  set dateevent=start_c;
update busyevent  set dateevent=start_c;