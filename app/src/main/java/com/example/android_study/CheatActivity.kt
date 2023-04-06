package com.example.android_study

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val EXTRA_ANSWER_SHOWN = "com.example.android_study.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.android_study.answer_is_true"
private const val KEY_IS_CHEATER = "isCheater"

class CheatActivity : AppCompatActivity() {

    private var isShown = false

    private val answerIsTrue by lazy {
        intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
    }

    private val answerTextView by lazy {
        findViewById<TextView>(R.id.answer_text_view)
    }

    private val showAnswerButton by lazy {
        findViewById<Button>(R.id.show_answer_button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        isShown = savedInstanceState?.getBoolean(KEY_IS_CHEATER) ?: false


        when {
            isShown -> {
                val answerText = when {
                    answerIsTrue -> R.string.true_button
                    else -> R.string.false_button
                }
                answerTextView.setText(answerText)
                setAnswerShownResult(true)
            }
        }

        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            isShown = true
            setAnswerShownResult(true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putBoolean(KEY_IS_CHEATER, isShown)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

}