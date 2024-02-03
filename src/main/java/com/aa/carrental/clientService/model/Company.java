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
@DiscriminatorValue(value = "company")
public class Company extends Client {

}