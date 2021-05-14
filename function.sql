CREATE OR REPLACE FUNCTION public.log_timecard_change()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    
AS $BODY$
begin
IF NEW.start_time<>OLD.start_time OR NEW.end_time<>OLD.end_time THEN
INSERT INTO audit_log(timecard_id,approved_by,old_start_time, old_end_time,edit_time)
VALUES(OLD.timecard_id,NEW.approved_by,OLD.start_time,OLD.end_time,now());
END IF;
return new;
end;
$BODY$;

