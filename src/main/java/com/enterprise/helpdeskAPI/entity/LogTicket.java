package com.enterprise.helpdeskAPI.entity;

import com.enterprise.helpdeskAPI.enums.PriorityEnum;
import com.enterprise.helpdeskAPI.enums.StatusTicketEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(schema = "helpdesk", name = "log_ticket")
public class LogTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private PriorityEnum priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusTicketEnum status;

    @Column(name = "date_change")
    private Timestamp dateChange;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryTicket categoryTicket;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
