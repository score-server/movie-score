set @user_id = 2;

delete
from moviesdb.user
where user.id = (select @user_id);
delete
from moviesdb.request
where user_id = (select @user_id);
delete
from moviesdb.comment
where user_id = (select @user_id);
delete
from moviesdb.timeline
where user_id = (select @user_id);
delete
from moviesdb.likes
where user_id = (select @user_id);
delete
from moviesdb.activity_log
where user_id = (select @user_id);
delete
from moviesdb.time
where user_id = (select @user_id);
