package com.lua.phonenumberservice.controller;

import com.lua.phonenumberservice.exception.NumberAlreadyActivatedException;
import com.lua.phonenumberservice.exception.ResourceNotFoundException;
import com.lua.phonenumberservice.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.MimeType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/phone-number/v1")
@Slf4j
public class PhoneNumberController {

    PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
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