package com.lua.phonenumberservice.service;

import com.lua.phonenumberservice.entity.PhoneNumber;

public interface PhoneNumberService {
    PhoneNumber activateNumber(String phoneNumber);
}
