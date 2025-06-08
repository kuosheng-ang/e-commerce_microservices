package com.ecommerce.user.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "WorkItem")
public class WorkItem {
    @Id
    private Long id;
    private String name;
    private String archive;
    private String guide ;
    private String date;
    private String description;
    private String status;


}
