package com.example.udhay.contactviewer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DetailContactActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        Intent startIntent = getIntent();
        String name =startIntent.getStringExtra("name");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Cursor cursor = getContentResolver().query(MainActivity.contactUri , new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER} ,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+"=?" , new String[]{name} ,
                null);
        Toast.makeText(this , "got the number  :" + cursor.getCount() , Toast.LENGTH_LONG).show();
        mRecyclerView = findViewById(R.id.contact_numbers);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter adapter = new customAdapter(this , cursor);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cursor.moveToFirst();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
class customAdapter extends RecyclerView.Adapter<customAdapter.NumberViewHolder>{

    Cursor mCursor;
    Context mContext;
    public customAdapter(Context context , Cursor cursor){
        mCursor = cursor;
        mContext = context;
    }
    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_contact_number , parent , false);

        return  new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.getNumber().setText(mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        final NumberViewHolder temoHolder = holder;
        holder.getNumber().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String contactNumber = temoHolder.getNumber().getText().toString();
                Toast.makeText(mContext , contactNumber , Toast.LENGTH_SHORT ).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {
        public TextView number;
        public NumberViewHolder(View view){
            super(view);
            number = view.findViewById(R.id.detail_number);

        }

        public TextView getNumber() {
            return number;
        }
    }
}
