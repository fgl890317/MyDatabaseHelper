package com.example.administrator.mydatabasehelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String newId;
   // private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  dbHelper=new MyDatabaseHelper(this,"BookStore.db",null,3);

        findViewById(R.id.add_data).setOnClickListener(this);
        findViewById(R.id.query_data).setOnClickListener(this);
        findViewById(R.id.update_data).setOnClickListener(this);
        findViewById(R.id.delete_data).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_data:
                Uri uri=Uri.parse("content://com.example.administrator.mydatabasehelper.provider/book");
                ContentValues values=new ContentValues();
                values.put("name","A Clash of Kings");
                values.put("author","George Martin");
                values.put("pages", 1040);
                values.put("price",22.85);
                Uri newUri=getContentResolver().insert(uri,values);
                newId=newUri.getPathSegments().get(1);
                break;

            case R.id.query_data:
                Uri uri1=Uri.parse("content://com.example.administrator.mydatabasehelper.provider/book");
                Cursor cursor=getContentResolver().query(uri1, null, null, null, null);
                if (cursor!=null){
                    while (cursor.moveToNext()){
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity","book name is"+name);
                        Log.d("MainActivity","book author is"+author);
                        Log.d("MainActivity","book pages is"+pages);
                        Log.d("MainActivity","book price is"+price);
                    }
                }
                cursor.close();
                break;
            case R.id.update_data:
                uri=Uri.parse("content://com.example.administrator.mydatabasehelper.provider/book/"+newId);
                ContentValues values1=new ContentValues();
                values1.put("name","A Storm of Swords");
                values1.put("pages",1216);
                values1.put("price", 24.05);
                getContentResolver().update(uri,values1,null,null);
                break;
            case R.id.delete_data:
                uri=Uri.parse("content://com.example.administrator.mydatabasehelper.provider/book/"+newId);
                getContentResolver().delete(uri,null,null);
                break;
        }
    }
}
