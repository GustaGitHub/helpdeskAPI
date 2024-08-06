package com.enterprise.helpdeskAPI.service;

import com.enterprise.helpdeskAPI.DTO.CommentDTO;
import com.enterprise.helpdeskAPI.entity.Comment;
import com.enterprise.helpdeskAPI.entity.Ticket;
import com.enterprise.helpdeskAPI.entity.User;
import com.enterprise.helpdeskAPI.enums.StatusTicketEnum;
import com.enterprise.helpdeskAPI.exceptions.NotFoundException;
import com.enterprise.helpdeskAPI.repository.ICommentRepository;
import com.enterprise.helpdeskAPI.repository.ITicketRepository;
import com.enterprise.helpdeskAPI.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private ICommentRepository _commentRepository;

    @Autowired
    private IUserRepository _userRepository;

    @Autowired
    private ITicketRepository _ticketRepository;

    public List<Comment> getAllComments(){ return _commentRepository.findAll(); }

    public Comment getCommentById(Long id) throws Exception{
        Optional<Comment> comment = _commentRepository.findById(id);

        if(comment.isPresent())
            return comment.get();

        throw new NotFoundException("Comment not found");
    }

    @Transactional
    public Comment createComment(CommentDTO commentDTO) throws Exception{
        Optional<User> user = _userRepository.findById(commentDTO.getUserId());
        Optional<Ticket> ticket = _ticketRepository.findById(commentDTO.getTicketId());

        if(user.isEmpty()) throw new NotFoundException("User not found");

        if(ticket.isEmpty() || ticket.get().getStatus() == StatusTicketEnum.Solved)
                throw new NotFoundException("Ticket not found or Ticket is closed");

        Comment comment = Comment.builder()
                                .commentContent(commentDTO.getCommentContent())
                                .commentDate(new Timestamp(System.currentTimeMillis()))
                                .user(user.get())
                                .ticket(ticket.get())
                                .build();

        return _commentRepository.save(comment);
    }

    @Transactional
    public Comment editComment(CommentDTO commentDTO) throws Exception{
        Optional<Comment> commentOptional = _commentRepository.findById(commentDTO.getId());

        if(commentOptional.isEmpty()) throw new NotFoundException("Ticket not exists or ticket is closed");

        Comment comment = commentOptional.get();

        if(commentDTO.getCommentContent() != null && commentDTO.getCommentContent().length() > 0)
            comment.setCommentContent(commentDTO.getCommentContent());

        return _commentRepository.save(comment);
    }

}
