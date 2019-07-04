package com.example.guestbook.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class PetContract {
    public static final String CONTENT_AUTHORITY = "com.example.guestbook";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String EVENTS_PATH = "myevent";//nama tabel database

    public abstract static class PetEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, EVENTS_PATH);

        public static final String  TABLE_NAME = "myevent";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_EVENT_NAME = "event";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_NAME + " TEXT NOT NULL"+
                " );";

    }
}
