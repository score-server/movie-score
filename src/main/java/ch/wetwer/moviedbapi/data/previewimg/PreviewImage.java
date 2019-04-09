package ch.wetwer.moviedbapi.data.previewimg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.data
 * @created 09.04.2019
 **/

@Data
@Entity
public class PreviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @JsonIgnore
    private byte[] previewImg;

}
