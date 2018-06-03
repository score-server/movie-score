package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findGenreByName(String name);

    Genre findGenreById(Long id);

}
