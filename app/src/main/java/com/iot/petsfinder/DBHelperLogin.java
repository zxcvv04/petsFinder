package com.iot.petsfinder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class DataBaseHelper extends SQLiteOpenHelper
{
    DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase _db)
    {
        _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE);

    }
    // Called when there is a database version mismatch meaning that the version
    // of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion)
    {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion
                + ", which will destroy all old data");

        // Upgrade the existing database to conform to the new version. Multiple
        // previous versions can be handled by comparing _oldVersion and _newVersion
        // values.
        // The simplest case is to drop the old table and create a new one.
        _db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        // Create a new one.
        onCreate(_db);
    }

}

//public class DBHelperLogin {
//
//    private static final String DB_NAME = "petsfinder";
//    private static final String TABLE_NAME = "login";
//    private static final int DB_VERSION = 1;
//
//    public DBHelperLogin(String userName, String password) {
//
//    }
//
//    /////db helper
//    private class DatabaseHelper extends SQLiteOpenHelper {
//
//        DatabaseHelper(Context context) {
//            super(context, DB_NAME, null, DB_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            try {
//                String DROP_SQL = "drop table if exists " + TABLE_NAME;
//                db.execSQL(DROP_SQL);
//            } catch (Exception e) { ErrLogger(e); }
//
//            String CREATE_SQL = "create table " + TABLE_NAME + " (" +
//                    " mail text PRIMARY KEY, " +
//                    " pw text )";
//
//            try { db.execSQL(CREATE_SQL); } catch (Exception e) { ErrLogger(e); }
//
//        }
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            Log.w(TAG, "upgrading database version from " + oldVersion + " to " + newVersion);
//        }
//
//        void ErrLogger(Exception e) {
//            e.printStackTrace();
//            Log.e(TAG, e.getMessage());
//        }
//    }
