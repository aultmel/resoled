package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

public class Condition {
    @Id
    @GeneratedValue
    int id;
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

    private ShoeCondition condition;

    public Condition(ShoeCondition condition) {
        this.condition = condition;
    }

    public int getId() {
        return id;
    }


    public ShoeCondition getCondition() {
        return condition;
    }

    public void setCondition(ShoeCondition condition) {
        this.condition = condition;
    }
}
