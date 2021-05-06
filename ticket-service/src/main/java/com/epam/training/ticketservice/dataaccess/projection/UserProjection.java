package com.epam.training.ticketservice.dataaccess.projection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserProjection {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;
    private String password;

    private Boolean isAdmin;
}
