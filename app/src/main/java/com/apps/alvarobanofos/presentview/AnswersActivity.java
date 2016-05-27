package com.apps.alvarobanofos.presentview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apps.alvarobanofos.presentview.Adapters.AnswersRecyclerAdapter;
import com.apps.alvarobanofos.presentview.Controllers.QuestionsController;
import com.apps.alvarobanofos.presentview.Helpers.DateParser;
import com.apps.alvarobanofos.presentview.Models.Answer;
import com.apps.alvarobanofos.presentview.Models.Question;
import com.github.lzyzsd.circleprogress.DonutProgress;

public class AnswersActivity extends AppCompatActivity {

    TextView tv_title;
    TextView textEnded;
    DonutProgress donutProgress;
    RecyclerView answersRecycler;
    Question question;
    int questionId;

    public static String TAG = "AnswersActivity";

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            loadView();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.answers_layout);

        questionId = getIntent().getIntExtra("questionId", 1);
        loadView();

    }

    private void loadView() {
        question = QuestionsController.getInstance(this).getQuestion(questionId);
        Log.d(TAG, "FINISHED: "+question.isFinished());

        tv_title = (TextView) findViewById(R.id.tv_answers_question_title);
        tv_title.setText(question.getTitle());
        textEnded = (TextView) findViewById(R.id.tv_ended_question);
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        answersRecycler = (RecyclerView) findViewById(R.id.answers_recycler_view);
        updateUI(question);
        loadAnswers();
    }

    private void updateUI(Question question) {

        long now = DateParser.now();
        final long timeStart = question.getTime_ini().getTime();
        final long timeEnd = timeStart + (question.getDuration() * 1000);


        //Encuesta finalizada
        if(now > timeEnd) {
            changeDonutPerText(getString(R.string.question_finished), DateParser.getStringFromLong(timeEnd, "dd/MM/yyyy' a las 'HH:mm:ss"));
        }

        //Encuesta no empezada
        else if(now < timeStart) {
            changeDonutPerText("Pregunta no iniciada");
            new CountDownTimer(timeStart-now, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    activateDonut(timeEnd, timeStart);

                }
            }.start();
        }

        else {
            activateDonut(timeEnd, now);
        }



    }

    private void activateDonut(final long timeEnd, long timeStart) {
        textEnded.setVisibility(View.INVISIBLE);
        donutProgress.setSuffixText("s");
        long remainingTime = timeEnd-timeStart;
        donutProgress.setMax((int) (remainingTime));
        donutProgress.setProgress((int) remainingTime);
        donutProgress.setVisibility(View.VISIBLE);



        new CountDownTimer(remainingTime, 10) {

            public void onTick(long millisUntilFinished) {
                donutProgress.setProgress((int)millisUntilFinished);
                donutProgress.setText(""+(int)millisUntilFinished/1000+"s");
            }

            public void onFinish() {
                donutProgress.setProgress(0);
                donutProgress.setText("Fin");
                changeDonutPerText("Esperando resultados...");

            }
        }.start();
    }

    private void changeDonutPerText(String string, String time_ini) {
        donutProgress.setVisibility(View.INVISIBLE);
        String textEnd = String.format(string, time_ini);
        textEnded.setText(textEnd);
        textEnded.setVisibility(View.VISIBLE);
    }




    private void changeDonutPerText(String string) {
        donutProgress.setVisibility(View.INVISIBLE);
        textEnded.setText(string);
        textEnded.setVisibility(View.VISIBLE);
    }

    private void loadAnswers() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }


        };
        RecyclerView.Adapter mAdapter;

        mAdapter = new AnswersRecyclerAdapter(QuestionsController.getInstance(this).getAnswersAdapter(question), this, question);


        answersRecycler.setLayoutManager(mLayoutManager);
        answersRecycler.setAdapter(mAdapter);
    }

    public void confirmAnswer(int answerId) {
        for(Answer answer : question.getAnswers()) {
            if(answer.getId() == answerId) {
                answer.setSelected(true);
            }
        }
        question.setFinished(true);
        QuestionsController.getInstance(this).updateQuestion(question);
        QuestionsController.getInstance(this).sendQuestionChangedEvent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("questionsChanged"));
    }
}
