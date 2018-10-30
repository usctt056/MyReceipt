package com.bignerdranch.androind.myreceipt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.database.ReceiptBaseHelper;
import com.bignerdranch.database.ReceiptCursorWrapper;
import com.bignerdranch.database.ReceiptDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by t_t056 on 30/10/2018.
 */

public class ReceiptLab {
    private static ReceiptLab sReceiptLab;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ReceiptLab get(Context context) {
        if (sReceiptLab == null) {
            sReceiptLab = new ReceiptLab(context);
        }
        return sReceiptLab;
    }
    private ReceiptLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ReceiptBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addReceipt(Receipt r) {
        ContentValues values = getContentValues(r);
        mDatabase.insert(ReceiptDbSchema.ReceiptTable.NAME, null, values);
    }

    public List<Receipt> getReceipts() {
        List<Receipt> receipts = new ArrayList<>();
        ReceiptCursorWrapper cursor = queryReceipts(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                receipts.add(cursor.getReceipt());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return receipts;
    }

    public Receipt getReceipt(UUID id) {
        ReceiptCursorWrapper cursor = queryReceipts(
                ReceiptDbSchema.ReceiptTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getReceipt();
        } finally {
            cursor.close();
        }
    }

    public void updateReceipt(Receipt receipt) {
        String uuidString = receipt.getId().toString();
        ContentValues values = getContentValues(receipt);
        mDatabase.update(ReceiptDbSchema.ReceiptTable.NAME, values,
                ReceiptDbSchema.ReceiptTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private ReceiptCursorWrapper queryReceipts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ReceiptDbSchema.ReceiptTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new ReceiptCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Receipt receipt) {
        ContentValues values = new ContentValues();
        values.put(ReceiptDbSchema.ReceiptTable.Cols.UUID, receipt.getId().toString());
        values.put(ReceiptDbSchema.ReceiptTable.Cols.TITLE, receipt.getTitle());
        values.put(ReceiptDbSchema.ReceiptTable.Cols.DATE, receipt.getDate().getTime());
        values.put(ReceiptDbSchema.ReceiptTable.Cols.SEEN, receipt.isSeen() ? 1 : 0);
        return values;
    }
}
