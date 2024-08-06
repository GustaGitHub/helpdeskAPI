package com.enterprise.helpdeskAPI.controller;

import com.enterprise.helpdeskAPI.DTO.TicketDTO;
import com.enterprise.helpdeskAPI.exceptions.NotFoundException;
import com.enterprise.helpdeskAPI.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService _ticketService;

    @GetMapping
    public ResponseEntity getAllTicket(){
        try{
            return new ResponseEntity<>(_ticketService.getAllTickets() ,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getTicketById(@PathVariable Long id){
        try {
            return new ResponseEntity(_ticketService.getTicketById(id), HttpStatus.OK);
        } catch (NotFoundException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity createTicket(@RequestBody TicketDTO ticketDTO){
        try {
            return new ResponseEntity(_ticketService.createTicket(ticketDTO), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity editTicket(@RequestBody TicketDTO ticketDTO){
        try {
            return new ResponseEntity(_ticketService.editTicket(ticketDTO), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
