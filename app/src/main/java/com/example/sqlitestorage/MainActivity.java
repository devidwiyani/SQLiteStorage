package com.example.sqlitestorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static DatabaseHelper dbHelper;
    ArrayList<String> arrayListName;
    ArrayList<Integer> arrayListNim;
    ListView listView;
    Button buttonTambah;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate : started");

        dbHelper = new DatabaseHelper(MainActivity.this);
        arrayListName = new ArrayList<>();
        arrayListNim = new ArrayList<>();
        buttonTambah = findViewById(R.id.mainButtonTambahData);
        listView = findViewById(R.id.mainListViewDataSiswa);

        Cursor cursor = dbHelper.tampilSemuaDataSiswa();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                arrayListNim.add(Integer.parseInt(cursor.getString(0)));
                arrayListName.add(cursor.getString(1));
            }
        }
        arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,arrayListName);
        listView.setAdapter(arrayAdapter);

        registerForContextMenu(listView);
    }

    public void fungsiTambahData(View view){
        Intent intent = new Intent(this, TambahActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int index = info.position;
        menu.setHeaderTitle("Option");
        menu.add(index,v.getId(), 0, "View");
        menu.add(index,v.getId(), 0, "Edit");
        menu.add(index,v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if (item.getTitle() == "View"){
            Intent intent = new Intent(MainActivity.this, LihatActivity.class);
            intent.putExtra("MainNIM", Integer.toString(arrayListNim.get(item.getGroupId())));
            startActivity(intent);
        }else if (item.getTitle() == "Edit"){
            Intent intent = new Intent(MainActivity.this, UbahActivity.class);
            intent.putExtra("MainNIM", Integer.toString(arrayListNim.get(item.getGroupId())));
            startActivity(intent);
        }else if (item.getTitle() == "Delete"){
            dbHelper.deleteSiswa(Integer.toString(arrayListNim.get(item.getGroupId())));
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            return false;
        }
        return super.onContextItemSelected(item);
    }
}