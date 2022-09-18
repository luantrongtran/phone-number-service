package com.lua.phonenumberservice.service;

import com.lua.phonenumberservice.entity.PhoneNumber;
import com.lua.phonenumberservice.exception.NumberAlreadyActivatedException;
import com.lua.phonenumberservice.exception.ResourceNotFoundException;
import com.lua.phonenumberservice.repository.PhoneNumberRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberServiceImpl(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @Override
    public PhoneNumber activateNumber(String activateNumber) {
        PhoneNumber phoneNumber = validateBeforeActivation(activateNumber);

        phoneNumber.setActivated(true);
        phoneNumber.setActivatedDate(LocalDate.now());

        phoneNumberRepository.save(phoneNumber);

        return phoneNumber;
    }

    PhoneNumber validateBeforeActivation(String phoneNumber) {
        Optional<PhoneNumber> opNumber = phoneNumberRepository.findById(phoneNumber);
        if (opNumber.isEmpty()) {
            throw new ResourceNotFoundException("Phone number not found");
        }

        var number = opNumber.get();
        if (number.getActivated()) {
            throw new NumberAlreadyActivatedException("The phone number had been activated");
        }

        return number;
    }
}
