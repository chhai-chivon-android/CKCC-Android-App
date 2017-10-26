package kh.edu.rupp.ckccapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * CKCCApp
 * Created by leapkh on 10/26/17.
 */

public class DbManager extends SQLiteAssetHelper {

    public DbManager(Context context) {
        super(context, "ckcc-app-db.sqlite", null, 1);
    }

    /*
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tableCreationSql = "create table LoginHistory(_id integer primary key autoincrement, " +
                "_username text, _date integer, _success integer)";
        sqLiteDatabase.execSQL(tableCreationSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    */

    // Fuction for inserting login history
    public void insertLoginHistory(LoginHistory history) {

        ContentValues record = new ContentValues();
        record.put("_username", history.getUsername());
        record.put("_date", history.getDate());
        record.put("_success", history.isSuccess());

        getWritableDatabase().insert("LoginHistory", null, record);

    }

    public ArrayList<LoginHistory> getHistories() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("LoginHistory", null, null, null, null, null, "_id desc");
        //Cursor cursor = db.rawQuery("select * from LoginHistory order by _id desc", null);

        ArrayList<LoginHistory> histories = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            long date = cursor.getLong(2);
            boolean isSuccess = (cursor.getInt(3) == 1);
            LoginHistory history = new LoginHistory(id, username, date, isSuccess);
            histories.add(history);
        }

        return histories;

    }

}































