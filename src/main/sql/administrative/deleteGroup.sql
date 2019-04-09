set @group_id = 1;

UPDATE moviesdb.user
SET group_id = NULL
WHERE group_id = (select @group_id);

delete
from moviesdb.group_invite
where id = (select @group_id);
