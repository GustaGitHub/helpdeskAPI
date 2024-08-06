package com.enterprise.helpdeskAPI.repository;

import com.enterprise.helpdeskAPI.entity.CategoryTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryTicketRepository extends JpaRepository<CategoryTicket, Long> {
}
