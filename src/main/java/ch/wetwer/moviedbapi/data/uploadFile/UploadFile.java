package ch.wetwer.moviedbapi.data.uploadFile;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author Wetwer
 * @project movie-db-web
 * @package ch.wetwer.moviedbapi.data.uploadFile
 * @created 16.01.2019
 **/

@Data
@Entity
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private Long size;

    private int hash;

    private Timestamp timestamp;

    private Boolean completed;

}
