package com.example.balanceviewer.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.balanceviewer.Class.Person;

import java.util.ArrayList;

/* Made by Rishabh Anand */

public class PersonDB {
    public static final String KEY_ROWID="_id";
    public static final String KEY_NAME="person_name";
    public static final String KEY_AMT="_cell";

    private  final String DATABASE_NAME="ContactsDB";
    private final String DATABASE_TABLE="ContactsTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public  PersonDB (Context context)
    {
        ourContext=context;
    }

    private class DBHelper extends SQLiteOpenHelper {
        private DBHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode="CREATE TABLE "+DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KEY_NAME + " TEXT NOT NULL , " +
                    KEY_AMT + " TEXT NOT NULL );";
            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE);
            onCreate(db);
        }
    }

    public  PersonDB open() throws SQLException {
        ourHelper  = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return  this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long createEntry(String name, String cell) {
        ContentValues cv =new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_AMT,cell);
        return ourDatabase.insert(DATABASE_TABLE, null ,cv);
    }

    public ArrayList<Person> getArray() {
        String [] columns = new String[] {KEY_ROWID,KEY_NAME,KEY_AMT};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        ArrayList<Person> result=new ArrayList<Person>();
        int iName = c.getColumnIndex(KEY_NAME);
        int iCell = c.getColumnIndex(KEY_AMT);
        c.moveToFirst();
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            result.add(new Person(c.getString(iName),c.getString(iCell)));
        }
        c.close();
        return result;
    }

    public long deleteEntry(String rowId) {
        return ourDatabase.delete(DATABASE_TABLE,KEY_ROWID+ "=?",new String[]{rowId});
    }

    public long updateEntry(String rowId, String name,String amt) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_ROWID,rowId);
        cv.put(KEY_NAME,name);
        cv.put(KEY_AMT,amt);
        return ourDatabase.update(DATABASE_TABLE,cv,KEY_ROWID + "=" + rowId,null);
    }

    public int find_index(String n) {
        String [] columns = new String[] {KEY_ROWID,KEY_NAME,KEY_AMT};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        int iRowID=c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(n.equals(c.getString(iName)))
            {
                return Integer.parseInt(c.getString(iRowID));
            }
        }
        c.close();
        return -1;
    }

    public Person getIndexData(int i) {
        String [] columns = new String[] {KEY_ROWID,KEY_NAME,KEY_AMT};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        int iRowID=c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iCell = c.getColumnIndex(KEY_AMT);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(i==Integer.parseInt(c.getString(iRowID)))
            {
                Person p=new Person(c.getString(iName),c.getString(iCell));
                return p;
            }
        }
        c.close();
        Person p=new Person("Error","404");
        return p;
    }
}

