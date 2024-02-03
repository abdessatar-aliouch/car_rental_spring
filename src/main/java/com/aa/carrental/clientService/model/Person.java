
package com.aa.carrental.clientService.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter @Setter
@Entity
@DiscriminatorValue(value = "person")
public class Person extends Client {

    @NotNull
    @Pattern(regexp = "[A-Za-z ]+", message = "lastname must only contain letters and spaces")
    private String lastname;

    private LocalDate dob;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "license_id", referencedColumnName = "id")
    private License license;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

}