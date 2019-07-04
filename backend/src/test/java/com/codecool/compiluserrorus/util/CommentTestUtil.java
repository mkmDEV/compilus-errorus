package com.codecool.compiluserrorus.util;

import com.codecool.compiluserrorus.model.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class CommentTestUtil {

    public static List<Comment> getOrderedComments(int posts) {
        List<Comment> orderedComments = new ArrayList<>();

        IntStream.rangeClosed(1, posts).forEach(minutes -> {
            int day = (minutes / 60 / 24);
            int hour = (minutes - (day * 60 * 24)) / 60;
            int minute = Math.abs(minutes - (hour * 60));

            Comment testComment = Comment.builder()
                    .message("Test message")
                    .postingDate(LocalDateTime.of(2019, 2, day + 1, hour, minute))
                    .build();

            orderedComments.add(testComment);
        });

        Collections.reverse(orderedComments);
        return orderedComments;
    }
}
