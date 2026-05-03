package com.customer.customerservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CustomerRequestDTO {

    private String fullName ;
    private String email;
    private String password ;



}
