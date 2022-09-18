package com.lua.phonenumberservice.controller;

import com.lua.phonenumberservice.entity.PhoneNumber;
import com.lua.phonenumberservice.exception.ResourceNotFoundException;
import com.lua.phonenumberservice.exception.NumberAlreadyActivatedException;
import com.lua.phonenumberservice.model.PhoneNumberSearch;
import com.lua.phonenumberservice.service.PhoneNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneNumberControllerTest {

    PhoneNumberController phoneNumberController;

    @Mock
    private PhoneNumberService phoneNumberService;

    @Mock
    PagedResourcesAssembler<PhoneNumber> pagedResourcesAssembler;

    @BeforeEach
    void setUp() {
        phoneNumberController = new PhoneNumberController(phoneNumberService, pagedResourcesAssembler);
    }

    @Test
    void testFind_GivenCriteriaAndPaging_ThenSearchAccordingly() {
        // Given
        var pageable = Pageable.unpaged();
        PhoneNumberSearch search = PhoneNumberSearch.builder().build();

        Page<PhoneNumber> expectedList = mock(Page.class);
        when(phoneNumberService.find(search, pageable)).thenReturn(expectedList);
        PagedModel<EntityModel<PhoneNumber>> expectedPaged = mock(PagedModel.class);
        when(pagedResourcesAssembler.toModel(expectedList)).thenReturn(expectedPaged);

        // When
        var actualPage = phoneNumberController.find(search, pageable);

        // Then
        assertThat(actualPage).isEqualTo(expectedPaged);
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