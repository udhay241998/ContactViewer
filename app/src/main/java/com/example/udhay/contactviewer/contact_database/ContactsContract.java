package com.example.udhay.contactviewer.contact_database;

import android.provider.BaseColumns;

public class ContactsContract {
    private ContactsContract(){}

    public class Contacts implements BaseColumns{

        public static final String TABLE_NAME = "contactEntry";
        public static final String COLUMN_NAME = "name";
        public static final String NUMBER_1 = "number_1";
        public static final String NUMBER_2 = "number_2";
        public static final String NUMBER_3 = "number_3";
        public static final String NUMBER_4 = "number_4";
        public static final String DEFAULT_NUMBER = "default_number";
    }

}
