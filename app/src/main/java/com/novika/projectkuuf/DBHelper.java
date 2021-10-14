package com.novika.projectkuuf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DBKuuf";
    private static final int DB_VERSION = 1;

    //Customer's data
    public static final String TABLE_USERS = "User";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userUsername";
    public static final String USER_PASSWORD = "userPassword";
    public static final String USER_PHONE = "userPhone";
    public static final String USER_GENDER = "userGender";
    public static final String USER_WALLET = "userWallet";
    public static final String USER_DOB = "userDOB";

    private static String CREATE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USER_NAME + " TEXT," +
            USER_PASSWORD + " TEXT," +
            USER_PHONE + " TEXT," +
            USER_GENDER + " TEXT," +
            USER_WALLET + " INTEGER," +
            USER_DOB + " TEXT)";

    private static final String DROP_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;

    //----------------------

    //Product's data
    public static final String TABLE_PRODUCTS = "Product";
    public static final String PROD_ID = "prodId";
    public static final String PROD_NAME = "prodName";
    public static final String PROD_MIN = "prodMin";
    public static final String PROD_MAX = "prodMax";
    public static final String PROD_PRICE = "prodPrice";
    public static final String PROD_LATITUDE = "prodLatitude";
    public static final String PROD_LONGITUDE = "prodLongitude";

    private static String CREATE_PRODUCTS = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCTS + " (" +
            PROD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PROD_NAME + " TEXT," +
            PROD_MIN + " INTEGER," +
            PROD_MAX + " INTEGER," +
            PROD_PRICE + " INTEGER," +
            PROD_LATITUDE + " REAL," +
            PROD_LONGITUDE + " REAL)";

    private static final String DROP_PRODUCTS = "DROP TABLE IF EXISTS " + TABLE_PRODUCTS;

    private static String INSERT_PRODUCTS1 = "INSERT INTO " + TABLE_PRODUCTS +
            " VALUES (1, \"Exploding Kitten\", 2, 5, 250000, -6.912035, 106.265139)";
    private static String INSERT_PRODUCTS2 = "INSERT INTO " + TABLE_PRODUCTS +
            " VALUES (2, \"Card Against Humanity\", 2, 4, 182500, -7.586037, 106.265139)";
    private static String INSERT_PRODUCTS3 = "INSERT INTO " + TABLE_PRODUCTS +
            " VALUES (3, \"Bang Dice Game\", 3, 8, 355000, -5.345676, 103.806584)";
    private static String INSERT_PRODUCTS4 = "INSERT INTO " + TABLE_PRODUCTS +
            " VALUES (4, \"Arkham Horror\", 3, 8, 250000, -6.912035, 101.789556)";
    private static String INSERT_PRODUCTS5 = "INSERT INTO " + TABLE_PRODUCTS +
            " VALUES (5, \"The Dark Moon\", 2, 7, 560000, -6.782312, 108.890254)";
    private static String INSERT_PRODUCTS6 = "INSERT INTO " + TABLE_PRODUCTS +
            " VALUES (6, \"Pandemic\", 2, 5, 1250000, -6.912035, 104.804334)";
    private static String INSERT_PRODUCTS7 = "INSERT INTO " + TABLE_PRODUCTS +
            " VALUES (7, \"The Werewolf Ultimate\", 5, 12, 325000, -6.890323, 106.632134)";

    //----------------------

    //Transaction's data
    public static final String TABLE_TRANSACTIONS = "Trans";
    public static final String TRANS_ID = "transId";
    public static final String USER_T_ID = "userId";
    public static final String PROD_T_ID = "prodId";
    public static final String TRANS_DATE = "transDate";

    private static String CREATE_TRANSACTIONS = "CREATE TABLE IF NOT EXISTS " + TABLE_TRANSACTIONS + " (" +
            USER_T_ID + " INTEGER NOT NULL," +
            PROD_T_ID + " INTEGER NOT NULL," +
            TRANS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TRANS_DATE + " TEXT," +
            " FOREIGN KEY (" + USER_T_ID + ")" + //fk custId
            " REFERENCES " + TABLE_USERS +
            " (" + USER_ID + ")," +
            " FOREIGN KEY (" + PROD_T_ID + ")" + //fk prodId
            " REFERENCES " + TABLE_PRODUCTS +
            " (" + PROD_ID + "))";


    private static final String DROP_TRANSACTIONS = "DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS;


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_PRODUCTS);
        db.execSQL(CREATE_TRANSACTIONS);
        db.execSQL(INSERT_PRODUCTS1);
        db.execSQL(INSERT_PRODUCTS2);
        db.execSQL(INSERT_PRODUCTS3);
        db.execSQL(INSERT_PRODUCTS4);
        db.execSQL(INSERT_PRODUCTS5);
        db.execSQL(INSERT_PRODUCTS6);
        db.execSQL(INSERT_PRODUCTS7);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USERS);
        db.execSQL(DROP_PRODUCTS);
        db.execSQL(DROP_TRANSACTIONS);
        onCreate(db);
    }
}
