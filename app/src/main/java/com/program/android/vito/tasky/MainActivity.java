package com.program.android.vito.tasky;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
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
    String currentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.tasky.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void refreshFrag(){
        frag.onStart();
    }

    public Onclick taskOnClick(final MyTask task){
        Onclick onclick = new Onclick() {
            @Override
            public void onClickL() {
                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.task_view_dialog, null);

                final EditText title = mView.findViewById(R.id.addTaskTitle);
                final EditText text = mView.findViewById(R.id.addTaskText);
                final EditText h = mView.findViewById(R.id.addTaskTimeH);
                final EditText m = mView.findViewById(R.id.addTaskTimeM);


                title.setText(task.title);
                h.setText(task.timeH);
                m.setText(task.timeM);
                text.setText(task.text);


                Button save = mView.findViewById(R.id.addTaskSave);
                final ImageButton addPic = mView.findViewById(R.id.add_pic);
                final ImageButton showPic = mView.findViewById(R.id.show_pic);

                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(2);

                h.setFilters(filters);
                m.setFilters(filters);



                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                if(task.imagePath == null){
                    addPic.setVisibility(View.VISIBLE);
                    showPic.setVisibility(View.GONE);
                }else {
                    addPic.setVisibility(View.GONE);
                    showPic.setVisibility(View.VISIBLE);
                }

                addPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPic.setVisibility(View.GONE);
                        showPic.setVisibility(View.VISIBLE);
                        dispatchTakePictureIntent();
                        galleryAddPic();
                        task.imagePath = currentPhotoPath;
                        db.updateTask(task,task.id);
                    }
                });

                showPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(task.imagePath), "image/*");
                        try {
                            startActivity(intent);
                        }catch (Exception e){

                        }
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyTask[] taskArr = new MyTask[]{new MyTask(
                                String.valueOf(calendar.get(Calendar.MONTH)),
                                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                                String.valueOf(title.getText()),
                                String.valueOf(text.getText()),
                                String.valueOf(h.getText()),
                                String.valueOf(m.getText()))};

                        if(taskArr[0].timeH.equals("") || Integer.parseInt(taskArr[0].timeH)>23 ||
                                Integer.parseInt(taskArr[0].timeH)<0)
                            taskArr[0].timeH = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                        if(taskArr[0].timeM.equals("") || Integer.parseInt(taskArr[0].timeM)>59 ||
                                Integer.parseInt(taskArr[0].timeM)<0)
                            taskArr[0].timeM = String.valueOf(calendar.get(Calendar.MINUTE));

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



                final EditText title = mView.findViewById(R.id.addTaskTitle);
                final EditText text = mView.findViewById(R.id.addTaskText);
                final EditText h = mView.findViewById(R.id.addTaskTimeH);
                final EditText m = mView.findViewById(R.id.addTaskTimeM);


                h.setHint(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
                m.setHint(String.valueOf(calendar.get(Calendar.MINUTE)));

                Button save = mView.findViewById(R.id.addTaskSave);
                final ImageButton addPic = mView.findViewById(R.id.add_pic);
                final ImageButton showPic = mView.findViewById(R.id.show_pic);

                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(2);

                h.setFilters(filters);
                m.setFilters(filters);



                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                addPic.setVisibility(View.VISIBLE);
                showPic.setVisibility(View.GONE);

                addPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPic.setVisibility(View.GONE);
                        showPic.setVisibility(View.VISIBLE);
                        dispatchTakePictureIntent();
                        galleryAddPic();
                    }
                });

                showPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(currentPhotoPath), "image/*");
                        try {
                            startActivity(intent);
                        }catch (Exception e){

                        }
                        Log.d("image URI\t", currentPhotoPath);
                    }
                });


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyTask[] task = new MyTask[]{new MyTask(
                                String.valueOf(calendar.get(Calendar.MONTH)),
                                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)+
                                        Integer.parseInt(clicked.getTag().toString())-day),
                                String.valueOf(title.getText()),
                                String.valueOf(text.getText()),
                                String.valueOf(h.getText()),
                                String.valueOf(m.getText()))};

                        task[0].setImagePath(currentPhotoPath);

                        if(task[0].timeH.equals("") || Integer.parseInt(task[0].timeH)>23 ||
                                Integer.parseInt(task[0].timeH)<0)
                            task[0].timeH = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                        if(task[0].timeM.equals("") || Integer.parseInt(task[0].timeM)>59 ||
                                Integer.parseInt(task[0].timeM)<0)
                            task[0].timeM = String.valueOf(calendar.get(Calendar.MINUTE));

                        if(task[0].title.equals(""))
                            task[0].title = "title";

                        task[0].alarm = 0;

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
        if (day == 7)
            day = 0;


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
