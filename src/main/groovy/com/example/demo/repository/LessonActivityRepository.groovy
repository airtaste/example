package com.example.demo.repository

import com.example.demo.domain.LessonActivity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LessonActivityRepository extends MongoRepository<LessonActivity, UUID>, LessonActivityRepositoryExtension { }
