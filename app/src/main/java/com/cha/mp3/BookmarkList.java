package com.cha.mp3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class BookmarkList extends AppCompatActivity {
    MyDB mydb;
    BM_Adapter adapter;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);

        mydb = new MyDB(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql2 = "SELECT * FROM bookmark";
        cursor = db.rawQuery(sql2, null);

        adapter = new BM_Adapter(getApplicationContext(), R.layout.bm_item, cursor);
        ListView lv = (ListView)findViewById(R.id.bm_lv);
          lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();

                SQLiteDatabase db = mydb.getReadableDatabase();
                String sql2 = "SELECT * FROM bookmark";
                adapter.cursor = db.rawQuery(sql2, null);
                adapter.cursor.moveToPosition(position);
                //String s = adapter.cursor.getString(adapter.cursor.getColumnIndex("name"));
                String s2 = adapter.cursor.getString(adapter.cursor.getColumnIndex("title"));
                Double d = adapter.cursor.getDouble(adapter.cursor.getColumnIndex("start"));
                String s3 = adapter.cursor.getString(adapter.cursor.getColumnIndex("path"));
                int i = adapter.cursor.getInt(adapter.cursor.getColumnIndex("bmcp"));

                intent.putExtra("BM_TITLE", s2);
                intent.putExtra("BM_START", d);
                intent.putExtra("BM_PATH", s3);
                intent.putExtra("BM_CP", i);

                setResult(RESULT_OK, intent);
                finish();


            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteDatabase db = mydb.getWritableDatabase();
                adapter.cursor.moveToPosition(position);
                int key = cursor.getInt(cursor.getColumnIndex("_id"));
                String sql = String.format("DELETE FROM bookmark WHERE _id=%d;", key);

                db.execSQL(sql);

                sql = "SELECT * FROM bookmark;";
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
