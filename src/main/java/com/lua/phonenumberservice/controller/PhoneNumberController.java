package com.lua.phonenumberservice.controller;

import com.lua.phonenumberservice.entity.PhoneNumber;
import com.lua.phonenumberservice.model.PhoneNumberSearch;
import com.lua.phonenumberservice.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/phone-number/v1")
@Slf4j
public class PhoneNumberController {

    private PagedResourcesAssembler<PhoneNumber> pagedResourcesAssembler;

    PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService, PagedResourcesAssembler<PhoneNumber> pagedResourcesAssembler) {
        this.phoneNumberService = phoneNumberService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Operation(summary = "Search phone numbers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns a list of phone number")
    })
    @GetMapping(produces = "application/json")
    public PagedModel find(@ParameterObject PhoneNumberSearch search,
                           @ParameterObject Pageable pageable) {
        var page = phoneNumberService.find(search, pageable);
        return pagedResourcesAssembler.toModel(page);
    }

    @Operation(summary = "Activate a phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully activated the number", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Couldn't find the given phone number", content = {@Content}),
            @ApiResponse(responseCode = "400", description = "The phone number is already activated", content = {@Content})
    })
    @PostMapping(value = "/activate/{phoneNumber}", produces = "text/plain")
    public ResponseEntity activatePhoneNumber(
            @Parameter(name = "phone number", description = "The activated phone number", in = ParameterIn.PATH)
            @PathVariable String phoneNumber) {
        phoneNumberService.activateNumber(phoneNumber);
        return ResponseEntity.noContent().build();
    }
}