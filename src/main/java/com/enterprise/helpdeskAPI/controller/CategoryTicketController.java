package com.enterprise.helpdeskAPI.controller;

import com.enterprise.helpdeskAPI.DTO.CategoryTicketDTO;
import com.enterprise.helpdeskAPI.exceptions.NotFoundException;
import com.enterprise.helpdeskAPI.service.CategoryTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category-ticket")
public class CategoryTicketController {

    @Autowired
    private CategoryTicketService _categoryTicketService;

    @GetMapping()
    public ResponseEntity getAllCategoryTicket(){
        try {
            return new ResponseEntity(_categoryTicketService.getAllCategoryTicket(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getCategoryTicketById(@PathVariable Long id){
        try {
            return new ResponseEntity(_categoryTicketService.getCategoryTicketServiceById(id), HttpStatus.OK);
        }catch (NotFoundException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity createCategoryTicket(@RequestBody CategoryTicketDTO dto){
        try {
            return new ResponseEntity(_categoryTicketService.createCategoryTicket(dto), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity editTicket(@RequestBody CategoryTicketDTO dto){
        try {
            return new ResponseEntity(_categoryTicketService.editCategoryTicket(dto), HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
