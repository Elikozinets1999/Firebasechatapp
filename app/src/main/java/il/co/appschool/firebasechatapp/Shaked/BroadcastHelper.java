package il.co.appschool.firebasechatapp.Shaked;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by pc on 23/04/2018.
 */

public class BroadcastHelper extends SQLiteOpenHelper {
    public static final String DATABASENAME="broadcast.db";
    public static final String TABLE_BROADCAST="tblbroadcast";
    public static final int DATABASEVERSION=1;

    public static final String COLUMN_ID="broadcastId";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_DESCRIPTION="description";
    public static final String COLUMN_TIME="time";

    SQLiteDatabase database;

    private static final String CREATE_TABLE_BROADCAST="CREATE TABLE IF NOT EXISTS " +
            TABLE_BROADCAST + "(" + COLUMN_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " VARCHAR," + COLUMN_DESCRIPTION + " VARCHAR,"
            + COLUMN_TIME +   " INTEGER "  +   ");";


    String []allColumns={BroadcastHelper.COLUMN_ID, BroadcastHelper.COLUMN_NAME,BroadcastHelper.COLUMN_DESCRIPTION,
            BroadcastHelper.COLUMN_TIME};


    public BroadcastHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BROADCAST);
        Log.i("data1", "Table broadcast created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BROADCAST);
        onCreate(db);
    }

    public void open() //opens the db
    {
        database=this.getWritableDatabase();
        Log.i("data", "Database connection open");
    }


    public Broadcast createBroadcast2(Broadcast b) //creates a Broadcast
    {
        ContentValues values=new ContentValues();
        values.put(BroadcastHelper.COLUMN_NAME, b.getName());
        values.put(BroadcastHelper.COLUMN_DESCRIPTION, b.getDescription());
        values.put(BroadcastHelper.COLUMN_TIME, b.getTime());

        long insertId=database.insert(BroadcastHelper.TABLE_BROADCAST, null, values);
        Log.i("data", "Broadcast " + insertId + "insert to database");
        b.setBroadcastId(insertId);
        return b;
    }
    public ArrayList<Broadcast> getAllBroadcasts() { //returns an ArrayList of the Broadcasts in the db.

        ArrayList<Broadcast> l = new ArrayList<Broadcast>();
        Cursor cursor=database.query(BroadcastHelper.TABLE_BROADCAST, allColumns, null, null, null, null, null);

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                long id=cursor.getLong(cursor.getColumnIndex(BroadcastHelper.COLUMN_ID));
                String name=cursor.getString(cursor.getColumnIndex(BroadcastHelper.COLUMN_NAME));
                String desc=cursor.getString(cursor.getColumnIndex(BroadcastHelper.COLUMN_DESCRIPTION));
                String time=cursor.getString(cursor.getColumnIndex(BroadcastHelper.COLUMN_TIME));
                Broadcast b=new Broadcast(name,desc,time,id);
                l.add(b);
            }
        }
        return l;
    }

    public long deleteAll() //deletes all the Broadcasts in the db.
    {
        return database.delete(BroadcastHelper.TABLE_BROADCAST, null, null);
    }
}
