package ru.guzeyst.compositionnumber.data

import ru.guzeyst.compositionnumber.domain.entity.GameSettings
import ru.guzeyst.compositionnumber.domain.entity.Level
import ru.guzeyst.compositionnumber.domain.entity.Question
import ru.guzeyst.compositionnumber.domain.repository.GameRepo

class GameRepoImpl: GameRepo {
    override fun generateQuestion(maxSummValue: Int, countOfOptions: Int): Question {
        TODO("Not yet implemented")
    }

    override fun getGameSettings(level: Level): GameSettings {
        TODO("Not yet implemented")
    }
}