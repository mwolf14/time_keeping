/*
Author: Matt Wolf
Date: 4/25/21
Desc: This is the needed sql that runs once hibernate has created the tables based on the models found in 
the package com.blueteam.timekeeping.models (note abstract classes do not generate a table)
Version: 0.1.0
*/

/*seed values for testing*/

/*seed values for Deployment*/
INSERT INTO public.employees(
	id, first_name, last_name, approved, password, supervisor, user_name)
	VALUES (0,'Dave','Hawkins', true, 'passwordpass', true,'admin');


/* create the audit log table and trigger*/

	/*table*/
	DROP TABLE IF EXISTS public.audit_log;

	CREATE TABLE public.audit_log
	(
	    audit_id integer NOT NULL,
	    timecard_id integer NOT NULL,
	    approved_by integer NOT NULL,
	    edit_time timestamp without time zone,
	    old_end_time timestamp without time zone,
	    old_start_time timestamp without time zone,
	    CONSTRAINT audit_log_pkey PRIMARY KEY (audit_id) 
	)

	TABLESPACE pg_default;

	ALTER TABLE public.audit_log
    	OWNER to postgres;
	