package com.bignerdranch.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.database.ReceiptDbSchema.ReceiptTable;

/**
 * Created by t_t056 on 30/10/2018.
 */
public class ReceiptBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "receiptBase.db";
    public ReceiptBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ReceiptTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ReceiptTable.Cols.UUID + ", " +
                ReceiptTable.Cols.TITLE + ", " +
                ReceiptTable.Cols.DATE + ", " +
                ReceiptTable.Cols.SEEN +
                ")"

        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}