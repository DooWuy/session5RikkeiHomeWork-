package com.customer.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "full_name" , nullable = false)
    private String fullName ;
    @Column( name = "email" , unique = true , nullable = false)
    private String email ;

    @Column( name = "password" , nullable = false)
    private String password ;


}
