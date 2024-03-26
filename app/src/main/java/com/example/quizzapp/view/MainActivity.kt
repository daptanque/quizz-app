package com.example.quizzapp.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizzapp.R
import com.example.quizzapp.databinding.ActivityMainBinding
import com.example.quizzapp.model.QuestionsListItem
import com.example.quizzapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quizViewModel: QuizViewModel
    lateinit var questionsList: List<QuestionsListItem>


    companion object{
        var result = 0
        var totalQuestions = 0


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        //Reset the score
        result = 0
        totalQuestions = 0

        //Getting the response
        quizViewModel = ViewModelProvider(this)
            .get(QuizViewModel::class.java)

        //Display first question - need to put this because despite running on MainThread you have the repo that is running on IO - getQuestions is suspend fun
        GlobalScope.launch(Dispatchers.Main){
            quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {
                if(it.size>0){
                    questionsList = it
                    Log.i("TAGY", "The first questions: ${questionsList[0]}")

                    binding.apply {
                        txtQuestion.text = questionsList!![0].question
                        radio1.text = questionsList!![0].option1
                        radio2.text = questionsList!![0].option2
                        radio3.text = questionsList!![0].option3
                        radio4.text = questionsList!![0].option4

                    }
                }
            })
        }

        //Next Button
        var i = 1
        binding.apply {
            btnNext.setOnClickListener(View.OnClickListener {
                val selectedOption = radiogroup?.checkedRadioButtonId

                if(selectedOption != -1){
                    val radbutton = findViewById<View>(selectedOption!!) as RadioButton

                    questionsList.let{
                        if(i< it.size!!){
                            totalQuestions = it.size
                            if(radbutton.text.toString().equals(it[i-1].correct_option)){
                                result++
                                txtResult?.text="Correct Answer: $result"
                            }

                            //Next Question
                            txtQuestion.text = "Question Number ${i+1}: " + questionsList[i].question
                            radio1.text = it[i].option1
                            radio2.text = it[i].option2
                            radio3.text = it[i].option3
                            radio4.text = it[i].option4

                            //check Last question
                            if(i == it.size!!.minus(1)){
                                btnNext.text = "FINISH"
                            }

                            radiogroup?.clearCheck()
                            i++
                        }else{
                            if(radbutton.text.toString().equals(it[i-1].correct_option)){
                                result++
                                txtResult?.text = "CorrectAnswer: $result"
                            }else{

                            }
                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    Toast.makeText(this@MainActivity,"Select an option", Toast.LENGTH_LONG).show()
                }
            })
        }

    }
}