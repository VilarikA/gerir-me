CREATE INDEX index_end
  ON months
  USING btree
  (end_c );

-- Index: indexstart_index

-- DROP INDEX indexstart_index;

CREATE INDEX indexstart_index
  ON months
  USING btree
  (start_c );