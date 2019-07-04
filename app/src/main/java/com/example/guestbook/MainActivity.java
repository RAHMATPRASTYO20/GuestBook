package com.example.guestbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guestbook.adapter.EventCatalogAdapter;
import com.example.guestbook.data.EventDbHelper;
import com.example.guestbook.data.PetContract;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "eventid";
    public static final String EXTRA_NAME = "eventname";
    public static final String EXTRA_IS_EDIT_MODE = "isEditMode";

    private EventDbHelper eventDbHelper;

    private boolean isEditMode = false;

    private long id;

    private TextView tvCount;
    private ListView rvEventCatalog;
    private FloatingActionButton fabEvent;
    private View emptyView;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText etEvent;
    String event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = (TextView) findViewById(R.id.tv_count);
        rvEventCatalog = (ListView) findViewById(R.id.rv_event_catalog);
        fabEvent = (FloatingActionButton) findViewById(R.id.fab_event);
        emptyView = (View) findViewById(R.id.empty_event_view);


        EventDbHelper meventDbHelper = new EventDbHelper(this);
        SQLiteDatabase db = meventDbHelper.getReadableDatabase();
        SQLiteDatabase dbwrite = meventDbHelper.getWritableDatabase();

        displayDatabaseInfo();
        fabEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFrom();
            }
        });



        //buat nerima intent dari EventCatalogAdapter
        Intent intent = getIntent();
        if (intent.getExtras()!= null) {
            id = intent.getLongExtra(EXTRA_ID, -1);
            etEvent.setText(intent.getExtras().getString(EXTRA_NAME));
            isEditMode = intent.getBooleanExtra(EXTRA_IS_EDIT_MODE, false);
        }

    }

    private void displayDatabaseInfo() {
        String[] projection = {PetContract.PetEntry._ID, PetContract.PetEntry.COLUMN_EVENT_NAME};
        final Cursor cursor = getContentResolver().query(PetContract.PetEntry.CONTENT_URI, projection, null, null, null);

        tvCount.setText("data table pet consists : " + cursor.getCount() + " rows. \n\n");
        tvCount.append(PetContract.PetEntry._ID + " - " +
                PetContract.PetEntry.COLUMN_EVENT_NAME + " - " + "\n");

        final int idColumnIndex = cursor.getColumnIndex(PetContract.PetEntry._ID);
        final int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_EVENT_NAME);

        EventCatalogAdapter adapter = new EventCatalogAdapter(this, R.layout.item_list_event,cursor,0);
        rvEventCatalog.setAdapter(adapter);
        adapter.changeCursor(cursor);//buat auto close ketika selesai buat item

        if (cursor.getCount() > 0) {
            rvEventCatalog.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }else {
            rvEventCatalog.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

        adapter.changeCursor(cursor);
    }

    private void dialogFrom() {
        dialog = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialogTheme);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_name_event, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Create your event");

        etEvent = (EditText) dialogView.findViewById(R.id.txt_event);

        dialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                event = etEvent.getText().toString().trim();
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Berhasil disimpan didatabase", Toast.LENGTH_LONG).show();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
