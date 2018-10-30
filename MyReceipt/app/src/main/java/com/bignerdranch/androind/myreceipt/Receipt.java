package com.bignerdranch.androind.myreceipt;

import java.util.Date;
import java.util.UUID;

/**
 * Created by t_t056 on 30/10/2018.
 */

public class Receipt {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSeen;

    public Receipt() {

    }

    public Receipt(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSeen() {
        return mSeen;
    }

    public void setSeen(boolean seen) {
        mSeen = seen;
    }
}
