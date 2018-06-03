package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {

    List<MovieGenre> findMovieGenresByMovieId(Long movieId);

    List<MovieGenre> findMovieGenresByGenreId(Long genreId);


}
