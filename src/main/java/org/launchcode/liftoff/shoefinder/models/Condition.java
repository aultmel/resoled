package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "shoeCondition")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /*
    Enum to set the level of condition for the shoes. When a condition class is created:
    Condition condition = new Condition();
    the condition can be set by:
    condition.setCondition(Condition.ShoeCondition.NEW);
    .NEW, .LIKE_NEW, etc
     */
    public enum ShoeCondition {
        NEW("New"),
        LIKE_NEW("Like New"),
        GOOD("Good"),
        FAIR("Fair"),
        POOR("Poor");


        private final String displayText;

        ShoeCondition(String displayText) {
            this.displayText = displayText;
        }
    }

    public static List<ShoeCondition> getAllConditions() {
        return Arrays.asList(ShoeCondition.values());
    }
    public static List<String> getAllConditionDisplayTexts() {
        List<String> displayTexts = new ArrayList<>();
        for (ShoeCondition condition : ShoeCondition.values()) {
            displayTexts.add(condition.displayText);
        }
        return displayTexts;
    }

    private ShoeCondition shoeCondition;

    public Condition(ShoeCondition condition) {
        this.shoeCondition = condition;
    }

    public Long getId() {
        return id;
    }


    public ShoeCondition getCondition() {
        return shoeCondition;
    }

    public void setCondition(ShoeCondition condition) {
        this.shoeCondition = condition;
    }



}