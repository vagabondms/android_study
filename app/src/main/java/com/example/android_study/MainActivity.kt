package com.example.android_study

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        Log.d(TAG, "quizViewModel initialized")

        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.d(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreateCalled")

        quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0

        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)


        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveForward()
            updateQuestion()
        }

        previousButton.setOnClickListener {
            quizViewModel.moveBackward()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            val intent =
                CheatActivity.newIntent(this@MainActivity, quizViewModel.currentQuestionAnswer)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        updateQuestion()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            REQUEST_CODE_CHEAT -> {
                quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        questionTextView.setText(quizViewModel.currentQuestionText)
        quizViewModel.enableButton(quizViewModel.currentQuestionResult)
    }

    private fun checkAnswer(userAnswer: Boolean) {

        val messageResId = when (userAnswer == quizViewModel.currentQuestionAnswer) {
            quizViewModel.isCheater -> R.string.judgement_toast

            (userAnswer == quizViewModel.currentQuestionAnswer) -> {
                quizViewModel.updateCurrentQuestionResult(true)
                updateQuestion()
                quizViewModel.enableButton(false)
                R.string.correct_toast
            }
            else -> {
                quizViewModel.updateCurrentQuestionResult(false)
                updateQuestion()
                quizViewModel.enableButton(true)
                R.string.incorrect_toast
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}