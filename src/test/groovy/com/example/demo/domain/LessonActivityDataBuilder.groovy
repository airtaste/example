package com.example.demo.domain

class LessonActivityDataBuilder implements Builder<LessonActivityData> {

    private final Random randomDataGenerator = new Random()

    @Override
    LessonActivityData build() {
        new LessonActivityData().tap {
            dateCode = randomDataGenerator.nextLong()
            lessonId = UUID.randomUUID()
            userId = UUID.randomUUID()
            name = Integer.toHexString( randomDataGenerator.nextInt() )
            duration = randomDataGenerator.nextLong()
        }
    }
}
