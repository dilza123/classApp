package com.example.classapp.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " (" +
                    UsersMaster.Users._ID + " INTEGER PRIMARY KEY," +
                    UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                    UsersMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UsersMaster.Users.TABLE_NAME;


    public void addInfo(String username, String password) {

        SQLiteDatabase db = getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, username);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        long newRowId = db.insert(UsersMaster.Users.TABLE_NAME, null, values);



    }
   public Boolean updateInfo (String username, String password) {
       SQLiteDatabase db = getWritableDatabase();



       ContentValues values = new ContentValues();
       values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, username);
       values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);


       String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
       String[] selectionArgs = {username};

       int count = db.update(
               UsersMaster.Users.TABLE_NAME,
               values,
               selection,
               selectionArgs);
    if (count >= 1)
    {
        return true;
    }
    else {
        return false;
    }

   }

   public void  deleteInfo (String username){

         SQLiteDatabase db = getWritableDatabase();

       String selection = UsersMaster.Users.COLUMN_NAME_USERNAME+ " LIKE ?";

       String[] selectionArgs = { username  };

       int deletedRows = db.delete(UsersMaster.Users.TABLE_NAME, selection, selectionArgs);

   }

   public List readAllInfo(String s){
        String username = "dil";
       SQLiteDatabase db = getReadableDatabase();


       String[] projection = {
               BaseColumns._ID,
               UsersMaster.Users.COLUMN_NAME_USERNAME,
               UsersMaster.Users.COLUMN_NAME_PASSWORD
       };


       String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " = ?";
       String[] selectionArgs = { username };


       String sortOrder =
               UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

       Cursor cursor = db.query(
               UsersMaster.Users.TABLE_NAME,
               projection,
               null,
               null,
               null,
               null,
               sortOrder
       );
       List userInfo = new ArrayList<>();
       while(cursor.moveToNext()) {
           String user = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
           String pass = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
           userInfo.add(user);
           userInfo.add(pass);



       }
       cursor.close();
       return userInfo;


   }
}