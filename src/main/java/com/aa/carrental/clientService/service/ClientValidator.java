package com.aa.carrental.clientService.service;

import com.aa.carrental.clientService.model.Client;
import com.aa.carrental.clientService.model.Company;
import com.aa.carrental.clientService.model.Person;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.aa.carrental.clientService.service.ClientValidator.ValidationResults.*;

/**
 * @author Abdessatar Aliouch
 * @created 28/12/2022
 */
@FunctionalInterface
public interface ClientValidator extends Function<Client, ClientValidator.ValidationResults> {

    String namePattern = "[A-Za-z ]+";
    String emailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    String phonePattern = "^\\d{10}$";
    RuntimeException NOT_A_PERSON_INSTANCE = new RuntimeException("Input is not a person instance!");

    enum ValidationResults {
        SUCCESS,
        EMAIL_INVALID,
        PHONE_INVALID,
        NAME_INVALID,
        PASSPORT_EXPIRATION_DATE_INVALID,
        LICENSE_EXPIRATION_DATE_INVALID,
        LICENSE_NUMBER_INVALID,
        PASSPORT_NUMBER_INVALID
    }


    static ClientValidator isNameValid() {
        return client -> {
            if (client instanceof Person person)
              return (Pattern.matches(namePattern, person.getFirstname()) && Pattern.matches(namePattern, person.getLastname())) ?
                        SUCCESS : NAME_INVALID;

            return Pattern.matches(namePattern, client.getFirstname())?
                        SUCCESS : NAME_INVALID;

        };
    }

    static ClientValidator isEmailValid() {
        return client -> {
            String email = client.getEmail();
            if (!email.isBlank()){
                return Pattern.matches(emailPattern, email) ?
                        SUCCESS : EMAIL_INVALID;
            }else return SUCCESS;
        };
    }

    static ClientValidator isPhoneValid() {
        return client -> {
            String phone = client.getPhone();
            if (!phone.isBlank()){
                return   Pattern.matches(phonePattern, phone) ?
                        SUCCESS : PHONE_INVALID;
            }else return SUCCESS;
        };
    }

    static ClientValidator isPassportNumberValid() {
        return client -> {
            if (client instanceof Person person) {
                return person.getPassport().getNumber().isEmpty() ? PASSPORT_NUMBER_INVALID : SUCCESS;
            }
            else throw NOT_A_PERSON_INSTANCE;
        };
    }

    static ClientValidator isPassportExpirationDateValid() {
        return client -> {
            if (client instanceof Person person)
                return person.getPassport().getExpirationDate().isBefore(LocalDate.now()) ? PASSPORT_EXPIRATION_DATE_INVALID : SUCCESS;
            else throw NOT_A_PERSON_INSTANCE;
        };
    }



    static ClientValidator isLicenseNumberValid() {
        return client -> {
            if (client instanceof Person person)
                return person.getLicense().getNumber().isEmpty() ? LICENSE_NUMBER_INVALID : SUCCESS;
            else throw NOT_A_PERSON_INSTANCE;
        };

    }

    static ClientValidator isLicenseExpirationDateValid() {
        return client -> {
            if (client instanceof Person person)
                return person.getLicense().getExpirationDate().isBefore(LocalDate.now()) ? LICENSE_EXPIRATION_DATE_INVALID : SUCCESS;
            else throw NOT_A_PERSON_INSTANCE;
        };
    }

    default ClientValidator and(ClientValidator other) {
        return client -> {
            ValidationResults results = this.apply(client);
            return results.equals(SUCCESS) ? other.apply(client) : results;
        };
    }

}

