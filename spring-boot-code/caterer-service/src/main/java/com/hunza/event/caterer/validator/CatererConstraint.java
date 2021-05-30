package com.hunza.event.caterer.validator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author ishaan.solanki
 * <p>
 * Interface {@link CatererConstraint} takes care of meta info on a validator class.
 *
 * <p>With the @Constraint annotation, we defined the class that is going to validate our field,
 * the message() is the error message that is showed in the user interface and
 * the additional code is most boilerplate code to conforms to the Spring standards.</p>
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = com.hunza.event.caterer.validator.CatererValidator.class)
@Documented
public @interface CatererConstraint {
    String message() default "Bad Request";

    Class[] groups() default {};

    Class[] payload() default {};
}
