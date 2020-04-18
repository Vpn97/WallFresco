package com.apkzube.wallfresco.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

public class DataStorage {
    //dedclration
    SharedPreferences pref;
    SharedPreferences.Editor DataWriter;
    Context ctx;
    String FileName;

    public final static int INTEGER = 1;
    public final static int FLOAT = 2;
    public final static int STRING = 3;
    public final static int LONG = 4;
    public final static int BOOLEAN = 5;


    public DataStorage(Context ctx, String FileName) {
        this.ctx = ctx;
        this.FileName = FileName;
        pref = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        DataWriter = pref.edit();
    }

    //some overloaded methods
    public void write(String key, int value) {
        DataWriter.putInt(key, value);
        DataWriter.commit();
    }

    public void write(String key, String value) {
        DataWriter.putString(key, value);
        DataWriter.commit();
    }

    public void write(String key, float value) {
        DataWriter.putFloat(key, value);
        DataWriter.commit();
    }

    public void write(String key, long value) {
        DataWriter.putLong(key, value);
        DataWriter.commit();
    }


    public void write(String key, boolean value) {
        DataWriter.putBoolean(key, value);
        DataWriter.commit();
    }

    public Object read(String key, int DataType) {
        Object temp = null;
        if (DataType == INTEGER) {
            temp = pref.getInt(key, 0);
        } else if (DataType == FLOAT) {
            temp = pref.getFloat(key, 0.0f);
        } else if (DataType == LONG) {
            temp = pref.getLong(key, 0);
        } else if (DataType == STRING) {
            temp = pref.getString(key, "");
        } else if (DataType == BOOLEAN) {
            temp = pref.getBoolean(key, false);
        }
        return temp;
    }


    public HashSet<String> readSet(String key) {
        return new HashSet<String>(pref.getStringSet(key, new HashSet<String>()));
    }

    public void writeSet(String key, HashSet<String> value) {
        DataWriter.putStringSet(key, value);
        DataWriter.commit();
    }

    public void deletedDataFromKey(String key){
        DataWriter.remove(key).apply();
    }

    public void clearAllData(){
        DataWriter.clear().commit();
    }

}