-- Table: log.auditmapper

-- DROP TABLE log.auditmapper;

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
  CONSTRAINT auditmapper_pk PRIMARY KEY (id )
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
  (company );


-- Table: log.logobj

-- DROP TABLE log.logobj;

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
  CONSTRAINT logobj_pk PRIMARY KEY (id )
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
  (company );


insert into log.logobj  (select * from logobj);
insert into log.auditmapper  (select * from auditmapper);





-- Table: log.auditmapper

-- DROP TABLE log.auditmapper;

CREATE TABLE log.auditmapperh
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
  CONSTRAINT auditmapperh_pk PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE log.auditmapperh
  OWNER TO mateus;

-- Index: log.auditmapper_company

-- DROP INDEX log.auditmapper_company;

CREATE INDEX auditmapperh_company
  ON log.auditmapperh
  USING btree
  (company );



insert into log.auditmapperh 
(select * from auditmapperh);





SELECT setval('log.logobj_id_seq', 4967378, true);
SELECT setval('log.auditmapper_id_seq', 1838330, true);
