package com.hunza.event.caterer.validator;

import com.hunza.event.caterer.model.Capacity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ishaa.solanki
 * <p>
 * Class {@link CatererValidator} validates the Caterer {@link Capacity}.
 * <p>Custom validator entails us rolling out our own annotation and using it in our model to enforce the validation rules.</p>
 */
public class CatererValidator implements ConstraintValidator<CatererConstraint, Capacity> {

    private static final Logger logger = LoggerFactory.getLogger(CatererValidator.class);

    @Override
    public boolean isValid(Capacity capacity, ConstraintValidatorContext constraintValidatorContext) {
        //Checks if guests counts are not in negative.
        if (capacity.getMinGuest() < 0 || capacity.getMaxGuest() < 0) {
            logger.info("Received negative values of guests.");
            return false;
        }
        //Its a cross field validation MaxGuest should not be less then MinGuest.
        if (capacity.getMaxGuest() < capacity.getMinGuest()) {
            logger.info("Received invalid cross field validation.");
            return false;
        } else {
            logger.info("Minimum guests are lesser then maximum guest.");
            return true;
        }
    }
}
