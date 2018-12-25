package com.example.princesaoud.dictionnaryapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "my_dic.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBHelper";
    private static final String TENG_FR = "Teng_french";
    private static final String TFR_ENG = "Tfrench_eng";
    private static final String TBOOKMARK = "bookmark";
    private static final String COL_KEY = "key";
    private static final String COL_VALUE = "value";
    public SQLiteDatabase mDB;
    private Context mContext;
    private String DATABASE_PATH = "";
    private String DATABASE_FULL_PATH = "";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;


        DATABASE_PATH = "data/data/" + mContext.getPackageName() + "/database/";
        DATABASE_FULL_PATH = DATABASE_PATH + DATABASE_NAME;

        if (!isExistingDB()) {
            try {
                File dbLocation = new File(DATABASE_PATH);
                dbLocation.mkdirs();
                copyDataBase();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_FULL_PATH, null);
    }

    public ArrayList<String> getWord(int dictype) {
        String tableName = getTableName(dictype);
        final String query = "SELECT * FROM " + tableName;
        Log.e(TAG, "getWord: " + query);
        Cursor cursor = mDB.rawQuery(query, null);
        ArrayList<String> temp = new ArrayList<>();
        while (cursor.moveToNext()) {
            temp.add(cursor.getString(cursor.getColumnIndex(COL_KEY)));
        }
//        cursor.close();
        return temp;
    }

    public ArrayList<String> getBookmark() {
        String query = "select * from " + TBOOKMARK + " order by [date] desc";
        Cursor cursor = mDB.rawQuery(query, null);
        ArrayList<String> temp = new ArrayList<>();
        while (cursor.moveToNext()) {
            temp.add(cursor.getString(cursor.getColumnIndex(COL_KEY)));
            temp.add(cursor.getString(cursor.getColumnIndex(COL_VALUE)));
        }
        cursor.close();
        return temp;
    }

    public boolean isWordMark(Word word) {
        String query = "select * from bookmark where upper([key]) = upper(?) and upper([value]) = upper(?)";
        Cursor cursor = mDB.rawQuery(query, new String[]{word.getKey(), word.getValue()});
        return cursor.getCount() > 0;
    }

    public Word getWordFromBookmark(String key) {
//        use to retrieve a word from bookmark
        String query = "select * from "+TBOOKMARK+" where upper([" + COL_KEY + "]) = upper(?)";
        Cursor cursor = mDB.rawQuery(query, new String[]{key});
        Word word = null;
        while (cursor.moveToNext()) {
            word = new Word();
            word.setKey(cursor.getString(cursor.getColumnIndex(COL_KEY)));
            word.setValue(cursor.getString(cursor.getColumnIndex(COL_VALUE)));
        }
        cursor.close();
        return word;
    }

    public ArrayList<String> getAllWordFromBookmark() {
//        use to retrieve a word from bookmark
        String query = "select * from "+TBOOKMARK+"";
        Cursor cursor = mDB.rawQuery(query, null);
        ArrayList<String> values = new ArrayList<>();
        while (cursor.moveToNext()) {
            values.add(cursor.getString(cursor.getColumnIndex(COL_KEY)));
//            values.add(cursor.getString(cursor.getColumnIndex(COL_VALUE)));
        }
        cursor.close();
        return values;
    }

    public Word getWord(int dictype, String key) {

        try {
            String tableName = getTableName(dictype);
            final String query = "select * from " + tableName + " where upper(["+COL_KEY+"]) = upper(?)";

            Cursor cursor = mDB.rawQuery(query, new String[]{key});
            Word temp =new Word();
            while (cursor.moveToNext()) {
    //            temp = new Word();
                temp.setKey(cursor.getString(cursor.getColumnIndex(COL_KEY)));
                temp.setValue(cursor.getString(cursor.getColumnIndex(COL_VALUE)));
            }
            cursor.close();
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addBookmark(Word word) {
        try {
            String query = "insert into " + TBOOKMARK + " ([key],[value]) values (?,?);";
            mDB.execSQL(query, new Object[]{word.getKey(), word.getValue()});

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBookmark(Word word) {
        try {
            String query = "delete from " + TBOOKMARK + " where upper([" + COL_KEY + "]) = upper(?) and upper([" + COL_VALUE + "]) = upper(?)";
            mDB.execSQL(query, new Object[]{word.getKey(), word.getValue()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTableName(int dicType) {
        String tableName = "";
        if (dicType == R.id.item_english_french) {
            tableName = TENG_FR;
        } else if (dicType == R.id.item_french_english) {
            tableName = TFR_ENG;
        }
        return tableName;
    }

    private boolean isExistingDB() {
        File file = new File(DATABASE_FULL_PATH);
        return file.exists();
    }

    private void copyDataBase() throws IOException {

        InputStream mInput = mContext.getAssets().open(DBHelper.DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[2024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
