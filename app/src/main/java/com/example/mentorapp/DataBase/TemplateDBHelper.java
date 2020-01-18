package com.example.mentorapp.DataBase;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import com.example.mentorapp.Template;

public class TemplateDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MentorDB";
    private static final String TABLE_NAME = "Template";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TEMPLATE = "template";
    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_TEMPLATE};

    public TemplateDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Template (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, " + "template TEXT )";
        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Template template) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(template.getId()) });
        db.close();
    }

    public Template getTemplate(int id) {
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
        String json = cursor.getString(2);

        Gson gson = new Gson();
        Template template = gson.fromJson(json, Template.class);
        template.setId(id_value);

        return template;
    }

    public List<Template> allTemplates() {

        List<Template> templates = new LinkedList<Template>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Template template = null;
        Gson gson = new Gson();

        if (cursor.moveToFirst()) {
            do {
                Integer id_value = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String json = cursor.getString(2);


                template = gson.fromJson(json, Template.class);
                template.setId(id_value);
                templates.add(template);
                System.out.println(template.getId());
            } while (cursor.moveToNext());
        }

        return templates;
    }

    public void addTemplate(Template template) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, template.getName());
        values.put(KEY_TEMPLATE, template.toJson());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updateTemplate(Template template) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, template.getName());
        values.put(KEY_TEMPLATE, template.toJson());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(template.getId()) });

        db.close();

        return i;
    }

}
