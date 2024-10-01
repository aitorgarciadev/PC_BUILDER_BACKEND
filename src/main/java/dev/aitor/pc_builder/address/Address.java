package dev.aitor.pc_builder.address;

import dev.aitor.pc_builder.profile.Profile;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipping_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private long id;
    @NonNull
    private String street;
    @NonNull
    private String city;
    @NonNull
    private String region;
    @NonNull
    private String postalCode;
    @NonNull
    private String country;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private Profile profile;

}
