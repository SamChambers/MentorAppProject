package com.example.mentorapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mentorapp.Official;
import com.example.mentorapp.Template;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

public class OfficialDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MentorDB";
    private static final String TABLE_NAME = "Official";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DOB_YEAR = "dob_year";
    private static final String KEY_DOB_MONTH = "dob_month";
    private static final String KEY_START_MONTH = "start_date";
    private static final String KEY_START_YEAR = "start_year";
    private static final String KEY_OFFICIAL = "official";
    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_DOB_YEAR, KEY_DOB_MONTH, KEY_START_MONTH, KEY_START_YEAR, KEY_OFFICIAL};

    public OfficialDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Template (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "name TEXT, " + "dob_year Integer, " + "dob_month Integer, "+
                "start_date Integer, " + "start_year Integer, " +
                "official Text )";
        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Official official) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(official.getId()) });
        db.close();
    }

    public Official getOfficial(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Integer id_value = Integer.parseInt(cursor.getString(0));
        String name = cursor.getString(1);
        Integer dob_year = Integer.parseInt(cursor.getString(2));
        Integer dob_month = Integer.parseInt(cursor.getString(3));
        Integer start_year = Integer.parseInt(cursor.getString(4));
        Integer start_month = Integer.parseInt(cursor.getString(5));
        String json = cursor.getString(6);

        Gson gson = new Gson();
        Official official = gson.fromJson(json, Official.class);
        official.setId(id_value);

        return official;
    }

    public List<Official> allOfficials() {

        List<Official> officials = new LinkedList<Official>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Official official = null;
        Gson gson = new Gson();

        if (cursor.moveToFirst()) {
            do {
                Integer id_value = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                Integer dob_year = Integer.parseInt(cursor.getString(2));
                Integer dob_month = Integer.parseInt(cursor.getString(3));
                Integer start_year = Integer.parseInt(cursor.getString(4));
                Integer start_month = Integer.parseInt(cursor.getString(5));
                String json = cursor.getString(6);

                official = gson.fromJson(json, Official.class);
                official.setId(id_value);

                officials.add(official);
            } while (cursor.moveToNext());
        }

        return officials;
    }

    public void addOfficial(Official official) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, official.getName());
        values.put(KEY_DOB_YEAR, official.getDob().getYear());
        values.put(KEY_DOB_MONTH, official.getDob().getMonth());
        values.put(KEY_START_YEAR, official.getStartedOfficiating().getYear());
        values.put(KEY_START_MONTH, official.getStartedOfficiating().getMonth());
        values.put(KEY_OFFICIAL, official.toJson());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updateOfficial(Official official) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, official.getName());
        values.put(KEY_DOB_YEAR, official.getDob().getYear());
        values.put(KEY_DOB_MONTH, official.getDob().getMonth());
        values.put(KEY_START_YEAR, official.getStartedOfficiating().getYear());
        values.put(KEY_START_MONTH, official.getStartedOfficiating().getMonth());
        values.put(KEY_OFFICIAL, official.toJson());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(official.getId()) });

        db.close();

        return i;
    }

}
