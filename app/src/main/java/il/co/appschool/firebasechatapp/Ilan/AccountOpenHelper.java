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

public class AccountOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASENAME = "account.db4";
    public static final String TABLE_ACCOUNT = "tblaccount";
    public static final int DATABASEVERSION = 1;

    public static final String COLUMN_ID = "accid";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_DISPLAYNAME = "displayname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ACCSPECID = "accspecid";

    public static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE IF NOT EXISTS " +
            TABLE_ACCOUNT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRSTNAME + " VARCHAR," + COLUMN_LASTNAME + " VARCHAR,"
            + COLUMN_DISPLAYNAME + " VARCHAR," + COLUMN_EMAIL + " VARCHAR," + COLUMN_ACCSPECID + " VARCHAR" + ");";


    String[] allColumns = {AccountOpenHelper.COLUMN_ID, AccountOpenHelper.COLUMN_FIRSTNAME, AccountOpenHelper.COLUMN_LASTNAME,
            AccountOpenHelper.COLUMN_EMAIL, AccountOpenHelper.COLUMN_DISPLAYNAME, AccountOpenHelper.COLUMN_ACCSPECID};

    SQLiteDatabase database;

    public AccountOpenHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("mosh", "Table customer created 1");

        db.execSQL(CREATE_TABLE_ACCOUNT);
        Log.d("mosh", "Table customer created 2");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        onCreate(db);
    }

    public void open() {
        database = this.getWritableDatabase();
        Log.i("generalACC", "Database connection open");
    }

    //    public Account createAccount(Account c) {
//        ContentValues values = new ContentValues();
//        values.put(AccountOpenHelper.COLUMN_ID, c.getId());
//        values.put(AccountOpenHelper.COLUMN_FIRSTNAME, c.getFirstName());
//        values.put(AccountOpenHelper.COLUMN_LASTNAME, c.getLastName());
//        values.put(AccountOpenHelper.COLUMN_DISPLAYNAME, c.getDisplayName());
//        values.put(AccountOpenHelper.COLUMN_EMAIL, c.getEmail());
//        values.put(AccountOpenHelper.COLUMN_ACCSPECID, c.getAccSpecId());
//
//        long insertId = database.insert(AccountOpenHelper.TABLE_ACCOUNT, null, values);
//        Log.d("mmmm", "AccountId " + insertId + "  " + "insert to database");
//        c.setId(insertId);
//        return c;
//
//    }
    public Account createAccount(Account B)//creating a new aacount
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, B.getFirstName());
        values.put(COLUMN_LASTNAME, B.getLastName());
        values.put(COLUMN_DISPLAYNAME, B.getDisplayName());
        values.put(COLUMN_EMAIL, B.getEmail());
        values.put(COLUMN_ACCSPECID, B.getAccSpecId());

        long insertId = database.insert(AccountOpenHelper.TABLE_ACCOUNT, null, values);
        Log.i("mmmm", "Product " + insertId + "insert to database");
        B.setId(insertId);
        return B;
    }



    public ArrayList<Account> getAllAccounts() ///function that bring you in arraylist all the accounts
    {

        ArrayList<Account> l = new ArrayList<Account>();
        Cursor cursor = database.query(AccountOpenHelper.TABLE_ACCOUNT, allColumns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(AccountOpenHelper.COLUMN_ID));
                String fname = cursor.getString(cursor.getColumnIndex(AccountOpenHelper.COLUMN_FIRSTNAME));
                String lname = cursor.getString(cursor.getColumnIndex(AccountOpenHelper.COLUMN_LASTNAME));
                String displayname = cursor.getString(cursor.getColumnIndex(AccountOpenHelper.COLUMN_DISPLAYNAME));
                String email = cursor.getString(cursor.getColumnIndex(AccountOpenHelper.COLUMN_EMAIL));
                String accspecid = cursor.getString(cursor.getColumnIndex(AccountOpenHelper.COLUMN_ACCSPECID));
                Account c = new Account(id, fname, lname, displayname, email, accspecid);
                l.add(c);
            }
        }
        return l;
    }


    public long deleteAllacc() ///delete all accounts
    {
        return database.delete(AccountOpenHelper.TABLE_ACCOUNT, null, null);
    }


    public long deleteAccountByRow(long rowId)/// delete account by id
    {
        return database.delete(AccountOpenHelper.TABLE_ACCOUNT, AccountOpenHelper.COLUMN_ID + "=" + rowId, null);
    }


    public long updateByRowacc(Account c)/// updating specific acc                    i removed this command  values.put(CustomerOpenHelper.COLUMN_ID, c.getCustormerId());
    {

        ContentValues values=new ContentValues();
        values.put(AccountOpenHelper.COLUMN_FIRSTNAME, c.getFirstName());
        values.put(AccountOpenHelper.COLUMN_LASTNAME, c.getLastName());
        values.put(AccountOpenHelper.COLUMN_DISPLAYNAME, c.getDisplayName());
        values.put(AccountOpenHelper.COLUMN_EMAIL, c.getEmail());
        values.put(AccountOpenHelper.COLUMN_ACCSPECID, c.getAccSpecId());

        return database.update(AccountOpenHelper.TABLE_ACCOUNT, values, AccountOpenHelper.COLUMN_ID +"=" + c.getId(), null);



    }
    public Account getAccountById(long rowId) ///getting account by id
    {
        Cursor cursor=database.query(AccountOpenHelper.TABLE_ACCOUNT, allColumns, AccountOpenHelper.COLUMN_ID + "=" +rowId, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            long id=cursor.getLong(cursor.getColumnIndex(AccountOpenHelper.COLUMN_ID));
            String fname=cursor.getString(cursor.getColumnIndex(AccountOpenHelper.COLUMN_FIRSTNAME));
            String lname=cursor.getString(cursor.getColumnIndex(AccountOpenHelper.COLUMN_LASTNAME));
            String displayname=cursor.getString(cursor.getColumnIndex(AccountOpenHelper.COLUMN_DISPLAYNAME));
            String email=cursor.getString(cursor.getColumnIndex(AccountOpenHelper.COLUMN_EMAIL));
            String accspecid=cursor.getString(cursor.getColumnIndex(AccountOpenHelper.COLUMN_ACCSPECID));
            Account c=new Account(id,fname,lname,displayname,email,accspecid);
            this.close();
            return c;
        }

        return null;

    }
}