package ru.guzeyst.compositionnumber.domain.useCase

import ru.guzeyst.compositionnumber.domain.entity.GameSettings
import ru.guzeyst.compositionnumber.domain.entity.Level
import ru.guzeyst.compositionnumber.domain.repository.GameRepo

class GetGameSettingsUseCase(
    private val repo: GameRepo
    )
{
    operator fun invoke(level: Level): GameSettings{
        return repo.getGameSettings(level)
    }
}