package ru.guzeyst.compositionnumber.domain.useCase

import ru.guzeyst.compositionnumber.domain.entity.Question
import ru.guzeyst.compositionnumber.domain.repository.GameRepo

class GenerateQuestionUseCase(
    private val repo: GameRepo
    )
{
    operator fun invoke(maxSumValue: Int): Question{
        return repo.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}