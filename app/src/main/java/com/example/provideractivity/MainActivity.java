package com.example.provideractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText_name;
    private EditText editText_meaing;
    private EditText editText_prounce;
    private Button insert, delete, update, query;
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_name = findViewById(R.id.edit_name);
        editText_meaing = findViewById(R.id.edit_meaing);
        editText_prounce = findViewById(R.id.edit_prounce);

        insert = findViewById(R.id.inster_but);
        delete = findViewById(R.id.delete_but);
        update = findViewById(R.id.update_but);
        query = findViewById(R.id.query_but);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText_name.getText().toString();
                String meaning = editText_meaing.getText().toString();
                String prounce = editText_prounce.getText().toString();
                //创建期待匹配的uri
                Uri uri1 = Uri.parse("content://com.example.wordsapp.provider/words");
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("meaning", meaning);
                values.put("prounce", prounce);
                //获得ContentResolver对象，调用方法
                ContentResolver con = getContentResolver();
                con.insert(uri1, values);
                Toast.makeText(MainActivity.this, "插入成功", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name1 = editText_name.getText().toString();
                Uri uri2 = Uri.parse("content://com.example.wordsapp.provider/words");
                getContentResolver().delete(uri2, "name=?", new String[]{name1});
                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText_name.getText().toString();
                String meaning = editText_meaing.getText().toString();
                String prounce = editText_prounce.getText().toString();
                //创建期待匹配的uri
                Uri uri1 = Uri.parse("content://com.example.wordsapp.provider/words");
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("meaning", meaning);
                values.put("prounce", prounce);
                getContentResolver().update(uri1, values, "name=?", new String[]{name});
                Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();

            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = null;
                String name1;
                name1 = editText_name.getText().toString();
                Log.i("name1", name1);
                Uri uri1 = Uri.parse("content://com.example.wordsapp.provider/words");
                if (name1.equals("")) {
                    cursor = getContentResolver().query(uri1, null, null, null, null);
                    cursor.moveToFirst();
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String meaning = cursor.getString(cursor.getColumnIndex("meaning"));
                        String prounce = cursor.getString(cursor.getColumnIndex("prounce"));
                        Toast.makeText(MainActivity.this, "查询结果：" + name + " " + meaning + " " + prounce, Toast.LENGTH_SHORT).show();

                    } while (cursor.moveToNext());
                }
                else {
                   cursor = getContentResolver().query(uri1,null,"name=?",new String[]{name1},null);
                    cursor.getCount();
                    while(cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String meaning = cursor.getString(cursor.getColumnIndex("meaning"));
                        String prounce = cursor.getString(cursor.getColumnIndex("prounce"));
                        Toast.makeText(MainActivity.this, "查询结果：" + name + " " + meaning + " " + prounce, Toast.LENGTH_SHORT).show();

                    }

                }
                cursor.close();
            }
        });

    }
}
