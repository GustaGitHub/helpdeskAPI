package com.enterprise.helpdeskAPI.service;

import com.enterprise.helpdeskAPI.DTO.TicketDTO;
import com.enterprise.helpdeskAPI.entity.CategoryTicket;
import com.enterprise.helpdeskAPI.entity.Ticket;
import com.enterprise.helpdeskAPI.entity.User;
import com.enterprise.helpdeskAPI.exceptions.NotFoundException;
import com.enterprise.helpdeskAPI.repository.ICategoryTicketRepository;
import com.enterprise.helpdeskAPI.repository.ITicketRepository;
import com.enterprise.helpdeskAPI.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private ITicketRepository _ticketRepository;

    @Autowired
    private ICategoryTicketRepository _categoryTicketRepository;

    @Autowired
    private IUserRepository _userRepository;

    public List<Ticket> getAllTickets(){
        return _ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id)throws Exception{
        Optional<Ticket> ticket = _ticketRepository.findById(id);

        if(ticket.isPresent())
            return ticket.get();

        throw new NotFoundException("Ticket not found");
    }

    @Transactional
    public Ticket createTicket(TicketDTO ticketDTO) throws Exception{
        Optional<CategoryTicket> categoryTicket = _categoryTicketRepository.findById(ticketDTO.getCategoryId());
        Optional<User> user = _userRepository.findById(ticketDTO.getAuthorId());

        if(categoryTicket.isEmpty())  throw new NotFoundException("Category not found");
        if(user.isEmpty())  throw new NotFoundException("User not found");

        Ticket ticketObj = Ticket.builder()
                            .description(ticketDTO.getDescription().trim())
                            .title(ticketDTO.getTitle().trim())
                            .categoryTicket(categoryTicket.get())
                            .user(user.get())
                            .status(ticketDTO.getStatus())
                            .priority(ticketDTO.getPriority())
                            .build();


        return _ticketRepository.save(ticketObj);
    }

    @Transactional
    public Ticket editTicket(TicketDTO ticketDTO) throws Exception{
        Optional<Ticket> ticketOptional = _ticketRepository.findById(ticketDTO.getId());

        if(ticketOptional.isEmpty()) throw new NotFoundException("Ticket not found");

        Ticket ticketEdited = ticketOptional.get();

        if(ticketDTO.getTitle() != null && ticketDTO.getTitle().length() > 0)
            ticketEdited.setTitle(ticketDTO.getTitle());

        if(ticketDTO.getDescription() != null && ticketDTO.getDescription().length() > 0)
            ticketEdited.setDescription(ticketDTO.getDescription());

        if(ticketDTO.getCategoryId() != null){
            Optional<CategoryTicket> categoryTicketOptional = _categoryTicketRepository.findById(ticketDTO.getCategoryId());

            if(categoryTicketOptional.isEmpty())
                throw new NotFoundException("Category not found");

            ticketEdited.setCategoryTicket(categoryTicketOptional.get());
        }

        if(ticketDTO.getStatus() != null) ticketEdited.setStatus(ticketDTO.getStatus());

        if(ticketDTO.getPriority() != null) ticketEdited.setPriority(ticketDTO.getPriority());



        return _ticketRepository.save(ticketEdited);
    }
}
