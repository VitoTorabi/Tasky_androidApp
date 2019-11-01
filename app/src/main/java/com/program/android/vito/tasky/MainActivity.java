package com.program.android.vito.tasky;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

//    Resources res = getResources();

    ArrayList<Button> days;
    ImageButton menu;
    LinearLayout menuLayout;
    Animation openMenu;
    Animation closeMenu;
    FragmentTransaction transaction;
    Button clicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        dayName = res.getStringArray(R.array.days);

        days = new ArrayList<>();
        menu = findViewById(R.id.menu);
        days.add((Button) findViewById(R.id.day0));
        days.add((Button) findViewById(R.id.day1));
        days.add((Button) findViewById(R.id.day2));
        days.add((Button) findViewById(R.id.day3));
        days.add((Button) findViewById(R.id.day4));
        days.add((Button) findViewById(R.id.day5));
        days.add((Button) findViewById(R.id.day6));

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if(day==7)
            day=0;

        final TextView debug = findViewById(R.id.debug);
        //debug.setText(String.valueOf(Calendar.SATURDAY));

        switch (day) {
            case 0: //Saturday
                clicked =days.get(0);
                clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
                clicked.setTextColor(getResources().getColor(R.color.light));
                clicked.setText(R.string.today);
                break;
            case Calendar.SUNDAY:
                clicked =days.get(1);
                clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
                clicked.setTextColor(getResources().getColor(R.color.light));
                clicked.setText(R.string.today);
                break;
            case Calendar.MONDAY:
                clicked =days.get(2);
                clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
                clicked.setTextColor(getResources().getColor(R.color.light));
                clicked.setText(R.string.today);
                break;
            case Calendar.TUESDAY:
                clicked =days.get(3);
                clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
                clicked.setTextColor(getResources().getColor(R.color.light));
                clicked.setText(R.string.today);
                break;
            case Calendar.WEDNESDAY:
                clicked =days.get(4);
                clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
                clicked.setTextColor(getResources().getColor(R.color.light));
                clicked.setText(R.string.today);
                break;
            case Calendar.THURSDAY:
                clicked =days.get(5);
                clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
                clicked.setTextColor(getResources().getColor(R.color.light));
                clicked.setText(R.string.today);
                break;
            case Calendar.FRIDAY:
                clicked =days.get(6);
                clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
                clicked.setTextColor(getResources().getColor(R.color.light));
                clicked.setText(R.string.today);
                break;
        }
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, new TodayFrag());
        transaction.commit();

        final int finalDay =day;
        for(int i=0;i<7;i++){
            final int finalI = i;
            days.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicked.setBackgroundResource(R.drawable.day_button);
                    clicked.setTextColor(getResources().getColor(R.color.dark));
                    clicked = (Button)v;
                    //debug.setText(String.valueOf(finalI));
                    clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
                    clicked.setTextColor(getResources().getColor(R.color.light));
                    if(finalI<finalDay){
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.mainFrame, new DaysBeforeFrag());
                        transaction.commit();
                    }else if(finalI==finalDay){
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.mainFrame, new TodayFrag());
                        transaction.commit();
                    }else {
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.mainFrame, new NextDaysFrag());
                        transaction.commit();
                    }
                }
            });
        }
        menuLayout = findViewById(R.id.menu_layout);
        openMenu = AnimationUtils.loadAnimation(this, R.anim.open_menu);
        closeMenu = AnimationUtils.loadAnimation(this, R.anim.close_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuLayout.getVisibility()== View.VISIBLE){
                    menuLayout.startAnimation(closeMenu);
                    menuLayout.setVisibility(View.GONE);
                }else {
                    menuLayout.startAnimation(openMenu);
                    menuLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
