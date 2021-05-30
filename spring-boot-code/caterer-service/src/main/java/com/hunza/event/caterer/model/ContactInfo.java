package com.hunza.event.caterer.model;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author ishaan.solanki
 * <p>
 * It is a model class which represents the contact details of a caterer.
 * <p>It is a class which carries out the contact details such as phone number, mobile number and email address</p>
 */
@ToString
@Data
public class ContactInfo {

    private String phoneNumber;

    @NotNull
    @NotBlank
    private String mobileNumber;

    /**
     * Validation applied on the email address field
     * <p>This field will only accepts email char and its constraints</p>
     */
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email address")
    private String emailAddress;
}
