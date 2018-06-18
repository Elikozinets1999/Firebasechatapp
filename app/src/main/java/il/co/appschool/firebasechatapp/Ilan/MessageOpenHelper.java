package il.co.appschool.firebasechatapp.Ilan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ailan on 03/04/2018.
 */

public class MessageOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASENAME = "message.db3";
    public static final String TABLE_MESSAGE = "tblmessage";
    public static final int DATABASEVERSION = 1;

    public static final String COLUMN_ID = "messageid";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_SAVEDAY = "msgsaveday";
    public static final String COLUMN_SAVEHOUR = "msgsavehour";
    public static final String COLUMN_FROMCHAT = "fromwho";

    public static final String CREATE_TABLE_MESSAGE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_MESSAGE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_MESSAGE + " VARCHAR," + COLUMN_SAVEDAY + " VARCHAR,"
            + COLUMN_SAVEHOUR + " VARCHAR," + COLUMN_FROMCHAT + " VARCHAR " + ");";


    String[] allColumns = {MessageOpenHelper.COLUMN_ID, MessageOpenHelper.COLUMN_MESSAGE, MessageOpenHelper.COLUMN_SAVEDAY,
            MessageOpenHelper.COLUMN_SAVEHOUR, MessageOpenHelper.COLUMN_FROMCHAT};

    SQLiteDatabase database;

    public MessageOpenHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("mosh", "Table customer created 1");

        db.execSQL(CREATE_TABLE_MESSAGE);
        Log.d("mosh", "Table customer created 2");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        onCreate(db);
    }

    public void open()  /// opening the database
    {
        database = this.getWritableDatabase();
        Log.i("Generalmsg", "Database connection open");
    }

    public Message createMessage(Message c) /// creating new message
    {
        ContentValues values = new ContentValues();
        values.put(MessageOpenHelper.COLUMN_MESSAGE, c.getMessage());
        values.put(MessageOpenHelper.COLUMN_SAVEDAY, c.getMsgSaveDay());
        values.put(MessageOpenHelper.COLUMN_SAVEHOUR, c.getMsgSaveHour());
        values.put(MessageOpenHelper.COLUMN_FROMCHAT, c.getFromWhichChat());
        long insertId = database.insert(MessageOpenHelper.TABLE_MESSAGE, null, values);
        Log.i("jj", "Customer " + insertId + "insert to database");
        c.setmessageId(insertId);
        return c;

    }

    public ArrayList<Message> getAllmessages()///function that bring you in arraylist all the messages
    {

        ArrayList<Message> l = new ArrayList<Message>();
        Cursor cursor = database.query(MessageOpenHelper.TABLE_MESSAGE, allColumns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(MessageOpenHelper.COLUMN_ID));
                String saveday = cursor.getString(cursor.getColumnIndex(MessageOpenHelper.COLUMN_SAVEDAY));
                String savehour = cursor.getString(cursor.getColumnIndex(MessageOpenHelper.COLUMN_SAVEHOUR));
                String message = cursor.getString(cursor.getColumnIndex(MessageOpenHelper.COLUMN_MESSAGE));
                String fromwho = cursor.getString(cursor.getColumnIndex(MessageOpenHelper.COLUMN_FROMCHAT));
                Message c = new Message(id, saveday, savehour, message, fromwho);
                l.add(c);
            }
        }
        return l;
    }
    public long deleteAllmsg() // deleting all messages
    {
        return database.delete(MessageOpenHelper.TABLE_MESSAGE, null, null);
    }


    public long deletemessagebyRow(long rowId) /// deleting message by id
    {
        return database.delete(MessageOpenHelper.TABLE_MESSAGE, MessageOpenHelper.COLUMN_ID + "=" + rowId, null);
    }


    public long updateByRowmsg(Message c)/// updating specific message                        i removed this command   values.put(MessageOpenHelper.COLUMN_ID, c.getCustormerId());
    {
        ContentValues values=new ContentValues();
        values.put(MessageOpenHelper.COLUMN_SAVEDAY, c.getMsgSaveDay());
        values.put(MessageOpenHelper.COLUMN_SAVEHOUR, c.getMsgSaveHour());
        values.put(MessageOpenHelper.COLUMN_MESSAGE, c.getMessage());
        values.put(MessageOpenHelper.COLUMN_FROMCHAT, c.getFromWhichChat());
        return database.update(MessageOpenHelper.TABLE_MESSAGE, values, MessageOpenHelper.COLUMN_ID +"=" + c.getmessageId(), null);
    }

    public Message getCustomerById(long rowId)/// getting message by specific id
    {
        Cursor cursor=database.query(MessageOpenHelper.TABLE_MESSAGE, allColumns, MessageOpenHelper.COLUMN_ID + "=" +rowId, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            long id=cursor.getLong(cursor.getColumnIndex(MessageOpenHelper.COLUMN_ID));
            String msgsaveday=cursor.getString(cursor.getColumnIndex(MessageOpenHelper.COLUMN_SAVEDAY));
            String msgsavehour=cursor.getString(cursor.getColumnIndex(MessageOpenHelper.COLUMN_SAVEHOUR));
            String message=cursor.getString(cursor.getColumnIndex(MessageOpenHelper.COLUMN_MESSAGE));
            String fromwho=cursor.getString(cursor.getColumnIndex(MessageOpenHelper.COLUMN_FROMCHAT));
            Message c=new Message(id,msgsaveday,msgsavehour,message,fromwho);
            this.close();
            return c;
        }

        return null;

    }

}