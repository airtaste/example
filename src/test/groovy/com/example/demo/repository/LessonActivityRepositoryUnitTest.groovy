package com.example.demo.repository

import com.example.demo.domain.LessonActivity
import com.example.demo.domain.LessonActivityData
import com.example.demo.domain.LessonActivityDataBuilder
import org.bson.Document
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.MongoCollectionUtils
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import spock.lang.Specification
import spock.lang.Subject

class LessonActivityRepositoryUnitTest extends Specification {

    private static final Logger log = LoggerFactory.getLogger( LessonActivityRepositoryUnitTest )

    def mongoOperations = Mock( MongoOperations )
    def randomizer = new Random()

    @Subject sut = new DefaultLessonActivityRepository( mongoOperations )

    def 'exercise findUserLessonActivity'() {

        given: 'expected records'
        def expected = ( 1..5 ).collect { new LessonActivityDataBuilder().build() }

        and: 'a known collection name'
        def collectionName = MongoCollectionUtils.getPreferredCollectionName( LessonActivity )

        when: 'totals are retrieved'
        def results = sut.findUserLessonActivity(
                UUID.randomUUID(),
                ( 1..5 ).collect { UUID.randomUUID() },
                randomizer.nextLong(),
                randomizer.nextLong() )

        then: 'mongo is called as expected'
        1 * mongoOperations.aggregate( !null as Aggregation, collectionName, LessonActivityData ) >> { Aggregation aggregation, collection, data ->
            def aggregationBlock = aggregation.toDocument( collectionName, Aggregation.DEFAULT_CONTEXT )
            log.debug 'Query: {}', aggregationBlock.toJson()
            new AggregationResults( expected, new Document() )
        }

        and: 'the expected results are returned'
        results == expected
    }
}
