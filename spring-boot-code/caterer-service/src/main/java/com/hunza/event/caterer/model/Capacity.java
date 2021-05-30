package com.hunza.event.caterer.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author ishaan.solanki
 *
 * It is a model class which represents the guest count details of a caterer.
 * <p>This is a class which carries out the guest count against a caterer i.e minimum guest and maximum guest</p>
 */
@ToString
@Data
public class Capacity {
    private Integer minGuest;
    private Integer maxGuest;
}
