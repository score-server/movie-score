package ch.wetwer.moviedbapi.data.previewimg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.data.previewimg
 * @created 09.04.2019
 **/

@Repository
public interface PreviewImageRepository extends JpaRepository<PreviewImage, Long> {
}
