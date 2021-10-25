package com.example.sqlitestorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LihatActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    TextView textViewNIM, textViewNama, textViewEmail;
    Button buttonKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);
        textViewNIM = findViewById(R.id.lihatTextViewNIM);
        textViewNama = findViewById(R.id.lihatTextViewNama);
        textViewEmail = findViewById(R.id.lihatTextViewEmail);
        buttonKembali = findViewById(R.id.lihatButtonKembali);

        DatabaseHelper dbHelper = new DatabaseHelper(LihatActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata WHERE nim = '" +
                getIntent().getStringExtra("MainNIM") + "'", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            cursor.moveToPosition(0);
            textViewNIM.setText(cursor.getString(0).toString());
            textViewNama.setText(cursor.getString(1).toString());
            textViewEmail.setText(cursor.getString(2).toString());
        }
    }

    public void fungsiKembali(View view){
        Intent intent = new Intent(LihatActivity.this, MainActivity.class);
        startActivity(intent);
        LihatActivity.this.finish();
    }
}