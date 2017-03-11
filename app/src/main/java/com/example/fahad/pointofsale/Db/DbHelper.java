package com.example.fahad.pointofsale.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dao.SalesTable;

/**
 * Created by fahad on 3/4/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "POS.db";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE SALES_RECORD (ProductName text,Quantity text,Price text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SALES_RECORD");
        onCreate(db);
    }


    public void insertSales(String productName,String Quantity,String Price){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("ProductName", productName);
            cv.put("Quantity", Quantity);
            cv.put("Price", Price);
            db.insert("SALES_RECORD", null, cv);
            Log.e("Insert",cv.toString());
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Error",e.getMessage());
        }
    }

    public List<SalesTable> getSales(){
        List<SalesTable> list=new ArrayList<>();
        String sql="SELECT * FROM SALES_RECORD";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor rs=db.rawQuery(sql,null);
        rs.moveToFirst();
        while (!rs.isAfterLast()){
            SalesTable salesTable=new SalesTable();
            salesTable.setProductName(rs.getString(0));
            salesTable.setQuantity(rs.getString(1));
            salesTable.setPrice(rs.getString(2));
            list.add(salesTable);

            rs.moveToNext();
        }
        return list;
    }
}
