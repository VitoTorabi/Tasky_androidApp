package com.program.android.vito.tasky;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {


    ArrayList<Button> days;
    ImageButton menu;
    LinearLayout menuLayout;
    Animation openMenu;
    Animation closeMenu;
    FragmentTransaction transaction;
    Button clicked;
    int day;

    public void weekDayClick(View view){
        clicked.setBackgroundResource(R.drawable.day_button);
        clicked.setTextColor(getResources().getColor(R.color.dark));
        clicked = (Button)view;
        clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
        clicked.setTextColor(getResources().getColor(R.color.light));
        if(Integer.valueOf((String) clicked.getTag())<day){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame, new DaysBeforeFrag());
            transaction.commit();
        }else if(Integer.valueOf((String) clicked.getTag())==day){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame, new TodayFrag());
            transaction.commit();
        }else {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame, new NextDaysFrag());
            transaction.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        day = calendar.get(Calendar.DAY_OF_WEEK);
        if(day==7)
            day=0;



        TodayFrag todayFrag = new TodayFrag();
        todayFrag.onclick = new Onclick() {
            @Override
            public void onClickL() {
                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.task_view_dialog, null);
                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
        clicked =days.get(day);
        clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
        clicked.setTextColor(getResources().getColor(R.color.light));
        clicked.setText(R.string.today);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, todayFrag);
        transaction.commit();

//        TodayFrag.addTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
//                View mView = getLayoutInflater().inflate(R.layout.task_view_dialog, null);
//                builder.setView(mView);
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });

        menuLayout = findViewById(R.id.menu_layout);
        final FrameLayout menuFrame = findViewById(R.id.menu_frame);
        openMenu = AnimationUtils.loadAnimation(this, R.anim.open_menu);
        closeMenu = AnimationUtils.loadAnimation(this, R.anim.close_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuLayout.getVisibility()== View.VISIBLE){
                    menuLayout.startAnimation(closeMenu);
                    menuLayout.setVisibility(View.GONE);
                    menuFrame.setVisibility(View.GONE);
                }else {
                    menuFrame.setVisibility(View.VISIBLE);
                    menuLayout.startAnimation(openMenu);
                    menuLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
