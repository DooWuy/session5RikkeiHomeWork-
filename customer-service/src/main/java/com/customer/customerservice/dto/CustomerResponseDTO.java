package com.customer.customerservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerResponseDTO {
    private Long id;
    private String fullName;
    private String email;


}
