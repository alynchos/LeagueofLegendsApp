package com.leagueapp.alynchos.leagueapp.SaveData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.leagueapp.alynchos.leagueapp.Debug.Logger;

/**
 * Created by Alex Lynchosky on 1/11/2015.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    /* Debugging */
    private static final String TAG = FeedReaderDbHelper.class.getSimpleName();
    private static final Logger logger = new Logger(TAG);

    private static FeedReaderDbHelper feedReaderDbHelper;
    private static Context mContext;

    private static final String DELETE = "DELETE ";
    private static final String UPDATE = "UPDATE ";
    private static final String WHERE  = "WHERE ";
    private static final String SET = "SET ";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_PLAYER =
            "CREATE TABLE " + FeedEntry.TABLE_PLAYER + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    // Experience
                    FeedEntry.COLUMN_NAME + TEXT_TYPE +
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_DELETE_TABLE_PLAYER =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_PLAYER;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";


    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        createPlayerTable(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for character data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLE_PLAYER);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteDataBase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_TABLE_PLAYER);
        onCreate(db);
    }

    public void deletePlayerDataBase(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_TABLE_PLAYER);
        createPlayerTable(db);
    }

    public long insertData(String table, String columns[], Object data[]) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column name is the key
        ContentValues values = new ContentValues();
        for (int i = 0; i < data.length; i++) {
            if (data[i] instanceof String) {
                logger.debug("Inserting String");
                values.put(columns[i], (String) data[i]);
            } else if (data[i] instanceof Integer) {
                logger.debug("Inserting integer");
                values.put(columns[i], (Integer) data[i]);
            } else if (data[i] instanceof Double) {
                logger.debug("Inserting Double");
                values.put(columns[i], (Double) data[i]);
            } else {
                if (data[i] == null)
                    logger.debug("data at " + i + " is null");
                else
                    logger.debug("error inserting type: " + i + data[i].getClass().toString());
            }
        }
        // Insert the new row, returning the primary key value of the new row
        return db.insert(table, null, values);
    }

    // Right now this retrieves an entire column to make it easy on me
    public Cursor queryData(String table, String columns[]) {
        return queryData(table, columns, null);
    }

    // Right now this retrieves an entire column to make it easy on me
    public Cursor queryData(String table, String columns[], String selection) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                table,                                    // The table to query
                columns,                                  // The columns to return
                selection,                                // The selection string for the WHERE clause (can input ?s to use next field)
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );
        cursor.moveToFirst();
        return cursor;
    }

    public void updateData(String table, String columns[], Object data[]) {
        updateData(table, columns, data, null, null);
    }

    public void updateData(String table, String columns[], Object data[], String whereColumns[], Object whereArgs[]) {
        SQLiteDatabase db = getReadableDatabase();
        String exec = "";
        exec += UPDATE + table + " " + SET;
        for (int i = 0; i < data.length; i++) {
            if (i > 0) {
                exec += COMMA_SEP;
            }
            exec += columns[i] + "=";
            if (data[i] instanceof String) {
                logger.debug("Updating String: " + data[i].toString());
                exec += "'" + data[i] + "'";
            } else if (data[i] instanceof Integer) {
                logger.debug("Updating integer: " + data[i].toString());
                exec += data[i];
            } else if (data[i] instanceof Double) {
                logger.debug("Updating double: " + data[i].toString());
                exec += data[i];
            }else {
                logger.debug("Improper Object updated: " + data[i].getClass().getSimpleName());
                return;
            }
        }
        if(whereColumns != null && whereArgs != null && whereColumns.length == whereArgs.length){
            exec += " " + WHERE;
            for(int i = 0; i < whereColumns.length; i++){
                if (i > 0) {
                    exec += COMMA_SEP;
                }
                exec += whereColumns[i] + "=";
                if (whereArgs[i] instanceof String) {
                    logger.debug("Updating String: " + whereArgs[i].toString());
                    exec += "'" + whereArgs[i] + "'";
                } else if (whereArgs[i] instanceof Integer) {
                    logger.debug("Updating integer: " + whereArgs[i].toString());
                    exec += whereArgs[i];
                } else {
                    logger.debug("Improper Object updated: " + whereArgs[i].getClass().getSimpleName());
                    return;
                }
            }
        }
        logger.debug("\"" + exec + "\"");
        db.execSQL(exec);
    }


    /**
     * *************************************************************
     * *************** Private Helper Functions ********************
     * *************************************************************
     */

    private void createPlayerTable(SQLiteDatabase db) {
        // Character Table
        db.execSQL(SQL_CREATE_PLAYER);
        Object defaultNum[] = new Object[1];
        defaultNum[0] = new Integer(0);
        // Identity
        insertData(FeedEntry.TABLE_PLAYER, new String[]{FeedEntry.COLUMN_NAME}, new Object[]{new String("Name")});
    }

    /**
     * ***********************************************************
     */

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        // Character Table
        public static final String TABLE_PLAYER = "character";
        // Identity
        public static final String COLUMN_NAME = "player_name";
    }

    /**
     * ****************************************************
     * ************* Non SQL Related Functions **************
     * *****************************************************
     */

    public static void setContext(Context context) {
        mContext = context;
    }

    public static FeedReaderDbHelper getInstance() {
        if (mContext == null) return null;
        if (feedReaderDbHelper == null) {
            feedReaderDbHelper = new FeedReaderDbHelper(mContext);
        }
        return feedReaderDbHelper;
    }
}
