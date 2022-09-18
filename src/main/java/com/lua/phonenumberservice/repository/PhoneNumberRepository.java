package com.lua.phonenumberservice.repository;

import com.lua.phonenumberservice.entity.PhoneNumber;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends PagingAndSortingRepository<PhoneNumber, String> {

}
