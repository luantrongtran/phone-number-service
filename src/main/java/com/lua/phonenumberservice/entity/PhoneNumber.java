package com.lua.phonenumberservice.entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "phone_number")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumber extends RepresentationModel<PhoneNumber> {
    @Id
    @Column(name = "phone_no")
    String phoneNo;
    @Column(name = "customer_no")
    String customerNo;
    @Column(name = "activated")
    Boolean activated;
    @Column(name = "activated_date")
    LocalDate activatedDate;
}
