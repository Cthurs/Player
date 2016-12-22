package com.cha.mp3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ABlist extends AppCompatActivity {
    MyDB mydb;
    MyAdapter adapter;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ablist);

        mydb = new MyDB(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql2 = "SELECT * FROM repeat";
        cursor = db.rawQuery(sql2, null);

        adapter = new MyAdapter(getApplicationContext(), R.layout.item_layout, cursor);

        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();

                SQLiteDatabase db = mydb.getWritableDatabase();
                String sql2 = "SELECT * FROM repeat";
                adapter.cursor = db.rawQuery(sql2, null);
                adapter.cursor.moveToPosition(position);
                Double d = adapter.cursor.getDouble(adapter.cursor.getColumnIndex("ab"));
                Double d2 = adapter.cursor.getDouble(adapter.cursor.getColumnIndex("start"));
                Double d3 = adapter.cursor.getDouble(adapter.cursor.getColumnIndex("end"));
                String s3 = adapter.cursor.getString(adapter.cursor.getColumnIndex("path"));
                int i = adapter.cursor.getInt(adapter.cursor.getColumnIndex("abcp"));

                intent.putExtra("TERM", d);
                intent.putExtra("START", d2);
                intent.putExtra("END", d3);
                intent.putExtra("AB_PATH", s3);
                intent.putExtra("AB_CP", i);


                setResult(RESULT_OK, intent);
                finish();


            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteDatabase db = mydb.getWritableDatabase();
               /* String sql = "SELECT * FROM repeat;";
                Cursor cursor = db.rawQuery(sql, null);*/
                adapter.cursor.moveToPosition(position);
                int key = cursor.getInt(cursor.getColumnIndex("_id"));
                String sql = String.format("DELETE FROM repeat WHERE _id=%d;", key);

                db.execSQL(sql);

                sql = "SELECT * FROM repeat;";
                cursor = db.rawQuery(sql, null);
                adapter.cursor.close();
                adapter.cursor = cursor;
                adapter.notifyDataSetChanged();
                db.close();

                return true;
            }
        });
    }
}
