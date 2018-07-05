package com.example.luiz1.nocash.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.luiz1.nocash.Model.Movimento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseTransacao extends SQLiteOpenHelper{

    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "carteiravirtual.db";
    private static final String TABLE_NAME = "movimento";
    private static final String COL_1 = "id";
    private static final String COL_2 = "carteiraOrigem";
    private static final String COL_3 = "carteiraDestino";
    private static final String COL_4 = "nrDocumento";
    private static final String COL_5 = "vlBruto";
    private static final String COL_6 = "vlLiquido";
    private static final String COL_7 = "vlDesc";
    private static final String COL_8 = "dtMovimento";

    public DatabaseTransacao(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " " +
                "(id integer primary key autoincrement," +
                "carteiraOrigem integer," +
                "carteiraDestino integer," +
                "nrDocumento varchar(60)," +
                "vlBruto numeric(14,2)," +
                "vlLiquido numeric(14,2)," +
                "vlDesc numerci(14,2)," +
                "dtMovimento date) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        Log.e("DATABASE", "Tabela deletada");
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public boolean insertData(int carteiraOrigem, int carteiraDestino, String doc, double bruto,
                              double liquido, double desc, String data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, carteiraOrigem);
        contentValues.put(COL_3, carteiraDestino);
        contentValues.put(COL_4, doc);
        contentValues.put(COL_5, bruto);
        contentValues.put(COL_6, liquido);
        contentValues.put(COL_7, desc);
        contentValues.put(COL_8, data);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public List<Movimento> getAllData() throws ParseException {
        List<Movimento> list = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("" +
                "select " +
                "id, " +
                "carteiraOrigem, " +
                "carteiraDestino, " +
                "nrDocumento, " +
                "vlBruto," +
                "vlLiquido," +
                "vlDesc," +
                "dtMovimento " +
                "from  " + TABLE_NAME, null);

        if(res.getCount() > 0){
            res.moveToFirst();
            do {
                Movimento movimento = new Movimento();
                movimento.setId(res.getInt(0));
                movimento.setNrDocumento(res.getString(3));
                movimento.setVlBruto(res.getDouble(4));
                movimento.setVlLiquido(res.getDouble(5));
                movimento.setVlDesc(res.getDouble(6));

                String s = res.getString(7);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date d = dateFormat.parse(s);
                movimento.setDtMovimento(d);


                list.add(movimento);
            } while (res.moveToNext());
        }

        return list;
    }
}
