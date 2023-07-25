package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shoeCondition")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String conditionName;
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
    public Condition() {

    }
    public Condition(Long id, String conditionName) {
        this.id = id;
        this.conditionName = conditionName;
    }



    public static List<String> getAllConditionDisplayTexts() {
        List<String> displayTexts = new ArrayList<>();
        for (ShoeCondition condition : ShoeCondition.values()) {
            displayTexts.add(condition.displayText);
        }
        return displayTexts;
    }


    public Long getId() {
        return id;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }
}