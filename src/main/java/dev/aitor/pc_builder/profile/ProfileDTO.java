package dev.aitor.pc_builder.profile;

import dev.aitor.pc_builder.address.AddressDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String street;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private boolean isShipping;
    private boolean isSubscribed;
    private Long user;
    private AddressDTO address;
}
