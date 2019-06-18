package com.codecool.compiluserrorus.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String setRomanDate(LocalDate postingDate) {
        LocalDate changedPosting = postingDate.minusYears(1960);
        return changedPosting.format(DateTimeFormatter.ofPattern("yy-MM-dd"));
    }
}
