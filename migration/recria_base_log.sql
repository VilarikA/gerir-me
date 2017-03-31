CREATE SCHEMA log
AUTHORIZATION postgres;
GRANT ALL ON SCHEMA log TO postgres;
GRANT ALL ON SCHEMA log TO public;

CREATE SEQUENCE log.auditmapper_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 3790402
  CACHE 1;
ALTER TABLE log.auditmapper_id_seq
  OWNER TO mateus;
CREATE SEQUENCE log.auditmapperh_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE log.auditmapperh_id_seq
  OWNER TO mateus;


CREATE SEQUENCE log.logobj_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 7644413
  CACHE 1;
ALTER TABLE log.logobj_id_seq
  OWNER TO mateus;

  CREATE TABLE log.auditmapper
(
  table_c character varying(200),
  id bigserial NOT NULL,
  event character varying(200),
  updatedat timestamp without time zone,
  createdat timestamp without time zone,
  company bigint,
  updatedby bigint,
  createdby bigint,
  idobj bigint,
  jsobj character varying(4000),
  CONSTRAINT auditmapper_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE log.auditmapper
  OWNER TO mateus;

-- Index: log.auditmapper_company

-- DROP INDEX log.auditmapper_company;

CREATE INDEX auditmapper_company
  ON log.auditmapper
  USING btree
  (company);

-- Index: log.auditmapper_createdby_idx

-- DROP INDEX log.auditmapper_createdby_idx;

CREATE INDEX auditmapper_createdby_idx
  ON log.auditmapper
  USING btree
  (createdby);

-- Index: log.auditmapper_table_c_idx

-- DROP INDEX log.auditmapper_table_c_idx;

CREATE INDEX auditmapper_table_c_idx
  ON log.auditmapper
  USING btree
  (table_c);



CREATE TABLE log.logobj
(
  id bigserial NOT NULL,
  message character varying(2555),
  company bigint,
  updatedat timestamp without time zone,
  createdat timestamp without time zone,
  updatedby bigint,
  createdby bigint,
  typelog character varying(255),
  CONSTRAINT logobj_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE log.logobj
  OWNER TO mateus;

-- Index: log.logobj_company

-- DROP INDEX log.logobj_company;

CREATE INDEX logobj_company
  ON log.logobj
  USING btree
  (company);

-- Index: log.logobj_createdby_idx

-- DROP INDEX log.logobj_createdby_idx;

CREATE INDEX logobj_createdby_idx
  ON log.logobj
  USING btree
  (createdby);