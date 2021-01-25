package com.example.demo.repository

import com.example.demo.domain.LessonActivityData

interface LessonActivityRepositoryExtension {

    List<LessonActivityData> findUserLessonActivity( final UUID userId,
                                                     final List<UUID> lessonIds,
                                                     final long startDate,
                                                     final long stopDate )
}
