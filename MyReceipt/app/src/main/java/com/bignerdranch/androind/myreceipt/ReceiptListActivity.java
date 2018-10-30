package com.bignerdranch.androind.myreceipt;

import android.support.v4.app.Fragment;

/**
 * Created by t_t056 on 30/10/2018.
 */

public class ReceiptListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ReceiptListFragment();
    }
}
