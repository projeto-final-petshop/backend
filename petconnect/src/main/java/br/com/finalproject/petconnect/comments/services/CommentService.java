package br.com.finalproject.petconnect.comments.services;

import br.com.finalproject.petconnect.comments.entities.Comment;
import br.com.finalproject.petconnect.comments.repositories.CommentRepository;
import br.com.finalproject.petconnect.photo.entities.Photo;
import br.com.finalproject.petconnect.photo.services.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final PhotoService photoService;
    private final CommentRepository commentRepository;

    public List<Comment> getCommentsByPhotoId(Long photoId) {
        Photo photo = photoService.getPhotoById(photoId);
        return photo.getComments();
    }

    public Comment saveComment(Long photoId, Comment comment) {
        Photo photo = photoService.getPhotoById(photoId);
        comment.setPhoto(photo);
        return commentRepository.save(comment);
    }

}
