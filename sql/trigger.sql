CREATE TRIGGER timecard_change
before update
on time_cards
for each row
execute procedure log_timecard_change();