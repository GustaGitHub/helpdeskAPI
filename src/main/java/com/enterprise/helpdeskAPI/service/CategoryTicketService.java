package com.enterprise.helpdeskAPI.service;

import com.enterprise.helpdeskAPI.DTO.CategoryTicketDTO;
import com.enterprise.helpdeskAPI.entity.CategoryTicket;
import com.enterprise.helpdeskAPI.exceptions.NotFoundException;
import com.enterprise.helpdeskAPI.repository.ICategoryTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryTicketService {

    @Autowired
    private ICategoryTicketRepository _categoryTicketRepository;

    public List<CategoryTicket> getAllCategoryTicket(){
        return _categoryTicketRepository.findAll();
    }

    public CategoryTicket getCategoryTicketServiceById(long id) throws Exception{
        Optional<CategoryTicket> categoryTicket = _categoryTicketRepository.findById(id);

        if(categoryTicket.isPresent())
            return categoryTicket.get();

        throw new NotFoundException("Category not found");
    }

    @Transactional
    public CategoryTicket createCategoryTicket(CategoryTicketDTO categoryTicketDTO){
        CategoryTicket entity = CategoryTicket.builder()
                                                .active(true)
                                                .description(categoryTicketDTO.getDescription().trim())
                                                .build();


        return _categoryTicketRepository.save(entity);
    }

    @Transactional
    public CategoryTicket editCategoryTicket(CategoryTicketDTO categoryTicketDTO) throws Exception {
        Optional<CategoryTicket> categoryTicketById = _categoryTicketRepository.findById(categoryTicketDTO.getId());

        if(categoryTicketById.isPresent()) {
            CategoryTicket categoryEdited = CategoryTicket.builder()
                                            .description(categoryTicketDTO.getDescription())
                                            .active(categoryTicketDTO.isActive())
                                            .id(categoryTicketDTO.getId())
                                            .tickets(categoryTicketById.get().getTickets())
                                            .logTickets(categoryTicketById.get().getLogTickets())
                                            .build();


            return _categoryTicketRepository.save(categoryEdited);
        }

        throw new NotFoundException("Category not found");
    }

}
