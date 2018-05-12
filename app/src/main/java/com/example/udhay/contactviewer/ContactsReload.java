package com.example.udhay.contactviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.udhay.contactviewer.contact_database.ContactOpenHelper;
import com.example.udhay.contactviewer.contact_database.ContactsContract;

public class ContactsReload extends AsyncTask<Cursor , Integer , Cursor> {
    Context mContext;
    public ContactsReload(Context context){
        mContext = context;
    }
    @Override
    protected Cursor doInBackground(Cursor... cursors) {
        Cursor contactCursor = cursors[0];
        ContentValues contactValue = new ContentValues();
        ContactOpenHelper openHelper = new ContactOpenHelper(mContext);
        SQLiteDatabase database = openHelper.getWritableDatabase();

        for(int i = 0 ; i <contactCursor.getCount() ; i++){

            contactCursor.moveToPosition(i);

            String name = contactCursor.getString(contactCursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contactValue.put(ContactsContract.Contacts.COLUMN_NAME, name);


            database.insert(ContactsContract.Contacts.TABLE_NAME, null, contactValue);

            contactValue.clear();

        }


        database = openHelper.getReadableDatabase();
        Cursor cursor = database.query(ContactsContract.Contacts.TABLE_NAME ,
                new String[]{ContactsContract.Contacts.COLUMN_NAME} ,
                null , null ,null , null ,null);

        return cursor;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        MainActivity.contactAdapter.swapCursor(cursor);
        MainActivity.contactAdapter.notifyDataSetChanged();
        Toast.makeText(mContext , "Contacts present" + cursor.getCount() , Toast.LENGTH_SHORT).show();
    }


}
