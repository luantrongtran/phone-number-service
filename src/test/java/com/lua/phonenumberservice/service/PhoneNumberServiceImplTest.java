package com.lua.phonenumberservice.service;

import com.lua.phonenumberservice.entity.PhoneNumber;
import com.lua.phonenumberservice.exception.NumberAlreadyActivatedException;
import com.lua.phonenumberservice.exception.ResourceNotFoundException;
import com.lua.phonenumberservice.repository.PhoneNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneNumberServiceImplTest {
    PhoneNumberServiceImpl phoneNumberService;

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @BeforeEach
    void setUp() {
        phoneNumberService = spy(new PhoneNumberServiceImpl(phoneNumberRepository));
    }

    @Test
    void testActivatePhoneNumber_givenAValidNumber_thenSuccessful() {
        // Given
        String validNumber = "0421948186";
        PhoneNumber phoneNumber = PhoneNumber.builder()
                .phoneNo(validNumber)
                .activated(false)
                .activatedDate(null)
                .customerNo(UUID.randomUUID().toString())
                .build();

        doReturn(phoneNumber).when(phoneNumberService).validateBeforeActivation(validNumber);

        // When
        PhoneNumber activatedNumber = phoneNumberService.activateNumber(validNumber);

        // Then
        assertThat(activatedNumber.getActivated()).isTrue();
        assertThat(activatedNumber.getActivatedDate()).isNotNull();

        verify(phoneNumberRepository, times(1)).save(phoneNumber);
    }

    @Test
    void testActivatePhoneNumber_givenAlreadyActivatedNumber_thenExpectNumberAlreadyActivatedException() {
        // Given
        String alreadyActivatedNo = "0421867372";

        doThrow(NumberAlreadyActivatedException.class).when(phoneNumberService).validateBeforeActivation(alreadyActivatedNo);

        // When
        assertThatThrownBy(() -> phoneNumberService.activateNumber(alreadyActivatedNo))
                .isInstanceOf(NumberAlreadyActivatedException.class);

        // Then
    }

    @Test
    void testActivatePhoneNumber_givenNotExistNumber_thenExpectResourceNotFoundException() {
        // Given
        String notExistNo = "0421857344";
        doThrow(ResourceNotFoundException.class).when(phoneNumberService).validateBeforeActivation(notExistNo);

        // When
        assertThatThrownBy(() -> phoneNumberService.activateNumber(notExistNo))
                .isInstanceOf(ResourceNotFoundException.class);

        // Then
    }

    @Test
    void testValidateBeforeActivation_givenNotExist_thenExpectResourceNotFoundException() {
        // Given
        String notExistNo = "0424545234";

        when(phoneNumberRepository.findById(notExistNo)).thenReturn(Optional.empty());

        // When
        assertThatThrownBy(() -> phoneNumberService.validateBeforeActivation(notExistNo))
                .isInstanceOf(ResourceNotFoundException.class);

        // Then
    }

    @Test
    void testValidateBeforeActivation_givenAlreadyActivatedNo_thenExpectNumberAlreadyActivatedException() {
        // Given
        String alreadyActivatedNo = "042454212";
        var phoneNumber = PhoneNumber.builder()
                .phoneNo(alreadyActivatedNo)
                .customerNo("123456")
                .activated(true)
                .activatedDate(LocalDate.now())
                .build();

        when(phoneNumberRepository.findById(alreadyActivatedNo)).thenReturn(Optional.of(phoneNumber));

        // When
        assertThatThrownBy(() -> phoneNumberService.validateBeforeActivation(alreadyActivatedNo))
                .isInstanceOf(NumberAlreadyActivatedException.class);

        // Then
    }
}