package com.example.quizapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.databinding.ScoreBinding

class Quiz : AppCompatActivity(),View.OnClickListener {

    companion object{

        var questionModelList:List<Questionmodel> = listOf()
        var time:String=""

    }

    lateinit var binding: ActivityQuizBinding
    var currentQuestionIndex=0
    var selectedAnswer=""
    var score=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            bTn1.setOnClickListener(this@Quiz)
            bTn2.setOnClickListener(this@Quiz)
            bTn3.setOnClickListener(this@Quiz)
            bTn4.setOnClickListener(this@Quiz)
            nextBTn.setOnClickListener(this@Quiz)
        }
        loadQuestions()
        startTimer()
    }
    private fun startTimer(){
        val totaltimeInMillis= time.toInt() * 60 * 1000L
        object :CountDownTimer(totaltimeInMillis,1000L){
            override fun onTick(millisUntilFinished: Long) {
                val seconds=millisUntilFinished / 1000
                val munites=seconds / 60
                val remainingSeconds= seconds % 60
                binding.timerIndecator.text=String.format("%02d:%02d",munites,remainingSeconds)
            }

            override fun onFinish() {
                //finish the quiz
            }

        }.start()
    }
    private fun loadQuestions(){

        selectedAnswer=""
        if(currentQuestionIndex== questionModelList.size){
            finishquiz()
            return
        }

        binding.apply {
            questionIndicator.text="Question ${currentQuestionIndex+1}/${questionModelList.size}"
            questionProgressIndicator.progress=
                (currentQuestionIndex.toFloat()/ questionModelList.size.toFloat()*100).toInt()
            questionTextView.text= questionModelList[currentQuestionIndex].question
            bTn1.text= questionModelList[currentQuestionIndex].options[0]
            bTn2.text= questionModelList[currentQuestionIndex].options[1]
            bTn3.text= questionModelList[currentQuestionIndex].options[2]
            bTn4.text= questionModelList[currentQuestionIndex].options[3]


        }
    }

    override fun onClick(view: View?) {

        binding.apply {
            bTn1.setBackgroundColor(getColor(R.color.gry))
            bTn2.setBackgroundColor(getColor(R.color.gry))
            bTn3.setBackgroundColor(getColor(R.color.gry))
            bTn4.setBackgroundColor(getColor(R.color.gry))
        }

        val clickedBtn=view as Button
        if (clickedBtn.id==R.id.next_bTn){
            //next button is clicked
            if(selectedAnswer.isEmpty())
            {
                Toast.makeText(applicationContext,"please select answer to continue",Toast.LENGTH_SHORT).show()
            }

            if(selectedAnswer== questionModelList[currentQuestionIndex].correct){
                score++;
                Log.i("Score of quiz",score.toString())
            }
            currentQuestionIndex++
            loadQuestions()
        }else{
            //option button is clicked
            selectedAnswer=clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orenge))
        }
    }
    private fun finishquiz(){

        val totalQuestion = questionModelList.size
        val percentage =((score.toFloat()/totalQuestion.toFloat())*100).toInt()

        val dialogBinding=ScoreBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress=percentage
            scoreProgressText.text="$percentage %"

            if(percentage>60){
                scoreTXt.text= getString(R.string.congrats_you_have_passed2)
                scoreTXt.setTextColor(Color.BLUE)
            }else{
                scoreTXt.text= getString(R.string.oops_you_have_failled1)
                scoreTXt.setTextColor(Color.RED)
            }
            scoreSubtitle.text="$score out of $totalQuestion are correct"

            finishBTn.setOnClickListener{
                finish()
            }
        }
        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }
}