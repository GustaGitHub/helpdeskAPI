package com.enterprise.helpdeskAPI.DTO;

import com.enterprise.helpdeskAPI.enums.PriorityEnum;
import com.enterprise.helpdeskAPI.enums.StatusTicketEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Long id;
    private Long authorId;
    private Long categoryId;
    private String title;
    private String description;
    private PriorityEnum priority;
    private StatusTicketEnum status;
}
