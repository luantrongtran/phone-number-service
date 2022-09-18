package com.lua.phonenumberservice.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneNumberSearch {
    @Parameter(
    description = "Customer number",
    schema = @Schema(
            type = "string"
    ))
    String customerNo;
}
