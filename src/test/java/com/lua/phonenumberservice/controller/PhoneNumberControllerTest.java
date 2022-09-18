package com.lua.phonenumberservice.controller;

import com.lua.phonenumberservice.exception.ResourceNotFoundException;
import com.lua.phonenumberservice.exception.NumberAlreadyActivatedException;
import com.lua.phonenumberservice.service.PhoneNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneNumberControllerTest {

    PhoneNumberController phoneNumberController;

    @Mock
    private PhoneNumberService phoneNumberService;

    @BeforeEach
    void setUp() {
        phoneNumberController = new PhoneNumberController(phoneNumberService);
    }

    @Test
    void testActivatePhoneNumber_GivenAValidNumber_ThenSuccess() {
        // Given
        String validPhoneNo = "0421112345";

        // When
        phoneNumberController.activatePhoneNumber(validPhoneNo);

        // Then
        verify(phoneNumberService, times(1)).activateNumber(validPhoneNo);
    }

    @Test
    void testActivatePhoneNumber_GivenNotExistNumber_ThenExpectResourceNotFoundException() {
        // Given
        String wrongFormatNumber = "1234567890";

        doThrow(ResourceNotFoundException.class).when(phoneNumberService).activateNumber(wrongFormatNumber);

        // When
        assertThatThrownBy(() -> phoneNumberController.activatePhoneNumber(wrongFormatNumber))
                .isInstanceOf(ResourceNotFoundException.class);

        // Then
    }

    @Test
    void testActivatePhoneNumber_GivenTheNumberAlreadyActivated_ThenExpectNumberAlreadyActivatedException() {
        // Given
        String alreadyActivatedNo = "0421992321";

        doThrow(NumberAlreadyActivatedException.class).when(phoneNumberService).activateNumber(alreadyActivatedNo);

        // When
        assertThatThrownBy(() -> phoneNumberController.activatePhoneNumber(alreadyActivatedNo))
                .isInstanceOf(NumberAlreadyActivatedException.class);

        // Then
    }
}