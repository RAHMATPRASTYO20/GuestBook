package com.example.guestbook.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.TextView;

import com.example.guestbook.R;
import com.example.guestbook.data.PetContract;

public class EventCatalogAdapter extends ResourceCursorAdapter {

    private TextView tvName;

    public EventCatalogAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        tvName = (TextView) view.findViewById(R.id.tv_name);

        final int idColumnIndex = cursor.getColumnIndex(PetContract.PetEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_EVENT_NAME);

        tvName.setText(cursor.getString(nameColumnIndex));

    }
}
