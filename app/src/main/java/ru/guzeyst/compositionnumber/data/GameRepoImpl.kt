package ru.guzeyst.compositionnumber.data

import ru.guzeyst.compositionnumber.domain.entity.GameSettings
import ru.guzeyst.compositionnumber.domain.entity.Level
import ru.guzeyst.compositionnumber.domain.entity.Question
import ru.guzeyst.compositionnumber.domain.repository.GameRepo
import java.lang.Integer.min
import kotlin.math.max
import kotlin.random.Random

object GameRepoImpl: GameRepo {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSummValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSummValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSummValue -1, rightAnswer - countOfOptions)
        while(options.size < countOfOptions){
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST -> {
                GameSettings(
                    10,
                    3,
                    50,
                    8
                )
            }
            Level.EASY -> {
                GameSettings(
                    10,
                    10,
                    70,
                    60
                )
            }
            Level.NORMAL -> {
                GameSettings(
                    20,
                    20,
                    80,
                    50
                )
            }
            Level.HARD -> {
                GameSettings(
                    30,
                    30,
                    90,
                    40
                )
            }
        }
    }
}