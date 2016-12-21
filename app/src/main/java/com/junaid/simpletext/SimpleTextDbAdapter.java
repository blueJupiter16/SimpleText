package com.junaid.simpletext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Junaid on 16-12-2016.
 */

public class SimpleTextDbAdapter {

    private static final String DATABASE_NAME = "simpleText.db";
    private static final int VERSION = 1 ;

    public static final String NOTE_TABLE = "note_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "_title";
    public static final String COLUMN_NOTE = "_note";
    public static final String COLUMN_DATE = "_date";

    private String[] columns = { COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE, COLUMN_DATE};

    public static final String DATABASE_CREATE = "CREATE TABLE " +  NOTE_TABLE + " ( "
            + COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_NOTE + " text not null, "
            + COLUMN_DATE+ " );";

    private SQLiteDatabase sqlDB;
    private Context mContext;

    private SimpleTextDBHelper helper;

    public SimpleTextDbAdapter(Context context){
        mContext = context;

    }

    public SimpleTextDbAdapter open() throws SQLException{
        helper = new SimpleTextDBHelper(mContext);
        sqlDB = helper.getWritableDatabase();
        return this;
    }

    public void close(){
        helper.close();
    }

    public Note insertNote(String title, String message){

        ContentValues mValues = new ContentValues();

        mValues.put(COLUMN_TITLE,title);
        mValues.put(COLUMN_NOTE,message);
        mValues.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis() + "");

        Long insertID = sqlDB.insert(NOTE_TABLE,null,mValues);

        Cursor mCursor =  sqlDB.query(NOTE_TABLE,columns,COLUMN_ID + " = " + insertID,null,null,null,null);

        mCursor.moveToFirst();
        Note note = cursorToNote(mCursor);
        mCursor.close();

        return note;
    }

    public long updateNote(long ID, String newTitle, String newMesage){

        ContentValues mValues = new ContentValues();

        mValues.put(COLUMN_TITLE,newTitle);
        mValues.put(COLUMN_NOTE,newMesage);
        mValues.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis() + "");

        return sqlDB.update(NOTE_TABLE,mValues,COLUMN_ID + " = " + ID,null);

    }

    public long deleteNote(long ID){
        return sqlDB.delete(NOTE_TABLE, COLUMN_ID + " = " + ID, null);
    }

    public ArrayList<Note> getNotes(){

        ArrayList<Note> mNotes = new ArrayList<Note>();

        Cursor cursor = sqlDB.query(NOTE_TABLE,columns,null,null,null,null,null);

        for(cursor.moveToLast(); !cursor.isBeforeFirst();cursor.moveToPrevious()){
            Note note = cursorToNote(cursor);
            mNotes.add(note);
        }

        cursor.close();
        return mNotes;
    }

    private Note cursorToNote(Cursor cursor){

        return (new Note(cursor.getString(1),cursor.getString(2),cursor.getInt(0),cursor.getLong(3)));
    }

    private static class SimpleTextDBHelper extends SQLiteOpenHelper{

        public SimpleTextDBHelper(Context context){
            super(context, DATABASE_NAME, null, VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
            onCreate(db);
        }
    }



}
