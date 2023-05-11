package com.btl.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.btl.model.Student;

import java.util.ArrayList;
import java.util.List;

public class SQLiteStudent extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student_db.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "student";

    public SQLiteStudent(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable = "CREATE TABLE " + TABLE_PRODUCT + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "date TEXT NOT NULL, " +
                "gender TEXT NOT NULL, " +
                "gpa FLOAT NOT NULL " +
                ")";

        db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }

    public List<Student> getAllStudent() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        Cursor rs = statement.query("student", null,
                null, null,
                null, null, null);
        while ((rs != null) && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String date = rs.getString(2);
            String gender = rs.getString(3);
            float gpa = rs.getFloat(4);
            students.add(new Student(id, name, date, gender, gpa));
        }
        return students;
    }

    public List<Student> getStudentByName(String NAME) {
        String whereClause = "name like ?";
        String[] whereArgs = {"%" + NAME + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("student", null, whereClause, whereArgs, null, null, null);
        List<Student> students = new ArrayList<>();
        while (rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String date = rs.getString(2);
            String gender = rs.getString(3);
            float gpa = rs.getFloat(4);
            students.add(new Student(id, name, date, gender, gpa));
        }
        return students;
    }

    public long insertStudent(Student student) {
        ContentValues c = new ContentValues();
        c.put("name", student.getName());
        c.put("date", student.getDate());
        c.put("gender", student.getGender());
        c.put("gpa", student.getGPA());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("student", null, c);
    }

    public long updateStudent(Student student) {
        ContentValues c = new ContentValues();
        c.put("name", student.getName());
        c.put("date", student.getDate());
        c.put("gender", student.getGender());
        c.put("gpa", student.getGPA());
        SQLiteDatabase st = getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(student.getId())};
        return st.update("student", c, whereClause, whereArgs);
    }

    public long deleteStudent(int ID) {
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(ID)};
        SQLiteDatabase st = getWritableDatabase();
        return st.delete("student", whereClause, whereArgs);
    }
}