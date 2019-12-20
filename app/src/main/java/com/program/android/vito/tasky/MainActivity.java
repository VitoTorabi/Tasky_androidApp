package com.program.android.vito.tasky;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    public SqliteDatabase db;
    ArrayList<Button> days;
    ImageButton menu;
    LinearLayout menuLayout;
    Animation openMenu;
    Animation closeMenu;
    Fragment frag;
    FragmentTransaction transaction;
    Button clicked;
    int day;

    public void refreshFrag(){
        frag.onStart();
    }

    public Onclick taskOnClick(final MyTask task){
        Onclick onclick = new Onclick() {
            @Override
            public void onClickL() {
                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.task_view_dialog, null);


                final EditText editTextM = mView.findViewById(R.id.addTaskM);
                final EditText editTextD = mView.findViewById(R.id.addTaskD);
                final EditText title = mView.findViewById(R.id.addTaskTitle);
                final EditText text = mView.findViewById(R.id.addTaskText);
                final EditText h = mView.findViewById(R.id.addTaskTimeH);
                final EditText m = mView.findViewById(R.id.addTaskTimeM);
                final EditText alarmH = mView.findViewById(R.id.addTaskAlarmH);
                final EditText alarmM = mView.findViewById(R.id.addTaskAlarmM);
                editTextM.setText(task.month);
                editTextD.setText(task.day);
                title.setText(task.title);
                h.setText(task.timeH);
                m.setText(task.timeM);
                text.setText(task.text);
                alarmH.setText(task.alarmH);
                alarmM.setText(task.alarmM);

                Button save = mView.findViewById(R.id.addTaskSave);

                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(2);
                editTextD.setFilters(filters);
                editTextM.setFilters(filters);


                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyTask task = new MyTask(String.valueOf(editTextM.getText()),
                                String.valueOf(editTextD.getText()),
                                String.valueOf(title.getText()), String.valueOf(text.getText()),
                                String.valueOf(h.getText()), String.valueOf(m.getText()));
                        task.setAlarmTime(String.valueOf(alarmH.getText()),
                                String.valueOf(alarmM.getText()));
                        db.addTask(task);
                        dialog.dismiss();
                    }
                });
            }
        };
        return onclick;
    }

    public Onclick setOnclickL(final Fragment frag){
        Onclick onclick = new Onclick() {
            @Override
            public void onClickL() {
                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.task_view_dialog, null);


                final EditText editTextM = mView.findViewById(R.id.addTaskM);
                final EditText editTextD = mView.findViewById(R.id.addTaskD);
                final EditText title = mView.findViewById(R.id.addTaskTitle);
                final EditText text = mView.findViewById(R.id.addTaskText);
                final EditText h = mView.findViewById(R.id.addTaskTimeH);
                final EditText m = mView.findViewById(R.id.addTaskTimeM);
                final EditText alarmH = mView.findViewById(R.id.addTaskAlarmH);
                final EditText alarmM = mView.findViewById(R.id.addTaskAlarmM);

                Button save = mView.findViewById(R.id.addTaskSave);

                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(2);
                editTextD.setFilters(filters);
                editTextM.setFilters(filters);


                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyTask task = new MyTask(String.valueOf(editTextM.getText()),
                                String.valueOf(editTextD.getText()),
                                String.valueOf(title.getText()), String.valueOf(text.getText()),
                                String.valueOf(h.getText()), String.valueOf(m.getText()));
                        task.setAlarmTime(String.valueOf(alarmH.getText()),
                                String.valueOf(alarmM.getText()));
                        db.addTask(task);
                        dialog.dismiss();
                        refreshFrag();
                    }
                });
            }
        };

        return onclick;
    }

    public void weekDayClick(View view){
        TodayFrag todayFrag = new TodayFrag();
        todayFrag.onclick=setOnclickL(todayFrag);
        NextDaysFrag nextDaysFrag = new NextDaysFrag();
        DaysBeforeFrag daysBeforeFrag = new DaysBeforeFrag();

        clicked.setBackgroundResource(R.drawable.day_button);
        clicked.setTextColor(getResources().getColor(R.color.dark));
        clicked = (Button)view;
        clicked.setBackgroundResource(R.drawable.day_button_with_gravity);
        clicked.setTextColor(getResources().getColor(R.color.light));
        if(Integer.valueOf((String) clicked.getTag())<day){
            frag = daysBeforeFrag;
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame, daysBeforeFrag);
            transaction.commit();
        }else if(Integer.valueOf((String) clicked.getTag())==day){
            frag = todayFrag;
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame, todayFrag);
            transaction.commit();
        }else {
            frag = nextDaysFrag;
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame, nextDaysFrag);
            transaction.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SqliteDatabase(MainActivity.this);

        days = new ArrayList<>();
        menu = findViewById(R.id.menu);
        days.add((Button) findViewById(R.id.day0));
        days.add((Button) findViewById(R.id.day1));
        days.add((Button) findViewById(R.id.day2));
        days.add((Button) findViewById(R.id.day3));
        days.add((Button) findViewById(R.id.day4));
        days.add((Button) findViewById(R.id.day5));
        days.add((Button) findViewById(R.id.day6));

        final Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_WEEK);
        if(day==7)
            day=0;


        final TodayFrag todayFrag = new TodayFrag();
        frag = todayFrag;
        todayFrag.onclick = setOnclickL(todayFrag);
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
