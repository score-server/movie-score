package ch.wetwer.moviedbapi.data.uploadFile;

import ch.wetwer.moviedbapi.data.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

/**
 * @author Wetwer
 * @project movie-score
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

    private String title;

    private Integer year;

    private String quality;

    private String mimetype;

    private Boolean ready;

    private Long size;

    @Column(unique = true)
    private int hash;

    private Timestamp timestamp;

    private Boolean completed;

    @Enumerated(EnumType.STRING)
    private VideoType videoType;

    @ManyToOne
    private User user;

    public String getVideoType() {
        switch (videoType) {
            case MOVIE:
                return "movie";
            case EPISODE:
                return "episode";
            default:
                return "undefined";
        }
    }

    public boolean isCorrect() {
        if (quality == null || year == null || title == null || !ready) {
            return false;
        }
        return true;
    }

}
