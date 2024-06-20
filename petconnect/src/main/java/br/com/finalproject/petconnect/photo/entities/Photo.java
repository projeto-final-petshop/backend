package br.com.finalproject.petconnect.photo.entities;

import br.com.finalproject.petconnect.comments.entities.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @OneToMany(mappedBy = "photo")
    private List<Comment> comments;

}
