package org.launchcode.liftoff.shoefinder.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListingConstants {

    //OTHER OPTIONS FOR CONDITION ?
    //        NEW("New"),
//        LIKE_NEW("Like New"),
//        GOOD("Good"),
//        FAIR("Fair"),
//        POOR("Poor");
    // Initial Style Names
    public static final List<String> INITIAL_STYLE_LIST = new ArrayList<>(Arrays.asList(
            "Running",
            "Casual",
            "Athletic",
            "Sandals",
            "Formal"
    ));


    public static final List<String> CONDITION_LIST = new ArrayList<>(Arrays.asList("NEW", "GREAT", "GOOD", "POOR"));

    public static final List<String> GENDER_LIST = new ArrayList<>(Arrays.asList("W", "M"));

    public static final List<String> SIZE_LIST = new ArrayList<>(Arrays.asList(
            "3.0",
            "3.5",
            "4.0",
            "4.5",
            "5.0",
            "5.5",
            "6.0",
            "6.5",
            "7.0",
            "7.5",
            "8.0",
            "8.5",
            "9.0",
            "9.5",
            "10.0",
            "10.5",
            "11.0",
            "11.5",
            "12.0",
            "12.5",
            "13.0",
            "13.5",
            "14.0",
            "14.5",
            "15.0",
            "15.5",
            "16.0",
            "16.5",
            "17.0"
    ));

    public static final List<String> NORMAL_SIZE_LIST = new ArrayList<>(Arrays.asList(
            "6.0",
            "6.5",
            "7.0",
            "7.5",
            "8.0",
            "8.5",
            "9.0",
            "9.5",
            "10.0",
            "10.5",
            "11.0",
            "11.5",
            "12.0",
            "12.5",
            "13.0"
    ));


// **************************
//// IF WE USE DOUBLE INSTEAD OF STRING
// ****************************
//    public static final List<Double> SIZE_LIST = new ArrayList<>(Arrays.asList(
//            3.0,
//            3.5,
//            4.0,
//            4.5,
//            5.0,
//            5.5,
//            6.0,
//            6.5,
//            7.0,
//            7.5,
//            8.0,
//            8.5,
//            9.0,
//            9.5,
//            10.0,
//            10.5,
//            11.0,
//            11.5,
//            12.0,
//            12.5,
//            13.0,
//            13.5,
//            14.0,
//            14.5,
//            15.0,
//            15.5,
//            16.0,
//            16.5,
//            17.0
//    ));


}