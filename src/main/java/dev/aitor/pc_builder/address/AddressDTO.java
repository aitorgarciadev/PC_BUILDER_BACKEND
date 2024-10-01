package dev.aitor.pc_builder.address;

import dev.aitor.pc_builder.profile.ProfileDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private long id;
    private String street;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private ProfileDTO profile;
}
