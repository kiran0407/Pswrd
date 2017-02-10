package com.k4ench.pswrd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SignUpInputView mSignUpInputView;
    Button btn_Again;
    TextView tv_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Log in");
        mSignUpInputView = (SignUpInputView) findViewById(R.id.sign_btn);
       // btn_Again = (Button) findViewById(R.id.btn_again);
        tv_info = (TextView) findViewById(R.id.tv_info);
        mSignUpInputView.setBgPaintColor(Color.BLACK);
        mSignUpInputView.setTextPaintColor(Color.WHITE);
        mSignUpInputView.setTitlePaintColor(Color.GRAY);
        mSignUpInputView.setSetpIcon(R.mipmap.user,  R.mipmap.pwd);
        mSignUpInputView.setSetpName("Name",  "PassWord");
        mSignUpInputView.setmButtonText("Sign up");
        mSignUpInputView.setVerifyTypeStep(SignUpInputView.VerifyType.NULL,

                SignUpInputView.VerifyType.PASSWORD);
     /*   btn_Again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignUpInputView.setmButtonText("Sign up");
                mSignUpInputView.setAgain();

            }
        });*/
        mSignUpInputView.setOnGetStepInfo(new SignUpInputView.GetStepAndText() {
            int ver=0;
            @Override
            public void GetInfo(int step, String stepName, String text) {

                String info = tv_info.getText().toString();


                if (step == mSignUpInputView.getSetpCount() - 1) {
                    info = info + "\n" + step + ":" + stepName + ":" + text;
                    mSignUpInputView.setmButtonText("Welcome");
                  //  startActivity(new Intent(MainActivity.this,Welcome.class));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(new Intent(MainActivity.this,DetailsActivity.class));
                        }
                    }, 3000);
                } else if (step == -1) {
                    info = info + "\nonClick " + stepName;
                } else if (step == mSignUpInputView.getSetpCount()) {
                   info = info + "\nonClick " + stepName;

                } else {
                    info = info + "\n" + step + ":" + stepName + ":" + text;
                }

               // tv_info.setText(info);
            }

        });


    }
}
