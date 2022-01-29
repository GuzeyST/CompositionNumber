package ru.guzeyst.compositionnumber.domain.entity

data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestion: Int,
    val gameSettings: GameSettings
)