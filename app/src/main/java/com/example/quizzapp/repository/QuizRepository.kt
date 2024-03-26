package com.example.quizzapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.model.QuestionsList
import com.example.quizzapp.retrofit.QuestionsAPI
import com.example.quizzapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizRepository {

    var questionsAPI: QuestionsAPI

    init {
        questionsAPI = RetrofitInstance().getRetrofitInstance()
            .create(QuestionsAPI::class.java)
    }

    fun getQuestionsFromAPI(): LiveData<QuestionsList>{
        var data = MutableLiveData<QuestionsList>()

        var questionsList : QuestionsList

        GlobalScope.launch(Dispatchers.IO){
            val response = questionsAPI.getQuestions()

            if(response != null){
                //save data to array list
                questionsList = response.body()!!

                //always use with mutabledata and coroutines the postValue, not the set or make value
                data.postValue(questionsList)
                Log.i("TAGY", ""+data.value)

            }

        }

        return data;
    }

}