package com.example.android_study

import android.util.Log
import androidx.lifecycle.ViewModel


private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))
    private var disabled = false
    var currentIndex = 0


    val currentQuestionAnswer: Boolean
    get() = questionBank[currentIndex].answer

    val currentQuestionText:Int
    get() = questionBank[currentIndex].textResId

    val currentQuestionResult: Boolean
    get() = questionBank[currentIndex].right

    var currentQuestionIsCheated: Boolean
    get() = questionBank[currentIndex].isCheated
    set(value){
        questionBank[currentIndex].isCheated = value
    }


    init {
        Log.d(TAG, "quizeViewMOdel")
    }


    fun updateCurrentQuestionResult(value: Boolean){
        questionBank[currentIndex].right = value
    }

    fun moveForward(){
        move(+1)
    }

    fun moveBackward(){
        move(-1)
    }

    fun enableButton(enable: Boolean){
        disabled = enable
    }

    private fun move(direction: Int){
        currentIndex = (currentIndex + direction) % questionBank.size
    }


}