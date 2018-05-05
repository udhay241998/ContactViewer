package com.example.udhay.contactviewer;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.contactViewHolder> {

    private Cursor contactCursor;
    public ContactAdapter(Cursor cursor){
        contactCursor = cursor;
    }
    @NonNull
    @Override
    public contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_contact , parent  ,false);
        return new contactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull contactViewHolder holder, int position) {

        try {
            if (contactCursor.moveToPosition(position)) {
                holder.contactName.setText(contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                holder.contactNumber.setText(contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                holder.lineView.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception ex){
            holder.contactNumber.setText("No number");
        }

    }

    @Override
    public int getItemCount() {
        return contactCursor.getCount();
    }

    public class contactViewHolder extends RecyclerView.ViewHolder {
        private TextView contactName;
        private TextView contactNumber;
        private View lineView;
        public contactViewHolder(View view){
            super(view);
            contactName = view.findViewById(R.id.contact_name);
            contactNumber = view.findViewById(R.id.contact_number);
            lineView = view.findViewById(R.id.view);
        }

        public String getContactNumber() {
            return contactNumber.getText().toString();
        }

        public String getContactName() {
            return contactName.getText().toString();
        }
    }
}
