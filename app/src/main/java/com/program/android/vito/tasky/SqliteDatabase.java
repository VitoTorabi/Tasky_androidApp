package com.program.android.vito.tasky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "";
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_INDEX = "task_index", COLUMN_TITLE = "title",
            COLUMN_DONE = "is_done", COLUMN_TEXT = "text", COLUMN_TIME = "time",
            COLUMN_ALARM = "alarm", COLUMN_DATE = "date";

    public SqliteDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE    TABLE " + TABLE_TASKS
                + "(" + COLUMN_INDEX + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DONE + " INTEGER,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TITLE+ " TEXT,"
                + COLUMN_TEXT + " TEXT,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_ALARM + " TEXT"
                + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public Data listTasks(String date){ //TODO
        String sql = "select * from " + TABLE_TASKS + " where " + COLUMN_DATE +
                " = ?" + " group by " + COLUMN_DONE;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MyTask> todoTasks = new ArrayList<>();
        ArrayList<MyTask> doneTasks = new ArrayList<>();
        Data data = new Data(todoTasks, doneTasks);
        Cursor cursor = db.rawQuery(sql, new String[] { String.valueOf(date)});
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                int done = Integer.parseInt(cursor.getString(0));
                String day = cursor.getString(2);
                String title = cursor.getString(3);
                String text = cursor.getString(4);
                String deadline = cursor.getString(5);
                String alarmTime = cursor.getString(6);

                MyTask temp = new MyTask(id, day, title , text , deadline);
                if (alarmTime != null)
                    temp.setAlarmTime(alarmTime);

                if (done ==0)
                    data.todo.add(temp);
                else if (done == 1)
                    data.done.add(temp);

            }while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    public void updateTask(int id, MyTask task, int done){
        String query = "update " + TABLE_TASKS + " set "
                + COLUMN_DATE + " = ? , "
                + COLUMN_DONE + " = ? ,"
                + COLUMN_TITLE + " = ? ,"
                + COLUMN_TEXT + " = ? ,"
                + COLUMN_TIME + " = ? ,"
                + COLUMN_ALARM + " = ? "
                + " where " + COLUMN_INDEX + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {
                        task.date , String.valueOf(done),task.title,task.text,
                        task.deadline, task.alarmTime,String.valueOf(id)});

        cursor.close();
    }

    public void addTask(MyTask task){

        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE , task.date);
        values.put(COLUMN_ALARM , task.alarmTime);
        values.put(COLUMN_TEXT, task.text);
        values.put(COLUMN_TIME, task.deadline);
        values.put(COLUMN_TITLE , task.title);
        values.put(COLUMN_DONE , 0);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TASKS, null, values);
    }

//    public MyTask findTask(int i){ // TODO
//        String query = "Select * FROM "    + TABLE_TASKS + " WHERE " + COLUMN_INDEX + " = " + i;
//        SQLiteDatabase db = this.getWritableDatabase();
//        MyTask task = null;
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()){
//            int id = Integer.parseInt(cursor.getString(0));
//            String levelName = cursor.getString(1);
//            int productQuantity = Integer.parseInt(cursor.getString(2));
//            task = new MyTask(0, "", "", "", "");
//        }
//        cursor.close();
//        return task;
//    }

    public void deleteLevel(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_INDEX    + "    = ?", new String[] { String.valueOf(id)});
    }
}

class Data{
    public ArrayList<MyTask> todo;
    public ArrayList<MyTask> done;
    public Data(ArrayList<MyTask> todo, ArrayList<MyTask> done){
        this.todo = todo;
        this.done = done;
    }
}


