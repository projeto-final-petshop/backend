package br.com.finalproject.petconnect.comments.controllers;

import br.com.finalproject.petconnect.comments.services.CommentService;
import br.com.finalproject.petconnect.comments.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/photos/{photoId}/comments")
@RestController
@AllArgsConstructor
//@CrossOrigin(
//        maxAge = 36000,
//        allowCredentials = "true",
//        value = {"http://localhost:4200", "http://localhost:9090"},
//        allowedHeaders = {"Authorization", "Content-Type"},
//        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE})
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<Comment> getCommentsByPhotoId(@PathVariable Long photoId) {
        return commentService.getCommentsByPhotoId(photoId);
    }

    @PostMapping
    public Comment saveComment(@PathVariable Long photoId, @RequestBody Comment comment) {
        return commentService.saveComment(photoId, comment);
    }

}
