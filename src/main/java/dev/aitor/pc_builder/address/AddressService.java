package dev.aitor.pc_builder.address;

import java.util.List;
import java.util.stream.Collectors;

import dev.aitor.pc_builder.address.address_exceptions.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressDTO> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AddressDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        return convertToDTO(address);
    }

    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        Address savedAddress = addressRepository.save(address);
        return convertToDTO(savedAddress);
    }

    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setRegion(addressDTO.getRegion());
        address.setPostalCode(addressDTO.getPostalCode());
        Address updatedAddress = addressRepository.save(address);
        return convertToDTO(updatedAddress);
    }

    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        addressRepository.delete(address);
    }

    private AddressDTO convertToDTO(Address address) {
        return new AddressDTO(address.getId(), address.getStreet(), address.getCity(), address.getRegion(), address.getPostalCode(), address.getCountry(), null);
    }

    private Address convertToEntity(AddressDTO addressDTO) {
        return new Address(addressDTO.getId(), addressDTO.getStreet(), addressDTO.getCity(), addressDTO.getRegion(), addressDTO.getPostalCode(), addressDTO.getCountry(), null);
    }
}