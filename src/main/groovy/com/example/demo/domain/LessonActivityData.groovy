package com.example.demo.domain

import groovy.transform.Canonical
import org.springframework.data.mongodb.core.mapping.Field
import static com.example.demo.domain.LessonActivity.FieldNames.DATE_CODE
import static com.example.demo.domain.LessonActivity.FieldNames.LESSON_ID
import static com.example.demo.domain.LessonActivity.FieldNames.USER_ID
import static com.example.demo.domain.LessonActivity.FieldNames.NAME
import static com.example.demo.domain.LessonActivity.FieldNames.DURATION

@Canonical
class LessonActivityData {

    @Field( DATE_CODE )
    long dateCode

    @Field( LESSON_ID )
    UUID lessonId

    @Field( USER_ID )
    UUID userId

    @Field( NAME )
    String name

    @Field( DURATION )
    long duration
}
