package library;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper
{
    public DatabaseHandler(Context context)
    {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + Constants.LOGIN_TAG + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_NAME + " TEXT,"
                + Constants.KEY_EMAIL + " TEXT UNIQUE,"
                + Constants.KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.LOGIN_TAG);
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(int id, String name, String email, String created_at)
    {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_ID, id);
        values.put(Constants.KEY_NAME, name);
        values.put(Constants.KEY_EMAIL, email);
        values.put(Constants.KEY_CREATED_AT, created_at);
 
        db.insert(Constants.LOGIN_TAG, null, values);
        db.close();
    }
     
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails()
    {
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + Constants.LOGIN_TAG;
          
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0)
        {
        	user.put("id", cursor.getString(0));
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("created_at", cursor.getString(3));
        }
        cursor.close();
        db.close();
        return user;
    }
 
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount()
    {
        String countQuery = "SELECT  * FROM " + Constants.LOGIN_TAG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        return rowCount;
    }
     
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.LOGIN_TAG, null, null);
        db.close();
    }
 
}
