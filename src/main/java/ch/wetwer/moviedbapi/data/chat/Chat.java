package ch.wetwer.moviedbapi.data.chat;

import ch.wetwer.moviedbapi.data.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @Size(max = 256)
    private String message;

    private Timestamp timestamp;
}
