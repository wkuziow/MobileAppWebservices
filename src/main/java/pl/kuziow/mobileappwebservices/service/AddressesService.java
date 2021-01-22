package pl.kuziow.mobileappwebservices.service;

import pl.kuziow.mobileappwebservices.shared.dto.AddressDTO;

import java.util.List;

public interface AddressesService {
    List<AddressDTO> getAddresses(String userId);
    AddressDTO getAddress(String addressId);
}
