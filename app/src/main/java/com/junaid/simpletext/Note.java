package com.junaid.simpletext;

/**
 * Created by Junaid on 14-12-2016.
 */

public class Note {

    private String mTitle, mMessage;
    private long mID, mDateInMilli;

    /*Constructors*/

    public Note(String title, String message){

        mTitle = title;
        mMessage = message;
        mID = 0;
        mDateInMilli = 0;

    }

    public Note(String title, String message, long id, long date){

        mTitle = title;
        mMessage = message;
        mID = id;
        mDateInMilli = date;
    }

   /*Get Methods*/

    public long getDateInMilli() { return mDateInMilli;  }
    public long getID() { return mID; }
    public String getMessage() { return mMessage; }
    public String getTitle() { return mTitle; }

    /*Set Methods*/

    public void setDateInMilli(long dateInMilli) { mDateInMilli = dateInMilli; }
    public void setID(long ID) { mID = ID; }
    public void setMessage(String message) { mMessage = message; }
    public void setTitle(String title) { mTitle = title; }
}

