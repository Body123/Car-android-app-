package com.example.products;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {
    public static final String DATABASE_NAME = "cars.db";
    public static final String tblName = "car";
    public static final String idColumn = "id";
    public static final String modelColumn = "model";
    public static final String colorColumn = "color";
    public static final String descriptionColumn = "description";
    public static final String imageColumn = "image";
    public static final String destanceColumn = "density";

        private static final int DATABASE_VERSION = 1;
        public MyDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

}
