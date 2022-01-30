package ru.guzeyst.compositionnumber.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswer: Int,
    val minPersentOfRightAnswers: Int,
    val timeGameInSecond: Int
    ): Parcelable