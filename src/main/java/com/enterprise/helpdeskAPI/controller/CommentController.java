package com.enterprise.helpdeskAPI.controller;

import com.enterprise.helpdeskAPI.DTO.CategoryTicketDTO;
import com.enterprise.helpdeskAPI.DTO.CommentDTO;
import com.enterprise.helpdeskAPI.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService _commentService;

    @GetMapping
    public ResponseEntity getAllComments(){
        try{
            return new ResponseEntity<>(_commentService.getAllComments(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getCommentById(@PathVariable long id){
        try{
            return new ResponseEntity<>(_commentService.getCommentById(id), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity createComment(@RequestBody CommentDTO commentDTO){
        try {
            return new ResponseEntity(_commentService.createComment(commentDTO), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity editComment(@RequestBody CommentDTO commentDTO){
        try {
            return new ResponseEntity(_commentService.editComment(commentDTO), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
