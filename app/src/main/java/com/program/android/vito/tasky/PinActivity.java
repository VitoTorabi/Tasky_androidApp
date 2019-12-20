package com.program.android.vito.tasky;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class PinActivity extends AppCompatActivity {

    Animation shake;
    LinearLayout pinLayout;
    ImageButton backspace;
    ArrayList<ImageView> pinCircles;
    final String s="";
    final String[] enteredPass = {s};
    Intent goToMain;
    final String pass ="1250";

    public void numClick(View view){
        goToMain = new Intent(PinActivity.this,MainActivity.class);
        Button b = (Button) view;
        enteredPass[0]+= b.getText();
        pinCircles.get(enteredPass[0].length()-1).setBackgroundResource(R.drawable.colored_circle);
        if (enteredPass[0].length() == 4) {
            if (enteredPass[0].equals(pass)) {
                startActivity(goToMain);
                finish();
            } else {
                pinLayout.startAnimation(shake);
                for (int i = 0; i < 4; i++) {
                    pinCircles.get(i).setBackgroundResource(R.drawable.circle);
                }
                enteredPass[0] = "";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        shake =AnimationUtils.loadAnimation(this, R.anim.shake);
        backspace = findViewById(R.id.bakspace);
        pinLayout = findViewById(R.id.pinLayout);
        pinCircles = new ArrayList<>();
        pinCircles.add((ImageView) findViewById(R.id.pin1));
        pinCircles.add((ImageView) findViewById(R.id.pin2));
        pinCircles.add((ImageView) findViewById(R.id.pin3));
        pinCircles.add((ImageView) findViewById(R.id.pin4));

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enteredPass[0].length()>0) {
                    pinCircles.get(enteredPass[0].length() - 1).setBackgroundResource(R.drawable.circle);
                    enteredPass[0]=enteredPass[0].substring(0,enteredPass[0].length()-1);
                }

            }
        });
    }
}
