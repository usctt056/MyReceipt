package com.bignerdranch.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.androind.myreceipt.Receipt;

import java.util.Date;
import java.util.UUID;

/**
 * Created by t_t056 on 30/10/2018.
 */
public class ReceiptCursorWrapper extends CursorWrapper {
    public ReceiptCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Receipt getReceipt() {
        String uuidString = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.UUID));
        String title = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.TITLE));
        long date = getLong(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.DATE));
        int isSeen = getInt(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.SEEN));

        Receipt receipt = new Receipt(UUID.fromString(uuidString));
        receipt.setTitle(title);
        receipt.setDate(new Date(date));
        receipt.setSeen(isSeen != 0);
        return receipt;
    }
}
