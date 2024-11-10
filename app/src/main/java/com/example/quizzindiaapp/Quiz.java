package com.example.quizzindiaapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import  java.util.List;
import java.util.ArrayList;

public class Quiz extends AppCompatActivity {

    private TextView tvQuestion,tvQuestionNo,tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1,rb2,rb3,rb4;
    private Button btnNext;

    int totalQuestions;
    int qCounter=0;

    int Score;

    boolean answered;

    CountDownTimer countDownTimer;




    private  QuizModel currentQuestion;


    private  List<QuizModel> quizModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Score=0;
        quizModelList=new ArrayList<>();
        tvQuestion =findViewById(R.id.textquestion);
        tvQuestionNo=findViewById(R.id.textquestins);
        tvTimer=findViewById(R.id.texttimer);

        radioGroup=findViewById(R.id.rg);
        radioGroup.clearCheck();
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);
        rb3=findViewById(R.id.rb3);
        rb4=findViewById(R.id.rb4);
        btnNext=findViewById(R.id.btnnext);



        addQuestions();
        totalQuestions=quizModelList.size();
        showNextQuestion();
        answered=false;

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answered==false){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() ){
                        checkAnswer();
                        countDownTimer.cancel();

                    }else{
                        Toast.makeText(Quiz.this, "Please Select an option ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showNextQuestion();
                }
            }
        });


    }

    private void checkAnswer() {

        answered=true;
        RadioButton rbselected=findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo =radioGroup.indexOfChild(rbselected) +1;
        if(answerNo== currentQuestion.getCorrectAnsNo()){
            Score++;
        }
        if(qCounter < totalQuestions){
            btnNext.setText("Next");
        }else{
            btnNext.setText("Finish");
        }

    }
    private void showNextQuestion() {

        radioGroup.clearCheck();




        if(qCounter< totalQuestions){
            timer();

            currentQuestion=quizModelList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            qCounter++;
            btnNext.setText("Submit");
            tvQuestionNo.setText("Question: "+qCounter+"/"+totalQuestions);
            answered=false;

        }else{
            // Assuming you have calculated the score and stored it in a variable called 'Score'
            Intent resultIntent = new Intent(Quiz.this, Resultpage.class);
            resultIntent.putExtra("user_score", Score); // Pass the score as an extra with a key
            startActivity(resultIntent);
        }
    }

    private void timer() {
        countDownTimer=new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("00:"+1/1000);
                long secondsRemaining = millisUntilFinished / 1000;
                tvTimer.setText(String.format("00:%02d", secondsRemaining));

            }

            @Override
            public void onFinish() {
                showNextQuestion();

            }
        }.start();
    }

    private void addQuestions() {

        quizModelList.add(new QuizModel("What is the capital of India? ", "Mumbai", "New Delhi", "Kolkata", "Bangalore", 2));
        quizModelList.add(new QuizModel("Which river is known as the 'Ganges' in India?", "Yamuna", "Brahmaputra", "Godavari", "Ganga", 4));
        quizModelList.add(new QuizModel("What is the largest mammal in the world?", "Elephant", "Giraffe", "Blue Whale", "Hippopotamus", 3));
        quizModelList.add(new QuizModel("The 'Taj Mahal' is located in which Indian city?", "New Delhi", "Agra", "Jaipur", "Varanasi", 2));
        quizModelList.add(new QuizModel("Which is the largest state in India by area?", "Maharashtra", "Uttar Pradesh", "Rajasthan", "Madhya Pradesh", 3));
        quizModelList.add(new QuizModel("Who wrote the Indian national anthem, 'Jana Gana Mana'? ", "Rabindranath Tagore", "Swami Vivekananda", "Mahatma Gandhi", "Jawaharlal Nehru", 1));
        quizModelList.add(new QuizModel("Which Indian state is known as the 'Land of Five Rivers'? ", "Punjab", "Haryana", "Uttar Pradesh", " Gujarat", 1));
        quizModelList.add(new QuizModel(" The first successful mission to Mars by India is known as:", "Mars Odyssey", " Mangalyaan", "Chandrayaan", "Mars Express", 2));
        quizModelList.add(new QuizModel(" What is the national animal of India?", "Lion", "Elephant", "Tiger", "Rhinoceros", 3));
        quizModelList.add(new QuizModel(" Which famous Indian monument was built in memory of Queen Mumtaz Mahal?", "Red Fort", "Qutub Minar", "Hawa Mahal", " Taj Mahal", 4));
    }
}
