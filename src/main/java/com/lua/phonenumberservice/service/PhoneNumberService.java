package com.lua.phonenumberservice.service;

import com.lua.phonenumberservice.entity.PhoneNumber;
import com.lua.phonenumberservice.model.PhoneNumberSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhoneNumberService {
    Page<PhoneNumber> find(PhoneNumberSearch search, Pageable pageable);
    PhoneNumber activateNumber(String phoneNumber);
}
