package com.mostafa_anter.dbproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.mostafa_anter.dbproject.data.FeedReaderDbHelper;
import com.mostafa_anter.dbproject.data.FeedReaderContract.FeedEntry;

/**
 * Created by mostafa on 08/02/16.
 */
public class TestDb extends AndroidTestCase {
    public void TestCreateDb()throws Throwable{
        mContext.deleteDatabase(FeedReaderDbHelper.DATABASE_NAME);
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(mContext);
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        assertEquals("Incorrect result", true, db.isOpen());
        db.close();

    }

    public void testInsertReadDb(){
        String title = "Java";
        String content = "is a programming language";

        // access dataBase
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(mContext);
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedEntry.COLUMN_NAME_CONTENT, content);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedEntry.TABLE_NAME,
                null,
                values);

        // verify we got row back
        if(newRowId != -1){
            Log.d("row_id", "new row id is" + newRowId);
        }

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] columns = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_TITLE,
                FeedEntry.COLUMN_NAME_CONTENT
        };



        Cursor c = db.query(
                FeedEntry.TABLE_NAME,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        if(c.moveToFirst()){
            int itemId = c.getInt(
                    c.getColumnIndexOrThrow(FeedEntry._ID));
            Log.d("id", "id is: " + itemId);

            String title1 = c.getString(
                    c.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TITLE));
            Log.d("title", "title is: " + title1);

            String content1 = c.getString(
                    c.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_CONTENT));
            Log.d("content", "content is: " + content1);

            //finally test result
            assertEquals(title, title1);
            assertEquals(content, content1);

        }else {
            fail("no values returned :(");
        }
        db.close();


    }
}
