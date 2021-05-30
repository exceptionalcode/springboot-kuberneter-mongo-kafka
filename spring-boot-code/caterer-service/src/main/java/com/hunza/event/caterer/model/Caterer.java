package com.hunza.event.caterer.model;

import com.hunza.event.caterer.validator.CatererConstraint;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ishaan.solanki
 * <p>
 * It is a model class defination for domain caterer.
 * <p>It is a class which carries out the meta details of a caterer distribution business</p>
 */
@Data
@Document
public class Caterer {

    @Transient
    public static final String SEQUENCE_NAME = "carter_sequence";

    @Id
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @Valid
    private Location location;

    @CatererConstraint
    private Capacity capacity;

    @Valid
    private ContactInfo contactInfo;

}
