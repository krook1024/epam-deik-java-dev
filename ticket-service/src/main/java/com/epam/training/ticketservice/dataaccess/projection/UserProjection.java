package com.epam.training.ticketservice.dataaccess.projection;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserProjection {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;
    private String password;

    private Boolean isAdmin;
}
