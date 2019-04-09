set @movie_id = 0;

delete
from moviesdb.movie
where movie.id = (select @movie_id);
delete
from moviesdb.genre
where movie_id = (select @movie_id);
delete
from moviesdb.comment
where movie_id = (select @movie_id);
delete
from moviesdb.list_movie
where movie_id = (select @movie_id);
delete
from moviesdb.import_log
where movie_id = (select @movie_id);
delete
from moviesdb.likes
where movie_id = (select @movie_id);
delete
from moviesdb.time
where movie_id = (select @movie_id);