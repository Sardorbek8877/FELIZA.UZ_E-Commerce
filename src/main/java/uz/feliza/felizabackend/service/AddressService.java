package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import uz.feliza.felizabackend.entity.Address;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.payload.AddressDto;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.AddressRepository;
import uz.feliza.felizabackend.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CustomerRepository customerRepository;

    public List<Address> getAddresses(){
        return addressRepository.findAll();
    }

    public List<Address> getAddressesByUserId( Long customerId){
        return addressRepository.findByCustomerId(customerId);}

    public ApiResponse addAddress(AddressDto addressDto){
        Optional<Customer> optionalCustomer = customerRepository.findById(addressDto.getCustomerId());
        if (optionalCustomer.isEmpty())
            return new ApiResponse("Haridor topilmadi", false);

        Address address = new Address();

        address.setCustomer(optionalCustomer.get());
        address.setRegion(addressDto.getRegion());
        address.setStreet(addressDto.getStreet());
        address.setHouseNumber(addressDto.getHouseNumber());
        address.setBTSAddress(addressDto.getBTSAddress());

        addressRepository.save(address);
        return new ApiResponse("Manzil qo'shildi", true, address);
    }
}
