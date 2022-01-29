package ru.guzeyst.compositionnumber.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswer: Int,
    val minPersentOfRightAnswers: Int,
    val timeGameInSecond: Int
    )