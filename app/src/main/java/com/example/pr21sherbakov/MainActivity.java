package com.example.pr21sherbakov;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    ListView userList;
    SimpleCursorAdapter userAdapter;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.userList);
        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> startActivity(new Intent(this, UserActivity.class)));

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userCursor = db.query(DatabaseHelper.TABLE, null, null, null, null, null, null);

        String[] headers = {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);

        userList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userCursor != null) userCursor.close();
        if (db != null) db.close();
    }
}