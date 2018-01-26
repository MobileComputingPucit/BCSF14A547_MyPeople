package com.example.uzair.my_people;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;



public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "people.db";
    private static final int DATABASE_VERSION = 2;

    public static final String PERSON_TABLE_NAME = "person";
    public static final String PERSON_COLUMN_ID = "_id";
    public static final String PERSON_COLUMN_NAME = "name";
    public static final String PERSON_COLUMN_GENDER = "gender";
    public static final String PERSON_COLUMN_PHONE = "phone";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + PERSON_TABLE_NAME +
                        "(" + PERSON_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        PERSON_COLUMN_NAME + " TEXT, " +
                        PERSON_COLUMN_GENDER + " TEXT, " +
                        PERSON_COLUMN_PHONE + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertPerson(Person p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PERSON_COLUMN_NAME, p.name);
        contentValues.put(PERSON_COLUMN_GENDER, p.gender);
        contentValues.put(PERSON_COLUMN_PHONE, p.phone);

        db.insert(PERSON_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_COLUMN_NAME, person.name);
        contentValues.put(PERSON_COLUMN_GENDER, person.gender);
        contentValues.put(PERSON_COLUMN_PHONE, person.phone);
        db.update(PERSON_TABLE_NAME, contentValues, PERSON_COLUMN_ID + " = ? ", new String[] { Integer.toString(person.id) } );
        return true;
    }

    public Integer deletePerson(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PERSON_TABLE_NAME,
                PERSON_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public List<Person> getAllPersons() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Person> people = new ArrayList<>();

        Cursor cursor =  db.rawQuery( "SELECT * FROM " + PERSON_TABLE_NAME, null );
        while (cursor.moveToNext()){
            Person p = new Person();
            p.id = cursor.getInt(cursor.getColumnIndex(PERSON_COLUMN_ID));
            p.name = cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_NAME));
            p.gender = cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_GENDER));
            p.phone = cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_PHONE));

            people.add(p);
        }
        return people;
    }
}