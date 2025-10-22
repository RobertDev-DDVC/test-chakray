package com.test.chakray.mapper;

import com.test.chakray.dto.UserResponseDTO;
import com.test.chakray.model.User;

import java.util.List;

public class UserMapper {
    public static UserResponseDTO toResponse(User u) {
        UserResponseDTO r = new UserResponseDTO();
        r.setId(u.getId());
        r.setEmail(u.getEmail());
        r.setName(u.getName());
        r.setPhone(u.getPhone());
        r.setTaxId(u.getTaxId());
        r.setCreatedAt(u.getCreatedAt());

        List<UserResponseDTO.AddressResponse> addrs = u.getAddresses().stream().map(a -> {
            UserResponseDTO.AddressResponse ar = new UserResponseDTO.AddressResponse();
            ar.id = a.getId();
            ar.name = a.getName();
            ar.street = a.getStreet();
            ar.countryCode = a.getCountryCode();
            return ar;
        }).toList();

        r.setAddresses(addrs);
        return r;
    }
}
