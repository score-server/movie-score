package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Nice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NiceRepository extends JpaRepository<Nice, Long> {

    Nice findNiceById(Long id);

}
