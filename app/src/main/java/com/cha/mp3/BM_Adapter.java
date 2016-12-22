package com.cha.mp3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by C on 2015-12-05.
 */
public class BM_Adapter extends BaseAdapter{
    Context context;
    int item_layout;
    Cursor cursor;
    LayoutInflater inflater;
    public BM_Adapter(Context context, int item_layout, Cursor cursor) {
        this.context = context;
        this.item_layout = item_layout;
        this.cursor = cursor;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }
    @Override
    public Object getItem(int position) {
        cursor.moveToPosition(position); // 커서는 테이블을 들고온다, 커서는 포인터값이다. 커서의 포인터값을 우리가 임의로 바꿔서 값을 참조한다.
        return cursor;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(item_layout, null);
        }
        cursor.moveToPosition(position);

        TextView tv1 = (TextView)convertView.findViewById(R.id.bm_tv_name);
        tv1.setText(cursor.getString(cursor.getColumnIndex("name")));

        TextView tv2 = (TextView)convertView.findViewById(R.id.bm_tv_title);
        tv2.setText(cursor.getString(cursor.getColumnIndex("title")));

        TextView tv3 = (TextView)convertView.findViewById(R.id.bm_tv_start);
        double d2 = cursor.getDouble(cursor.getColumnIndex("start"));
        tv3.setText(String.format("시작시간 : %02d : %02d.%02d",
                        TimeUnit.MILLISECONDS.toMinutes((long) d2),
                        TimeUnit.MILLISECONDS.toSeconds((long) d2) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) d2)),
                        (TimeUnit.MILLISECONDS.toMillis((long)d2)-
                                TimeUnit.MILLISECONDS.toSeconds((long) d2)*1000)/10)
        );

        return convertView;
    }
}
