package br.com.finalproject.petconnect.comments.entities;

import br.com.finalproject.petconnect.photo.entities.Photo;
import br.com.finalproject.petconnect.user.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends AbstractAuditable<User, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    private Photo photo;

    /// Implementando o método equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // A "NullPointerException" could be throw; "o" is nullable here
        if (getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
