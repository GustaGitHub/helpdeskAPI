package com.enterprise.helpdeskAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryTicketDTO {
    private Long id;
    private String description;
    private boolean active;
}
