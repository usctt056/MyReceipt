package com.bignerdranch.database;

/**
 * Created by t_t056 on 30/10/2018.
 */
public class ReceiptDbSchema {
    public static final class ReceiptTable {
        public static final String NAME = "receipts";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SEEN = "seen";
        }
    }
}
