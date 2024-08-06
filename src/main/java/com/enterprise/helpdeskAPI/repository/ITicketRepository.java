package com.enterprise.helpdeskAPI.repository;

import com.enterprise.helpdeskAPI.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {
}
