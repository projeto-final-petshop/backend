package br.com.finalproject.petconnect.comments.repositories;

import br.com.finalproject.petconnect.comments.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
