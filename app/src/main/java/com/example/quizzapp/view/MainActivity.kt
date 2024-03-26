package com.example.quizzapp.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.quizzapp.R
import com.example.quizzapp.databinding.ActivityMainBinding
import com.example.quizzapp.model.QuestionsListItem
import com.example.quizzapp.viewmodel.QuizViewModel

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


    }
}