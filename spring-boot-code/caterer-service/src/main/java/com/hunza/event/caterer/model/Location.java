package com.hunza.event.caterer.model;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * It is a model class which represents the location details of a caterer.
 * <p>This is a class which carries out the location against a caterer i.e city name, street name number and postal code</p>
 */
@ToString
@Data
public class Location {

    /**
     * Validation applied on the city name field
     * <p>This field will only accepts alphabets, no special char</p>
     */
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "City name must not contain digits or special characters")
    private String cityName;

    private String streetNameNumber;

    private String postalCode;
}
