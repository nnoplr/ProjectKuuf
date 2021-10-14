package com.novika.projectkuuf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class KuufDB {
    private DBHelper dbHelper;

    public KuufDB(Context ctx){
        dbHelper = new DBHelper(ctx);
    }

    //USER
    public void insertUser (DataUser dataUser){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.USER_NAME, dataUser.username);
        cv.put(DBHelper.USER_PASSWORD, dataUser.password);
        cv.put(DBHelper.USER_PHONE, dataUser.phoneNumber);
        cv.put(DBHelper.USER_GENDER, dataUser.gender);
        cv.put(DBHelper.USER_WALLET, dataUser.wn);
        cv.put(DBHelper.USER_DOB, dataUser.dateOfBirth);

        db.insert(DBHelper.TABLE_USERS, null, cv);
        db.close();
    }

    public void updateUserWallet (DataUser dataUser, int idUserDipilih){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DBHelper.USER_NAME, dataUser.username);
        cv.put(DBHelper.USER_PASSWORD, dataUser.password);
        cv.put(DBHelper.USER_PHONE, dataUser.phoneNumber);
        cv.put(DBHelper.USER_GENDER, dataUser.gender);
        cv.put(DBHelper.USER_WALLET, dataUser.wn);
        cv.put(DBHelper.USER_DOB, dataUser.dateOfBirth);

        String where = DBHelper.USER_ID + "=?";
        String[] whereArgs = {"" + idUserDipilih};

        db.update(DBHelper.TABLE_USERS, cv, where, whereArgs);
        db.close();
    }

    public DataUser getUser(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DBHelper.USER_ID + "=?";
        String[] selectionArgs = {"" + userId};

        Cursor cursor = db.query(DBHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);

        DataUser dataUser = null;

        if (cursor.moveToFirst()) {

            int idx_id = cursor.getColumnIndex(DBHelper.USER_ID);
            int idx_name = cursor.getColumnIndex(DBHelper.USER_NAME);
            int idx_password = cursor.getColumnIndex(DBHelper.USER_PASSWORD);
            int idx_phone = cursor.getColumnIndex(DBHelper.USER_PHONE);
            int idx_gender = cursor.getColumnIndex(DBHelper.USER_GENDER);
            int idx_wallet = cursor.getColumnIndex(DBHelper.USER_WALLET);
            int idx_dob = cursor.getColumnIndex(DBHelper.USER_DOB);

            dataUser = new DataUser(cursor.getInt(idx_id), cursor.getString(idx_name), cursor.getString(idx_password), cursor.getString(idx_phone), cursor.getString(idx_gender), cursor.getInt(idx_wallet), cursor.getString(idx_dob));
        }
        cursor.close();
        db.close();

        return dataUser;
    }

    public ArrayList<DataUser> getAllUsers(){

        ArrayList<DataUser> dataUserArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

        DataUser dataUser = null;

        if (cursor.moveToFirst()){
            do{

                int idx_id = cursor.getColumnIndex(DBHelper.USER_ID);
                int idx_name = cursor.getColumnIndex(DBHelper.USER_NAME);
                int idx_password = cursor.getColumnIndex(DBHelper.USER_PASSWORD);
                int idx_phone = cursor.getColumnIndex(DBHelper.USER_PHONE);
                int idx_gender = cursor.getColumnIndex(DBHelper.USER_GENDER);
                int idx_wallet = cursor.getColumnIndex(DBHelper.USER_WALLET);
                int idx_dob = cursor.getColumnIndex(DBHelper.USER_DOB);

                dataUser = new DataUser(cursor.getInt(idx_id), cursor.getString(idx_name), cursor.getString(idx_password), cursor.getString(idx_phone), cursor.getString(idx_gender), cursor.getInt(idx_wallet), cursor.getString(idx_dob));
                dataUserArrayList.add(dataUser);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dataUserArrayList;
    }

    public void insertProduct (Products products){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.PROD_NAME, products.name);
        cv.put(DBHelper.PROD_MIN, products.minPlayer);
        cv.put(DBHelper.PROD_MAX, products.maxPlayer);
        cv.put(DBHelper.PROD_PRICE, products.price);
        cv.put(DBHelper.PROD_LATITUDE, products.latitude);
        cv.put(DBHelper.PROD_LONGITUDE, products.longitude);

        db.insert(DBHelper.TABLE_PRODUCTS, null, cv);
        db.close();
    }

    public ArrayList<Products> getAllProducts(){

        ArrayList<Products> productsArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TABLE_PRODUCTS, null, null, null, null, null, null);

        Products products = null;

        if (cursor.moveToFirst()){

            do {
                int idx_id = cursor.getColumnIndex(DBHelper.PROD_ID);
                int idx_name = cursor.getColumnIndex(DBHelper.PROD_NAME);
                int idx_min = cursor.getColumnIndex(DBHelper.PROD_MIN);
                int idx_max = cursor.getColumnIndex(DBHelper.PROD_MAX);
                int idx_price = cursor.getColumnIndex(DBHelper.PROD_PRICE);
                int idx_latitude = cursor.getColumnIndex(DBHelper.PROD_LATITUDE);
                int idx_longitude = cursor.getColumnIndex(DBHelper.PROD_LONGITUDE);

                products = new Products(cursor.getInt(idx_id), cursor.getString(idx_name), cursor.getInt(idx_min), cursor.getInt(idx_max), cursor.getInt(idx_price), cursor.getDouble(idx_latitude), cursor.getDouble(idx_longitude));
                productsArrayList.add(products);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productsArrayList;
    }

    public Products getProduct (int prodId){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DBHelper.PROD_ID + "=?";
        String[] selectionArgs = {""+ prodId};

        Cursor cursor = db.query(DBHelper.TABLE_PRODUCTS, null, selection, selectionArgs, null, null, null);

        Products products = null;

        if(cursor.moveToFirst()){

            int idx_id = cursor.getColumnIndex(DBHelper.PROD_ID);
            int idx_name = cursor.getColumnIndex(DBHelper.PROD_NAME);
            int idx_min = cursor.getColumnIndex(DBHelper.PROD_MIN);
            int idx_max = cursor.getColumnIndex(DBHelper.PROD_MAX);
            int idx_price = cursor.getColumnIndex(DBHelper.PROD_PRICE);
            int idx_latitude = cursor.getColumnIndex(DBHelper.PROD_LATITUDE);
            int idx_longitude = cursor.getColumnIndex(DBHelper.PROD_LONGITUDE);

            products = new Products(cursor.getInt(idx_id), cursor.getString(idx_name), cursor.getInt(idx_min), cursor.getInt(idx_max), cursor.getInt(idx_price), cursor.getDouble(idx_latitude), cursor.getDouble(idx_longitude));
        }

        cursor.close();
        db.close();

        return products;
    }

    public void insertTransaction(Transaction transaction){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

//        cv.put(DBHelper.TRANS_ID, transaction.transId);
        cv.put(DBHelper.USER_T_ID, transaction.userId);
        cv.put(DBHelper.PROD_T_ID, transaction.prodId);
        cv.put(DBHelper.TRANS_DATE, transaction.date);

        db.insert(DBHelper.TABLE_TRANSACTIONS, null, cv);
        db.close();
    }

    public ArrayList<Transaction> getAllTransactions(int idd){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String joinTable = "SELECT " + DBHelper.TRANS_ID + " , "+
        DBHelper.PROD_NAME + " , " + DBHelper.PROD_PRICE + " , " +
        DBHelper.TRANS_DATE + " , " + DBHelper.PROD_LATITUDE + " , " +
        DBHelper.PROD_LONGITUDE +
        " FROM " + DBHelper.TABLE_TRANSACTIONS + " tr " +
        " JOIN "  + DBHelper.TABLE_USERS + " us " +
                "ON tr." + DBHelper.USER_T_ID + " = us." + DBHelper.USER_ID +
        " JOIN " + DBHelper.TABLE_PRODUCTS + " pr " +
                "ON tr." + DBHelper.PROD_T_ID + " = pr." + DBHelper.PROD_ID +
        " WHERE tr." + DBHelper.USER_T_ID + " = " + idd;

        Cursor cursor = db.rawQuery(joinTable, null);

        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        cursor.moveToFirst();

        if (cursor.getCount()>0 && cursor.moveToFirst()){
            while (!cursor.isAfterLast())
            {
                int idx_id = cursor.getColumnIndex(DBHelper.TRANS_ID);
//                int idx_user_id = cursor.getColumnIndex(DBHelper.USER_T_ID);
//                int idx_prod_id = cursor.getColumnIndex(DBHelper.PROD_T_ID);
                int idx_date = cursor.getColumnIndex(DBHelper.TRANS_DATE);
                int idx_prod_name = cursor.getColumnIndex(DBHelper.PROD_NAME);
                int idx_prod_price = cursor.getColumnIndex(DBHelper.PROD_PRICE);

//                transactionArrayList.add(new Transaction(cursor.getInt(idx_id), cursor.getInt(idx_user_id), cursor.getInt(idx_prod_id), cursor.getString(idx_date), cursor.getString(idx_prod_name), cursor.getInt(idx_prod_price)));

                Transaction transaction= new Transaction(cursor.getInt(idx_id), cursor.getString(idx_date), cursor.getString(idx_prod_name), cursor.getInt(idx_prod_price));
                transactionArrayList.add(transaction);

//                transactionArrayList.add(new Transaction(cursor.getString(idx_date), cursor.getString(idx_prod_name), cursor.getInt(idx_prod_price)));

                cursor.moveToNext();
            }
        }

        cursor.close();

        return transactionArrayList;
    }

    public Transaction getTransactions (int transId){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DBHelper.TRANS_ID + "=?";
        String[] selectionArgs = {""+ transId};

        Cursor cursor = db.query(DBHelper.TABLE_TRANSACTIONS, null, selection, selectionArgs, null, null, null);

        Transaction transaction = null;

        if(cursor.moveToFirst()){

            int idx_id = cursor.getColumnIndex(DBHelper.TRANS_ID);
            int idx_user_id = cursor.getColumnIndex(DBHelper.USER_T_ID);
            int idx_prod_id = cursor.getColumnIndex(DBHelper.PROD_T_ID);
            int idx_date = cursor.getColumnIndex(DBHelper.TRANS_DATE);
            int idx_prod_name = cursor.getColumnIndex(DBHelper.PROD_NAME);
            int idx_prod_price = cursor.getColumnIndex(DBHelper.PROD_PRICE);

            transaction = new Transaction(cursor.getInt(idx_id), cursor.getInt(idx_user_id), cursor.getInt(idx_prod_id), cursor.getString(idx_date), cursor.getString(idx_prod_name), cursor.getInt(idx_prod_price));
        }

        cursor.close();

        return transaction;
    }

    public void deleteTransaction(int idTransDipilih){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM " + DBHelper.TABLE_TRANSACTIONS+ " WHERE "+DBHelper.TRANS_ID+"=" + idTransDipilih);


    }

}
