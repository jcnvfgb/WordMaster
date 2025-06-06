package com.example.maing.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.maing.Domain.DataSetPacket;
import com.example.maing.Domain.WordModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    private static final String TABLE_LANGUAGES = "languages";
    private static final String TABLE_SETS = "sets";
    private static final String TABLE_WORDS = "words";

    // Languages Table Columns
    private static final String COLUMN_LANGUAGE_ID = "id_language";
    private static final String COLUMN_LANGUAGE_NAME = "language";

    // Sets Table Columns
    private static final String COLUMN_SET_ID = "id_set";
    private static final String COLUMN_SET_NAME = "setName";
    private static final String COLUMN_SET_LANGUAGE_ID = "id_language";

    // Words Table Columns
    private static final String COLUMN_WORD_ID = "id_word";
    private static final String COLUMN_WORD_NAME = "word";
    private static final String COLUMN_WORD_TRANSLATION = "translation";
    private static final String COLUMN_WORD_SET_ID = "id_set";
    private static final String COLUMN_WORD_ACTIVITY = "wordActivity";

    // Statistics Table Columns
    private static final String TABLE_STATS = "statistics";
    private static final String COLUMN_SESSION_ID = "id_session";
    private static final String COLUMN_CORRECT = "correct";
    private static final String COLUMN_INCORRECT = "incorrect";
    private static final String COLUMN_SKIPPED = "skipped";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        // Включаем поддержку внешних ключей для всех подключений
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");
        // Создание таблицы языков
        String CREATE_LANGUAGES_TABLE =
                "CREATE TABLE " + TABLE_LANGUAGES + "("
                        + COLUMN_LANGUAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_LANGUAGE_NAME + " TEXT NOT NULL UNIQUE"
                        + ");";

        db.execSQL(CREATE_LANGUAGES_TABLE);

        // Создание таблицы наборов
        String CREATE_SETS_TABLE =
                "CREATE TABLE " + TABLE_SETS + "("
                        + COLUMN_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_SET_NAME + " TEXT NOT NULL, "
                        + COLUMN_SET_LANGUAGE_ID + " INTEGER NOT NULL, "
                        + "FOREIGN KEY (" + COLUMN_SET_LANGUAGE_ID + ") "
                        + "REFERENCES " + TABLE_LANGUAGES + "(" + COLUMN_LANGUAGE_ID + ") "
                        + "ON DELETE CASCADE"
                        + ");";

        db.execSQL(CREATE_SETS_TABLE);

        // Создание таблицы слов
        String CREATE_WORDS_TABLE =
                "CREATE TABLE " + TABLE_WORDS + "("
                        + COLUMN_WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_WORD_NAME + " TEXT NOT NULL, "
                        + COLUMN_WORD_TRANSLATION + " TEXT NOT NULL, "
                        + COLUMN_WORD_SET_ID + " INTEGER NOT NULL, "
                        + COLUMN_WORD_ACTIVITY + " TEXT "
                        + "CHECK(" + COLUMN_WORD_ACTIVITY + " IN ('bad', 'middle', 'good', 'great')) "
                        + "DEFAULT 'bad', "
                        + "FOREIGN KEY (" + COLUMN_WORD_SET_ID + ") "
                        + "REFERENCES " + TABLE_SETS + "(" + COLUMN_SET_ID + ") "
                        + "ON DELETE CASCADE"
                        + ");";

        db.execSQL(CREATE_WORDS_TABLE);

        // Создание таблицы статистики
        String CREATE_STATS_TABLE =
                "CREATE TABLE " + TABLE_STATS + "("
                        + COLUMN_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_SET_NAME + " TEXT NOT NULL, "
                        + COLUMN_CORRECT + " INTEGER DEFAULT 0, "
                        + COLUMN_INCORRECT + " INTEGER DEFAULT 0, "
                        + COLUMN_SKIPPED + " INTEGER DEFAULT 0 "
                        + ");";

        db.execSQL(CREATE_STATS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        onCreate(db);
    }

    // Insert a new language
    public void insertLanguage(String language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LANGUAGE_NAME, language);
        db.insert(TABLE_LANGUAGES, null, values);
        db.close();
    }

    // Insert a new set
    public void insertSet(String setName, int languageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SET_NAME, setName);
        values.put(COLUMN_SET_LANGUAGE_ID, languageId);
        db.insert(TABLE_SETS, null, values);
        db.close();
    }

    // Insert a new word
    public void insertWord(String word, String translation, int setId, String wordActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORD_NAME, word);
        values.put(COLUMN_WORD_TRANSLATION, translation);
        values.put(COLUMN_WORD_SET_ID, setId);
        values.put(COLUMN_WORD_ACTIVITY, wordActivity);
        db.insert(TABLE_WORDS, null, values);
        db.close();
    }

    // Get all languages
    public Cursor getAllLanguages() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LANGUAGES, null);
    }

    // Get all sets
    public Cursor getAllSets() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SETS, null);
    }

    // Get all words
    public Cursor getAllWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_WORDS, null);
    }

    // Update a language
    public void updateLanguage(int id, String language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LANGUAGE_NAME, language);
        db.update(TABLE_LANGUAGES, values, COLUMN_LANGUAGE_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Update a set
    public void updateSet(int id, String setName, int languageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SET_NAME, setName);
        values.put(COLUMN_SET_LANGUAGE_ID, languageId);
        db.update(TABLE_SETS, values, COLUMN_SET_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Update a word
    public void updateWord(int id, String word, String translation, int setId, String wordActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORD_NAME, word);
        values.put(COLUMN_WORD_TRANSLATION, translation);
        values.put(COLUMN_WORD_SET_ID, setId);
        values.put(COLUMN_WORD_ACTIVITY, wordActivity);
        db.update(TABLE_WORDS, values, COLUMN_WORD_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Delete a language
    public void deleteLanguage(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_LANGUAGES, COLUMN_LANGUAGE_ID + " = ?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    // Delete a set
    public void deleteSet(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_SETS, COLUMN_SET_ID + " = ?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    // Delete a word
    public void deleteWord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_WORDS, COLUMN_WORD_ID + " = ?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void checkWordsAfterDeleteSet() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor wordsCursor = db.rawQuery("SELECT * FROM " + TABLE_WORDS, new String[]{});

        while (wordsCursor.moveToNext()) {
            Log.d("DatabaseHelper", "ID: " + wordsCursor.getInt(0));
            Log.d("DatabaseHelper", "Word: " + wordsCursor.getString(1));
        }

        wordsCursor.close();
        db.close();
    }

    public int getBadAndMiddleWordsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_WORDS
                + " WHERE " + COLUMN_WORD_ACTIVITY + " IN ('bad', 'middle')";

        Cursor cursor = db.rawQuery(query, null);
        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<WordModel> getBadAndMiddleWords() {
        ArrayList<WordModel> wordsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_WORDS
                + " WHERE " + COLUMN_WORD_ACTIVITY + " IN ('bad', 'middle')";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                WordModel word = new WordModel(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(4),
                        cursor.getInt(0),
                        cursor.getInt(3)
                );
                wordsList.add(word);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return wordsList;
    }

    public ArrayList<String> getAllTranslations() {
        ArrayList<String> translationsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_WORD_TRANSLATION + " FROM " + TABLE_WORDS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                translationsList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return translationsList;
    }

    public ArrayList<WordModel> getWordsBySetId(int setId) {
        ArrayList<WordModel> wordsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_WORDS
                + " WHERE " + COLUMN_WORD_SET_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(setId)});

        if (cursor.moveToFirst()) {
            do {
                WordModel word = new WordModel(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(4),
                        cursor.getInt(0),
                        cursor.getInt(3)
                );
                wordsList.add(word);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return wordsList;
    }

    public int getLanguageId(String languageName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int languageId = -1;
        Cursor cursor = null;

        try {
            cursor = db.query(
                    TABLE_LANGUAGES,
                    new String[]{COLUMN_LANGUAGE_ID},
                    COLUMN_LANGUAGE_NAME + " = ?",
                    new String[]{languageName},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                languageId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE_ID));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return languageId;
    }

    public long getOrCreateSetId(String setName, long languageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long setId = -1;
        Cursor cursor = null;

        try {
            // Проверяем существование набора
            cursor = db.query(
                    TABLE_SETS,
                    new String[]{COLUMN_SET_ID},
                    COLUMN_SET_NAME + " = ? AND " + COLUMN_SET_LANGUAGE_ID + " = ?",
                    new String[]{setName, String.valueOf(languageId)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                setId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SET_ID));
            } else {
                // Создаем новый набор
                ContentValues values = new ContentValues();
                values.put(COLUMN_SET_NAME, setName);
                values.put(COLUMN_SET_LANGUAGE_ID, languageId);
                setId = db.insert(TABLE_SETS, null, values);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return setId;
    }

    public boolean importData(List<DataSetPacket> parsedData) {
        int idLenguage = -1;
        int idSet = -1;
        for (DataSetPacket item : parsedData) {
            idLenguage = getLanguageId(item.getLanguageName());
            if (idLenguage == -1)
                return false;
            idSet = (int)getOrCreateSetId(item.getSetName(), idLenguage);

            //Log.d("DatabaseHelper", "idLenguage: " + idLenguage);
            //Log.d("DatabaseHelper", "idSet: " + idSet);

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_WORD_NAME, item.getWord());
            values.put(COLUMN_WORD_TRANSLATION, item.getTranslation());
            values.put(COLUMN_WORD_SET_ID, idSet);
            values.put(COLUMN_WORD_ACTIVITY, "bad");
            db.insert(TABLE_WORDS, null, values);
            db.close();
        }
        return true;
    }

    public long addStatsEntry(String setName, int correct, int incorrect, int skipped) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SET_NAME, setName);
        values.put(COLUMN_CORRECT, correct);
        values.put(COLUMN_INCORRECT, incorrect);
        values.put(COLUMN_SKIPPED, skipped);

        long newRowId = db.insert(TABLE_STATS, null, values);
        db.close();
        return newRowId; // Возвращает ID новой записи или -1 при ошибке
    }

    public Cursor getAllStats() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_STATS + " ORDER BY "
                + COLUMN_SESSION_ID + " DESC", null);
    }

    public int deleteStatsEntryStat(long sessionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(
                TABLE_STATS,
                COLUMN_SESSION_ID + " = ?",
                new String[]{String.valueOf(sessionId)}
        );
        db.close();
        return deletedRows;
    }

    public String getSetNameById(int setId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String setName = null;
        Cursor cursor = null;

        try {
            cursor = db.query(
                    TABLE_SETS,
                    new String[]{COLUMN_SET_NAME},
                    COLUMN_SET_ID + " = ?",
                    new String[]{String.valueOf(setId)},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                setName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SET_NAME));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return setName;
    }
}
