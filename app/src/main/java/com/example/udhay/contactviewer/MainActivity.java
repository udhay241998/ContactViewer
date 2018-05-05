package com.example.udhay.contactviewer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
private Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
private Cursor contactCursor;
private static final int LOADER_ID = 100;
private RecyclerView contactRecyclerView;
private ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.READ_CONTACTS}, 24);

        contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        contactRecyclerView = findViewById(R.id.contact_recycle);
        contactRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        LoaderManager manager = getSupportLoaderManager();
        Loader<Cursor> loader = manager.getLoader(LOADER_ID);
        if(loader == null){
            manager.initLoader(LOADER_ID , null ,this).forceLoad();
        }else {
            manager.restartLoader(LOADER_ID, null, this).forceLoad();
        }



    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.v("loader Creation" , "Loader is Created");
        return new ContactAsyncTask(this , contactUri);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

Log.v("loader finished" , "inside loader finished");
        contactCursor = data;

        contactAdapter = new ContactAdapter(data);
        contactRecyclerView.setItemAnimator(new DefaultItemAnimator());
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        contactRecyclerView.setAdapter(contactAdapter);

        Log.v("display" , Integer.toString(contactCursor.getCount()));


    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        getSupportLoaderManager().restartLoader(LOADER_ID , null , this);
    }

}
