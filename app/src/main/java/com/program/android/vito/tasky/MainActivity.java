package com.program.android.vito.tasky;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
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
    Calendar calendar = Calendar.getInstance();
    Fragment frag;
    FragmentTransaction transaction;
    public Button clicked;
    public int day;

    public void refreshFrag(){
        frag.onStart();
    }

    public Onclick taskOnClick(final MyTask task){
        Onclick onclick = new Onclick() {
            @Override
            public void onClickL() {
                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.task_view_dialog, null);


                final EditText dateM = mView.findViewById(R.id.addTaskM);
                final EditText dateD = mView.findViewById(R.id.addTaskD);
                final EditText title = mView.findViewById(R.id.addTaskTitle);
                final EditText text = mView.findViewById(R.id.addTaskText);
                final EditText h = mView.findViewById(R.id.addTaskTimeH);
                final EditText m = mView.findViewById(R.id.addTaskTimeM);
                final EditText alarmH = mView.findViewById(R.id.addTaskAlarmH);
                final EditText alarmM = mView.findViewById(R.id.addTaskAlarmM);

                dateM.setText(task.month);
                dateD.setText(task.day);
                title.setText(task.title);
                h.setText(task.timeH);
                m.setText(task.timeM);
                text.setText(task.text);
                alarmH.setText(task.alarmH);
                alarmM.setText(task.alarmM);


                Button save = mView.findViewById(R.id.addTaskSave);

                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(2);
                dateD.setFilters(filters);
                dateM.setFilters(filters);
                h.setFilters(filters);
                m.setFilters(filters);
                alarmH.setFilters(filters);
                alarmM.setFilters(filters);


                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MyTask[] taskArr = new MyTask[]{new MyTask(
                                String.valueOf(dateM.getText()),
                                String.valueOf(dateD.getText()),
                                String.valueOf(title.getText()),
                                String.valueOf(text.getText()),
                                String.valueOf(h.getText()),
                                String.valueOf(m.getText()))};
                        taskArr[0].setAlarmTime(
                                String.valueOf(alarmH.getText()),
                                String.valueOf(alarmM.getText()));


                        if(!taskArr[0].alarmH.equals("") && !taskArr[0].alarmM.equals("") ) {
                            if (Integer.parseInt(taskArr[0].alarmH) > 23 ||
                                    Integer.parseInt(taskArr[0].alarmH) < 0)
                                taskArr[0].alarmH = task.alarmH;
                            if (Integer.parseInt(taskArr[0].alarmM) > 59 ||
                                    Integer.parseInt(taskArr[0].alarmM) < 0)
                                taskArr[0].alarmM = task.alarmM;
                        }else {
                            taskArr[0].alarmH = task.alarmH;
                            taskArr[0].alarmM = task.alarmM;
                        }
                        if(taskArr[0].timeH.equals("") || Integer.parseInt(taskArr[0].timeH)>23 ||
                                Integer.parseInt(taskArr[0].timeH)<0)
                            taskArr[0].timeH = task.timeH;
                        if(taskArr[0].timeM.equals("") || Integer.parseInt(taskArr[0].timeM)>59 ||
                                Integer.parseInt(taskArr[0].timeM)<0)
                            taskArr[0].timeM = task.timeM;

                        if(taskArr[0].day.equals("")|| Integer.parseInt(taskArr[0].day)>30 ||
                                Integer.parseInt(taskArr[0].day)<1)
                            taskArr[0].day = task.day;
                        if( taskArr[0].month.equals("") || Integer.parseInt( taskArr[0].month)>12 ||
                                Integer.parseInt( taskArr[0].month)<1)
                            taskArr[0].month = task.month;

                        if(taskArr[0].title.equals(""))
                            taskArr[0].title = task.title;

                            db.updateTask(taskArr[0],task.id);
                            refreshFrag();

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


                final EditText dateM = mView.findViewById(R.id.addTaskM);
                final EditText dateD = mView.findViewById(R.id.addTaskD);
                final EditText title = mView.findViewById(R.id.addTaskTitle);
                final EditText text = mView.findViewById(R.id.addTaskText);
                final EditText h = mView.findViewById(R.id.addTaskTimeH);
                final EditText m = mView.findViewById(R.id.addTaskTimeM);
                final EditText alarmH = mView.findViewById(R.id.addTaskAlarmH);
                final EditText alarmM = mView.findViewById(R.id.addTaskAlarmM);

                dateD.setHint(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                dateM.setHint(String.valueOf(calendar.get(Calendar.MONTH)));
                h.setHint(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
                m.setHint(String.valueOf(calendar.get(Calendar.MINUTE)));

                Button save = mView.findViewById(R.id.addTaskSave);

                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(2);
                dateD.setFilters(filters);
                dateM.setFilters(filters);
                h.setFilters(filters);
                m.setFilters(filters);
                alarmH.setFilters(filters);
                alarmM.setFilters(filters);


                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyTask[] task = new MyTask[]{new MyTask(
                                String.valueOf(dateM.getText()),
                                String.valueOf(dateD.getText()),
                                String.valueOf(title.getText()),
                                String.valueOf(text.getText()),
                                String.valueOf(h.getText()),
                                String.valueOf(m.getText()))};
                        task[0].setAlarmTime(
                                String.valueOf(alarmH.getText()),
                                String.valueOf(alarmM.getText()));

                        if(!task[0].alarmH.equals("") && !task[0].alarmM.equals("") ) {
                            if (Integer.parseInt(task[0].alarmH) > 23 ||
                                    Integer.parseInt(task[0].alarmH) < 0)
                                task[0].alarmH = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                            if (Integer.parseInt(task[0].alarmH) > 59 ||
                                    Integer.parseInt(task[0].alarmH) < 0)
                                task[0].alarmM = String.valueOf(calendar.get(Calendar.MINUTE));


                            // set alarm
                            Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                            i.putExtra(AlarmClock.EXTRA_HOUR, task[0].alarmH);
                            i.putExtra(AlarmClock.EXTRA_MINUTES, task[0].alarmM);
                            i.putExtra(AlarmClock.EXTRA_SKIP_UI,true);


                            startActivity(i);
                        }else {
                            task[0].alarmH ="";
                            task[0].alarmM ="";
                        }
                        if(task[0].timeH.equals("") || Integer.parseInt(task[0].timeH)>23 ||
                                Integer.parseInt(task[0].timeH)<0)
                            task[0].timeH = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                        if(task[0].timeM.equals("") || Integer.parseInt(task[0].timeM)>59 ||
                                Integer.parseInt(task[0].timeM)<0)
                            task[0].timeM = String.valueOf(calendar.get(Calendar.MINUTE));

                        if(task[0].month.equals("") || Integer.parseInt(task[0].month)>12 ||
                                Integer.parseInt(task[0].month)<1)
                            task[0].month = String.valueOf(calendar.get(Calendar.MONTH));
                        if(task[0].day.equals("")|| Integer.parseInt(task[0].day)>30 ||
                                Integer.parseInt(task[0].day)<1)
                            task[0].day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

                        if(task[0].title.equals(""))
                            task[0].title = "title";


                        db.addTask(task[0]);
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
        nextDaysFrag.onclick = setOnclickL(nextDaysFrag);
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

        day = calendar.get(Calendar.DAY_OF_WEEK);
        day = 1;


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
