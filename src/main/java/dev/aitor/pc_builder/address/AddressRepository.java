package dev.aitor.pc_builder.address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address save(AddressDTO address);
    
    

}
