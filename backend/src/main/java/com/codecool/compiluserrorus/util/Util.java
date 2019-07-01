package com.codecool.compiluserrorus.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String setRomanDate(LocalDateTime postingDate) {
        LocalDateTime changedPosting = postingDate.minusYears(1960);
        return changedPosting.format(DateTimeFormatter.ofPattern("yy-MM-dd"));
    }
}
