package com.example.sheetal.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sheetal.
 */
public class TodoDatabaseHelper extends SQLiteOpenHelper{

    private  static TodoDatabaseHelper sInstance;

    //Database info
    private static final String DATABASE_NAME = "TodoDatabase";
    private  static  final int  DATABASE_VERSION = 10;

    //Tables info
    private  static  final  String TABLE_LIST = "list";

    //LIST table Columns info
    private  static  final String KEY_LIST_ID = "id";
    private  static  final String KEY_LIST_TEXT = "text";
    private  static  final String KEY_LIST_POSITION = "position";
    private  static final String KEY_LIST_DUEDATE = "dueDate";


    //Singletone - make sure only one database instance exists across the application
    public static synchronized TodoDatabaseHelper getInstance(Context context)
    {
        if(sInstance == null) {
            sInstance = new TodoDatabaseHelper(context.getApplicationContext());
        }

        return sInstance;
    }


    private TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LIST_TABLE = "CREATE TABLE "+ TABLE_LIST +
                                    "(" +
                                        KEY_LIST_ID +" INTEGER PRIMARY KEY," +
                                        KEY_LIST_TEXT +" TEXT," +
                                        KEY_LIST_POSITION +" TEXT," +
                                        KEY_LIST_DUEDATE +" TEXT" +
                                    ")";

        db.execSQL(CREATE_LIST_TABLE);
    }

    // Called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion != newVersion) {
            String DROP_LIST_TABLE = "DROP TABLE IF EXISTS " + TABLE_LIST;
            onCreate(db);
        }
    }

    // add list item to database
    public void addListItem(ListItem listItem, int position)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try
        {
            ContentValues values = new ContentValues();
            values.put(KEY_LIST_TEXT, listItem.text);
            values.put(KEY_LIST_DUEDATE,listItem.dueDate);
            values.put(KEY_LIST_POSITION,String.valueOf(position));

            db.insertOrThrow(TABLE_LIST,null,values);
            db.setTransactionSuccessful();

        }catch (Exception e)
        {
            Log.d("DEBUG","Error while trying to add list item to database");

        }finally {
            db.endTransaction();
        }

    }

    public int updateListItem(ListItem listItem, int position)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LIST_TEXT,listItem.text);
        contentValues.put(KEY_LIST_DUEDATE,listItem.dueDate.toString());

        return db.update(TABLE_LIST,contentValues, KEY_LIST_POSITION + " = ?",
                new String[]{String.valueOf(position)});

    }

    public void deleteListItem(int position)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_LIST,KEY_LIST_POSITION + " = ?",
                new String[]{String.valueOf(position)});
    }

    public List<ListItem> getAllListItems()
    {
        String LIST_SELECT_QUERY = String.format("Select * from " + TABLE_LIST);

        SQLiteDatabase db= getReadableDatabase();
        List<ListItem> listItemArray = new ArrayList<>();

        Cursor cursor = db.rawQuery(LIST_SELECT_QUERY,null);

        try
        {
            if (cursor.moveToFirst())
            {
                do {
                    ListItem newItem = new ListItem(cursor.getString(cursor.getColumnIndex(KEY_LIST_TEXT)),
                           cursor.getString(cursor.getColumnIndex(KEY_LIST_DUEDATE)));

                    listItemArray.add(newItem);

                }while (cursor.moveToNext());
            }

        }
        catch (Exception e)
        {
            Log.d("DEBUG","Error while reading list items from database");
            e.printStackTrace();
        }
        finally {
            if(cursor !=null&& !cursor.isClosed()) {
                cursor.close();
            }
        }

        return  listItemArray;
    }


    public void deleteAllListItems()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try
        {
            db.delete(TABLE_LIST,null,null);

        }catch (Exception e)
        {
            Log.d("DEBUG", "Error while trying to delete all list items");
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }

}
