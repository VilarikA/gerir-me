CREATE INDEX status_star
  ON treatment
  USING btree
  (start_c  NULLS FIRST, status  NULLS FIRST, company  NULLS FIRST);
COMMENT ON INDEX start_end
  IS 'status_star';


CREATE INDEX treatment_start_c
  ON treatment
  USING btree
(start_c);
