package com.example.mentorapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Game;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.Tags.Tag;
import com.example.mentorapp.Template;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MentorDB";

    private static final String Official_TABLE_NAME = "Official";
    private static final String Official_KEY_ID = "id";
    private static final String Official_KEY_NAME = "name";
    private static final String Official_KEY_DOB_YEAR = "dob_year";
    private static final String Official_KEY_DOB_MONTH = "dob_month";
    private static final String Official_KEY_START_MONTH = "start_date";
    private static final String Official_KEY_START_YEAR = "start_year";
    private static final String Official_KEY_OFFICIAL = "official";
    private static final String[] Official_COLUMNS = { Official_KEY_ID, Official_KEY_NAME,
            Official_KEY_DOB_YEAR, Official_KEY_DOB_MONTH, Official_KEY_START_MONTH,
            Official_KEY_START_YEAR, Official_KEY_OFFICIAL};

    private static final String Template_TABLE_NAME = "Template";
    private static final String Template_KEY_ID = "id";
    private static final String Template_KEY_NAME = "name";
    private static final String Template_KEY_TEMPLATE = "template";
    private static final String[] Template_COLUMNS = { Template_KEY_ID, Template_KEY_NAME,
            Template_KEY_TEMPLATE};

    private static final String Evaluation_TABLE_NAME = "Evaluation";
    private static final String Evaluation_KEY_ID = "id";
    private static final String Evaluation_KEY_OFFICIAL = "official_id";
    private static final String Evaluation_KEY_EVALUATION = "evaluation";
    private static final String[] Evaluation_COLUMNS = { Evaluation_KEY_ID, Evaluation_KEY_OFFICIAL, Evaluation_KEY_EVALUATION};

    private static final String Game_TABLE_NAME = "Game";
    private static final String Game_KEY_ID = "id";
    private static final String Game_KEY_GAME = "game";
    private static final String[] Game_COLUMNS = { Game_KEY_ID, Game_KEY_GAME};

    private static final String Tag_TABLE_NAME = "Tag";
    private static final String Tag_KEY_ID = "id";
    private static final String Tag_KEY_NAME = "name";
    private static final String Tag_KEY_TAG = "tag";
    private static final String[] Tag_COLUMNS = { Tag_KEY_ID, Tag_KEY_NAME, Tag_KEY_TAG};

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Official_CREATION_TABLE = "CREATE TABLE Official (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "name TEXT, " + "dob_year Integer, " + "dob_month Integer, "+
                "start_date Integer, " + "start_year Integer, " +
                "official Text )";
        String Template_CREATION_TABLE = "CREATE TABLE Template (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, " + "template TEXT )";
        String Evaluation_CREATION_TABLE = "CREATE TABLE Evaluation (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "official_id INTEGER, " + "evaluation TEXT )";
        String Game_CREATION_TABLE = "CREATE TABLE Game (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "game TEXT )";
        String Tag_CREATION_TABLE = "CREATE TABLE Tag (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name Text, " + "tag TEXT )";

        db.execSQL(Official_CREATION_TABLE);
        db.execSQL(Template_CREATION_TABLE);
        db.execSQL(Evaluation_CREATION_TABLE);
        db.execSQL(Game_CREATION_TABLE);
        db.execSQL(Tag_CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + Official_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Template_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Evaluation_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Game_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Tag_TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOfficial(Official official) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Official_TABLE_NAME, "id = ?", new String[] { String.valueOf(official.getId()) });
        db.close();
    }

    public Official getOfficial(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Official_TABLE_NAME, // a. table
                Official_COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (cursor.getCount() != 1){
            return null;
        }

        Integer id_value = Integer.parseInt(cursor.getString(0));
        String name = cursor.getString(1);
        Integer dob_year = cursor.getString(2) == null ? null : Integer.parseInt(cursor.getString(2));
        Integer dob_month = cursor.getString(3) == null ? null : Integer.parseInt(cursor.getString(3));
        Integer start_year = cursor.getString(4) == null ? null : Integer.parseInt(cursor.getString(4));
        Integer start_month = cursor.getString(5) == null ? null : Integer.parseInt(cursor.getString(5));
        String json = cursor.getString(6);

        Gson gson = new Gson();
        Official official = gson.fromJson(json, Official.class);
        official.setId(id_value);

        return official;
    }

    public List<Official> allOfficials() {

        List<Official> officials = new LinkedList<Official>();
        String query = "SELECT  * FROM " + Official_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Official official = null;
        Gson gson = new Gson();

        if (cursor.moveToFirst()) {
            do {
                Integer id_value = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                Integer dob_year = cursor.getString(2) == null ? null : Integer.parseInt(cursor.getString(2));
                Integer dob_month = cursor.getString(3) == null ? null : Integer.parseInt(cursor.getString(3));
                Integer start_year = cursor.getString(4) == null ? null : Integer.parseInt(cursor.getString(4));
                Integer start_month = cursor.getString(5) == null ? null : Integer.parseInt(cursor.getString(5));
                String json = cursor.getString(6);

                official = gson.fromJson(json, Official.class);
                official.setId(id_value);

                officials.add(official);
            } while (cursor.moveToNext());
        }

        return officials;
    }

    public Long addOfficial(Official official) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Official_KEY_NAME, official.getName());
        values.put(Official_KEY_DOB_YEAR, official.getDob().getYear());
        values.put(Official_KEY_DOB_MONTH, official.getDob().getMonth());
        values.put(Official_KEY_START_YEAR, official.getStartedOfficiating().getYear());
        values.put(Official_KEY_START_MONTH, official.getStartedOfficiating().getMonth());
        values.put(Official_KEY_OFFICIAL, official.toJson());
        // insert
        long id = db.insert(Official_TABLE_NAME,null, values);
        db.close();
        return id;
    }

    public int updateOfficial(Official official) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Official_KEY_NAME, official.getName());
        values.put(Official_KEY_DOB_YEAR, official.getDob().getYear());
        values.put(Official_KEY_DOB_MONTH, official.getDob().getMonth());
        values.put(Official_KEY_START_YEAR, official.getStartedOfficiating().getYear());
        values.put(Official_KEY_START_MONTH, official.getStartedOfficiating().getMonth());
        values.put(Official_KEY_OFFICIAL, official.toJson());

        int i = db.update(Official_TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(official.getId()) });

        db.close();

        return i;
    }

    public void deleteTemplate(Template template) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Template_TABLE_NAME, "id = ?", new String[] { String.valueOf(template.getId()) });
        db.close();
    }

    public Template getTemplate(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Template_TABLE_NAME, // a. table
                Template_COLUMNS, // b. column names
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
        String query = "SELECT  * FROM " + Template_TABLE_NAME;
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
        values.put(Template_KEY_NAME, template.getName());
        values.put(Template_KEY_TEMPLATE, template.toJson());
        // insert
        db.insert(Template_TABLE_NAME,null, values);
        db.close();
    }

    public int updateTemplate(Template template) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Template_KEY_NAME, template.getName());
        values.put(Template_KEY_TEMPLATE, template.toJson());

        int i = db.update(Template_TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(template.getId()) });

        db.close();

        return i;
    }


    public void deleteEvaluation(Evaluation evaluation) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Evaluation_TABLE_NAME, "id = ?", new String[] { String.valueOf(evaluation.getId()) });
        db.close();
    }

    public Evaluation getEvaluation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Evaluation_TABLE_NAME, // a. table
                Evaluation_COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Integer id_value = Integer.parseInt(cursor.getString(0));
        String officialString = cursor.getString(1);
        Integer officialID;
        if (officialString == null){
            officialID = null;
        } else {
            officialID = Integer.parseInt(officialString);
        }
        String json = cursor.getString(2);


        Gson gson = new Gson();
        Evaluation evaluation = gson.fromJson(json, Evaluation.class);
        evaluation.setId(id_value);

        return evaluation;
    }

    public List<Evaluation> allEvaluations() {

        List<Evaluation> evaluations = new LinkedList<Evaluation>();
        String query = "SELECT  * FROM " + Evaluation_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Evaluation evaluation = null;
        Gson gson = new Gson();

        if (cursor.moveToFirst()) {
            do {
                Integer id_value = Integer.parseInt(cursor.getString(0));
                Integer officialID = Integer.parseInt(cursor.getString(1));
                String json = cursor.getString(2);

                evaluation = gson.fromJson(json, Evaluation.class);
                evaluation.setId(id_value);

                evaluations.add(evaluation);

            } while (cursor.moveToNext());
        }

        return evaluations;
    }

    public Long addEvaluation(Evaluation evaluation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (evaluation.getId() != null){
            System.out.println("\n\nYOU ARE ADDING AN OFFICIAL WITH AN ID\n\n");
        }


        values.put(Evaluation_KEY_OFFICIAL, evaluation.getOfficialId());
        values.put(Evaluation_KEY_EVALUATION, evaluation.toJson());

        System.out.println(evaluation.toJson());

        Long id = db.insert(Evaluation_TABLE_NAME,null, values);
        db.close();
        return id;
    }

    public int updateEvaluation(Evaluation evaluation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Evaluation_KEY_OFFICIAL, evaluation.getOfficialId());
        values.put(Evaluation_KEY_EVALUATION, evaluation.toJson());

        int i = db.update(Evaluation_TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(evaluation.getId()) });

        db.close();

        System.out.println(evaluation.toJson());

        return i;
    }

    public void deleteGame(Game game) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Game_TABLE_NAME, "id = ?", new String[] { String.valueOf(game.getId()) });
        db.close();
    }

    public Game getGame(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Game_TABLE_NAME, // a. table
                Game_COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Integer id_value = Integer.parseInt(cursor.getString(0));
        String json = cursor.getString(1);

        Gson gson = new Gson();
        Game game = gson.fromJson(json, Game.class);
        game.setId(id_value);

        return game;
    }

    public List<Game> allGames() {

        List<Game> games = new LinkedList<Game>();
        String query = "SELECT  * FROM " + Game_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Game game = null;
        Gson gson = new Gson();

        if (cursor.moveToFirst()) {
            do {
                Integer id_value = Integer.parseInt(cursor.getString(0));
                String json = cursor.getString(1);

                System.out.println(json);

                game = gson.fromJson(json, Game.class);
                game.setId(id_value);

                games.add(game);

            } while (cursor.moveToNext());
        }

        return games;
    }

    public Long addGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Game_KEY_GAME, game.toJson());

        Long id = db.insert(Game_TABLE_NAME,null, values);
        db.close();
        return id;
    }

    public int updateGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Game_KEY_GAME, game.toJson());

        int i = db.update(Game_TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(game.getId()) });

        db.close();

        return i;
    }

    public void deleteTag(Tag tag) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Game_TABLE_NAME, "id = ?", new String[] { String.valueOf(tag.getId()) });
        db.close();
    }

    public Tag getTag(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Tag_TABLE_NAME, // a. table
                Tag_COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Integer id_value = Integer.parseInt(cursor.getString(0));
        String json = cursor.getString(1);

        Gson gson = new Gson();
        Tag tag = gson.fromJson(json, Tag.class);
        tag.setId(id_value);

        return tag;
    }

    public List<Tag> allTags() {

        List<Tag> tags = new LinkedList<>();
        String query = "SELECT  * FROM " + Tag_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Tag tag = null;
        Gson gson = new Gson();

        if (cursor.moveToFirst()) {
            do {
                Integer id_value = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String json = cursor.getString(2);

                tag = gson.fromJson(json, Tag.class);
                tag.setId(id_value);

                tags.add(tag);

            } while (cursor.moveToNext());
        }

        return tags;
    }

    public Long addTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Tag_KEY_NAME, tag.getName());
        values.put(Tag_KEY_TAG, tag.toJson());

        Long id = db.insert(Tag_TABLE_NAME,null, values);
        db.close();
        return id;
    }

    public int updateTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Tag_KEY_TAG, tag.toJson());

        int i = db.update(Tag_TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(tag.getId()) });

        db.close();

        return i;
    }
}
