package ru.guzeyst.compositionnumber.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.guzeyst.compositionnumber.R
import ru.guzeyst.compositionnumber.data.GameRepoImpl
import ru.guzeyst.compositionnumber.domain.entity.GameResult
import ru.guzeyst.compositionnumber.domain.entity.GameSettings
import ru.guzeyst.compositionnumber.domain.entity.Level
import ru.guzeyst.compositionnumber.domain.entity.Question
import ru.guzeyst.compositionnumber.domain.useCase.GenerateQuestionUseCase
import ru.guzeyst.compositionnumber.domain.useCase.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var _formatedTime: MutableLiveData<String>
    val formatedTime: LiveData<String>
        get() = _formatedTime

    private lateinit var _question: MutableLiveData<Question>
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    private lateinit var _progressAnswers: MutableLiveData<String>
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val context = application
    private var countOfRightAnswers = 0
    private var countOfQuestion = 0

    private lateinit var level: Level
    private lateinit var gameSetting: GameSettings

    private var timer: CountDownTimer? = null
    private val repo = GameRepoImpl()
    private val generateQuestionUseCase = GenerateQuestionUseCase(repo)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repo)

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        generateQuestion()
    }

    private fun updateProgress() {
        val percentOfRightAnswers = calculatePercentOfRightAnswers()
        _percentOfRightAnswer.value = percentOfRightAnswers
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSetting.minCountOfRightAnswer
        )
        _enoughCount.value = countOfRightAnswers >= gameSetting.minCountOfRightAnswer
        _enoughPercent.value =
            percentOfRightAnswers >= gameSetting.minPersentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return ((countOfQuestion / countOfQuestion.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestion++
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSetting = getGameSettingsUseCase(level)
        _minPercent.value = gameSetting.minPersentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSetting.timeGameInSecond * MILISEC_IN_SECOND,
            MILISEC_IN_SECOND
        ) {
            override fun onTick(p0: Long) {
                _formatedTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    private fun formatTime(p0: Long): String {
        val seconds = p0 / MILISEC_IN_SECOND
        val minutes = seconds / 60
        val leftSeconds = seconds - minutes * SECONDS_IN_MINUTES
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSetting.maxSumValue)
    }

    private fun finishGame() {
        val gameResult = GameResult(
            winner = enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestion = countOfQuestion,
            gameSettings = gameSetting
        )
        _gameResult.value = gameResult
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
        finishGame()
    }

    companion object {
        private const val MILISEC_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

}