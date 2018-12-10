package ch.wetwer.moviedbapi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class GroupInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    private boolean active;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private List<User> users;

}
