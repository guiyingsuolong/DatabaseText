package databasetext.example.com.databasetext;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * integer表示整型，real表示浮点型，text表示文本类型，blob表示二进制类型
 * primary key将id设为主键，autoincrement关键字表示id列是自动增长的
 *
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt= (Button) findViewById(R.id.bt);
        Button addbt= (Button) findViewById(R.id.addbt);
        Button upbt= (Button) findViewById(R.id.updatabt);
        Button debt= (Button) findViewById(R.id.delebt);
        Button rebt= (Button) findViewById(R.id.retrievebt);
        Button litebt= (Button) findViewById(R.id.litepalbt);
        Button liteaddbt= (Button) findViewById(R.id.liteaddbt);
        Button litedebt= (Button) findViewById(R.id.litedebt);
        Button liteupbt= (Button) findViewById(R.id.liteupbt);
        Button literebt= (Button) findViewById(R.id.literebt);
        Log.d("ee","woshi ");
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,1);
        bt.setOnClickListener(this);
        addbt.setOnClickListener(this);
        upbt.setOnClickListener(this);
        debt.setOnClickListener(this);
        rebt.setOnClickListener(this);
        litebt.setOnClickListener(this);
        liteaddbt.setOnClickListener(this);
        litedebt.setOnClickListener(this);
        liteupbt.setOnClickListener(this);
        literebt.setOnClickListener(this);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt:
                dbHelper.getWritableDatabase();
                break;
            case R.id.addbt:

                ContentValues values=new ContentValues();
                //开始组装第一条数据
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16);
                db.insert("Book",null,values);//插入第一条数据
                values.clear();
                //开始组装第二条数据
                values.put("name","The Lost Symbol");
                values.put("author","Dan Brown");
                values.put("pages",510);
                values.put("price",19.95);
                db.insert("Book",null,values);//插入第二条数据
                break;
            case R.id.updatabt:
                ContentValues value=new ContentValues();
                value.put("price",10.99);
                db.update("Book",value,"name = ?",new String[]{"The Da Vinci Code"} );
                break;
            case R.id.delebt:
                db.delete("Book","pages > ?",new String[]{"500"});
                break;
            case  R.id.retrievebt:
                Cursor cursor=db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        //遍历Cursor对象，取出数据并打印
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("Main","book name is"+name);
                        Log.d("Main","book author is"+author);
                        Log.d("Main","book pages is"+pages);
                        Log.d("Main","book price is"+price);
                    }while(cursor.moveToNext());
                }
                break;
            case R.id.litepalbt:
                Connector.getDatabase();
                break;
            case R.id.liteaddbt:
                Book book=new Book();
                book.setName("这个红楼梦");
                book.setAuthor("杨岗");
                book.setPages(800);
                book.setPrice(18.80);
                book.setPress("中国人民");
                book.save();
                break;
            case R.id.liteupbt:
                Book book1=new Book();
                book1.setPrice(10.99);
                book1.setAuthor("zhui");
                book1.updateAll("name = ? and author = ?","The Lost Symbol","Dan Brown");

                break;
            case  R.id.litedebt:
                DataSupport.deleteAll(Book.class,"price < ?","15");
                break;
            case R.id.literebt:
                List<Book> books=DataSupport.findAll(Book.class);
                for(Book book2: books){
                    Log.d("Main","book name is"+book2.getName());
                    Log.d("Main","book author is"+book2.getAuthor());
                    Log.d("Main","book pages is"+book2.getPages());
                    Log.d("Main","book press is"+book2.getPress());
                }
                break;
        }
    }
}
