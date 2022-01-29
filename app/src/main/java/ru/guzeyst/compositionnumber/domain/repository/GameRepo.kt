package ru.guzeyst.compositionnumber.domain.repository

import ru.guzeyst.compositionnumber.domain.entity.GameSettings
import ru.guzeyst.compositionnumber.domain.entity.Level
import ru.guzeyst.compositionnumber.domain.entity.Question

interface GameRepo {
    fun generateQuestion(
        maxSummValue: Int,
        countOfOptions: Int
    ): Question
    fun getGameSettings(level: Level): GameSettings
}