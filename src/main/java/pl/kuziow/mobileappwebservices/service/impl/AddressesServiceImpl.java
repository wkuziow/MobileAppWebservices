package pl.kuziow.mobileappwebservices.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kuziow.mobileappwebservices.io.entity.AddressEntity;
import pl.kuziow.mobileappwebservices.io.entity.UserEntity;
import pl.kuziow.mobileappwebservices.io.repositories.AddressRepository;
import pl.kuziow.mobileappwebservices.io.repositories.UserRepository;
import pl.kuziow.mobileappwebservices.service.AddressesService;
import pl.kuziow.mobileappwebservices.shared.dto.AddressDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressesServiceImpl implements AddressesService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDTO> getAddresses(String userId) {
        List<AddressDTO> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            return returnValue;
        }

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);

        for (AddressEntity addressEntity : addresses) {
            returnValue.add(modelMapper.map(addressEntity, AddressDTO.class));
        }

        return returnValue;
    }

    @Override
    public AddressDTO getAddress(String addressId) {
        AddressDTO returnValue = null;
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        if (addressEntity != null) {
            returnValue = new ModelMapper().map(addressEntity, AddressDTO.class);
        }
        return returnValue;
    }
}
