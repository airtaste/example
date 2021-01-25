package com.example.demo.repository

import com.example.demo.domain.LessonActivity
import com.example.demo.domain.LessonActivityData
import org.springframework.data.mongodb.MongoCollectionUtils
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.aggregation.AggregationOptions
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match
import static org.springframework.data.mongodb.core.query.Criteria.where
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group
import static com.example.demo.domain.LessonActivity.FieldNames.DATE_CODE
import static com.example.demo.domain.LessonActivity.FieldNames.DURATION
import static com.example.demo.domain.LessonActivity.FieldNames.USER_ID
import static com.example.demo.domain.LessonActivity.FieldNames.LESSON_ID
import static com.example.demo.domain.LessonActivity.FieldNames.NAME

class DefaultLessonActivityRepository implements LessonActivityRepositoryExtension {

    private final MongoOperations theMongoOperations

    DefaultLessonActivityRepository( final MongoOperations mongoOperations ) {
        theMongoOperations = mongoOperations
    }

    @Override
    List<LessonActivityData> findUserLessonActivity( UUID userId,
                                                     List<UUID> lessonIds,
                                                     long startDate,
                                                     long stopDate ) {
        def aggregation = Aggregation.newAggregation(
                matchUserLessonCoursewareActivity( userId, lessonIds, startDate, stopDate ),
                groupUserLessonData()
        )
        def collectionName = MongoCollectionUtils.getPreferredCollectionName( LessonActivity )
        def options = new AggregationOptions( true, false, null )
        def updatedAggregation = aggregation.withOptions( options )
        def results = theMongoOperations.aggregate( updatedAggregation, collectionName, LessonActivityData )
        results.mappedResults as ArrayList<LessonActivityData>
    }

    private AggregationOperation groupUserLessonData() {
        group( LESSON_ID, NAME )
                .max( DATE_CODE ).as( DATE_CODE )
                .last( LESSON_ID ).as( LESSON_ID )
                .last( USER_ID ).as( USER_ID )
                .last( NAME ).as( NAME )
                .sum( DURATION ).as( DURATION )
    }

    private AggregationOperation matchUserLessonCoursewareActivity( final UUID userId,
                                                                    final List<UUID> lessonIds,
                                                                    final long startDate,
                                                                    final long stopDate ) {
        match( where( USER_ID ).is( userId ).and( LESSON_ID ).in ( lessonIds ).and( DATE_CODE ).lte( stopDate ).gte( startDate ) )
    }
}
