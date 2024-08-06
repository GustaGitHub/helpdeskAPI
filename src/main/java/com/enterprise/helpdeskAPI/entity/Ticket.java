package com.enterprise.helpdeskAPI.entity;

import com.enterprise.helpdeskAPI.enums.PriorityEnum;
import com.enterprise.helpdeskAPI.enums.StatusTicketEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(schema = "helpdesk", name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private PriorityEnum priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusTicketEnum status;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryTicket categoryTicket;

    @OneToMany(mappedBy = "ticket")
    private List<Comment> comments;

    @OneToMany(mappedBy = "ticket")
    private List<LogTicket> logTickets;

    @OneToMany(mappedBy = "ticket")
    private List<FileAttachment> files;
}
