package com.enterprise.helpdeskAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(schema = "helpdesk", name = "category_ticket")
public class CategoryTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "categoryTicket")
    @JsonIgnore
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "categoryTicket")
    @JsonIgnore
    private List<LogTicket> logTickets;
}
