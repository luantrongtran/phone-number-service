package com.lua.phonenumberservice.service;

import com.lua.phonenumberservice.entity.PhoneNumber;
import com.lua.phonenumberservice.exception.NumberAlreadyActivatedException;
import com.lua.phonenumberservice.exception.ResourceNotFoundException;
import com.lua.phonenumberservice.model.PhoneNumberSearch;
import com.lua.phonenumberservice.repository.PhoneNumberRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
    public Page<PhoneNumber> find(PhoneNumberSearch search, Pageable pageable) {
        PhoneNumber example = phoneNumberSearchToExample(search);
        if (example != null) {
            return phoneNumberRepository.findAll(Example.of(example),
                            pageable);
        }
        return phoneNumberRepository
                .findAll(pageable);
    }

//    @Override
//    public Page<PhoneNumber> findPage(PhoneNumberSearch search, Pageable pageable) {
//        PhoneNumber example = phoneNumberSearchToExample(search);
//        if (example != null) {
//            return phoneNumberRepository.findAll(Example.of(example),
//                            pageable);
//        }
//        return phoneNumberRepository
//                .findAll(pageable);
//    }

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

    private PhoneNumber phoneNumberSearchToExample(PhoneNumberSearch search) {
        PhoneNumber example = null;
        if (search != null) {
            if (search.getCustomerNo() != null) {
                example = new PhoneNumber();
                if (search.getCustomerNo() != null) {
                    example.setCustomerNo(search.getCustomerNo());
                }
            }
        }
        return example;
    }
}
