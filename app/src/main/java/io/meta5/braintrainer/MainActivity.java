package io.meta5.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rlMain;
    TextView tvTimer,tvProblem,tvScore,tvStatus;
    Random random1;
    Button correctBtn,playagain;
    int score=0,tries =0;
    boolean isActive;
    GridLayout glBtns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlMain = (RelativeLayout)findViewById(R.id.rlMain);
        rlMain.setVisibility(View.INVISIBLE);
        tvTimer = (TextView)findViewById(R.id.tvTimer);
        tvProblem = (TextView)findViewById(R.id.tvProblem);
        tvScore = (TextView)findViewById(R.id.tvScore);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        playagain = (Button)findViewById(R.id.btnPlayAgain);
        glBtns = (GridLayout)findViewById(R.id.glBtns);
        random1 = new Random();
    }

    public void play (View view){
        view.setVisibility(View.INVISIBLE);
        playagain.setVisibility(View.INVISIBLE);
        rlMain.setVisibility(View.VISIBLE);
        //enable btns
        for (int i=0;i<glBtns.getChildCount();i++){
            View view1 = glBtns.getChildAt(i);
            view1.setClickable(true);
        }
        tvTimer.setText("30s");
        tvScore.setText("0/0");
        tvStatus.setText("");
        correctBtn = update(createProblem());
        isActive = true;
        score = 0; tries = 0;
        new CountDownTimer(30000+100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText((int)millisUntilFinished/1000+"s");


            }

            @Override
            public void onFinish() {
                tvTimer.setText("0s");
                isActive = false;
                tvStatus.setText("Your score: " + score + "/"+tries);
                playagain.setVisibility(View.VISIBLE);

                for (int i=0;i<glBtns.getChildCount();i++){
                    View view = glBtns.getChildAt(i);
                    view.setClickable(false);
                }

            }
        }.start();


    }

    public int createProblem(){
        int broj1 = random1.nextInt(48+1);
        int broj2 = random1.nextInt(48+1);

        tvProblem.setText(broj1 + " + " + broj2 );
        return broj1+broj2;
    }

    public Button update(int correct){

        int rand = random1.nextInt(4);
        int resId = getResources().getIdentifier("btn"+rand,"id",getPackageName());
        Button correctBtn = (Button)findViewById(resId);
        correctBtn.setText(Integer.toString(correct));
        for (int i=0;i<4;i++){
            if(i!=rand){
                Button falseBtn = (Button)findViewById(getResources().getIdentifier("btn"+i,"id",getPackageName()));
                falseBtn.setText(Integer.toString(random1.nextInt(99+1)));
            }
        }
        return correctBtn;
    }

    public void guess(View view){
        if(view.getTag() == correctBtn.getTag()){
            score++;
            updateScore();
            tvStatus.setText("Tačno!");
            correctBtn = update(createProblem());
        }else{
            updateScore();
            tvStatus.setText("Netačno!");
            correctBtn = update(createProblem());
        }

    }

    public void updateScore(){
        tries++;
        tvScore.setText(score + "/" + tries);
    }
}
