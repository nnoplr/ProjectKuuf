package com.novika.projectkuuf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private boolean tes;
    private final String KEY = "KEY";
    ArrayList<Products> productlist = new ArrayList<>();
    ArrayList<Transaction> translist = new ArrayList<>();
    ArrayList<DataUser> userList = new ArrayList<>();

    KuufDB kuufDB;

    RecyclerView rvTrans;
    TextView greetings, walletNominal, noTrans;
    private final String KEYPROD = "KEYPROD";
    private final String KEYPRODTRANS = "KEYPRODTRANS";


    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.Home:
                intent = new Intent(this,HomeActivity.class);
                finish();
                break;
            case R.id.Store:
                intent = new Intent(this,StoreActivity.class);
//                finish();
                SharedPreferences test = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
                tes = test.getBoolean("TES", true);

                if (tes==false){
                    finish();
                }
                break;
            case R.id.Profile:
                intent = new Intent(this, ProfileActivity.class);
                break;
            case R.id.Logout:
                intent = new Intent(this,MainActivity.class);
                finish();
                break;
        }
        if(intent!=null){
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.i("lifecycle", "onHomeCreate:");

        tes = true;
        SharedPreferences.Editor test = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).edit();
        test.putBoolean("TES", tes);
        test.apply();

        kuufDB = new KuufDB(this);


        //Ambil data user yang login
        SharedPreferences idSelUser = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        int idd = idSelUser.getInt("SELECTEDID", 0);

        DataUser dataUser = kuufDB.getUser(idd);

        //ID user disimpan agar bisa digunakan di activity lain
        SharedPreferences.Editor sendUserID = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).edit();
        sendUserID.putInt("SELECTEDID", idd);
        sendUserID.apply();

        greetings = findViewById(R.id.greetings);
        walletNominal = findViewById(R.id.walletNominal);
        noTrans = findViewById(R.id.noTrans);
        greetings.setText(String.valueOf(dataUser.getUsername()));
        walletNominal.setText(String.valueOf(dataUser.getWn()));


        rvTrans = findViewById(R.id.rvTrans);

        // Adapter History Transaction

        TransAdapter transAdapter = new TransAdapter();
        ArrayList<Transaction> transactionArrayList = kuufDB.getAllTransactions(idd);
        transAdapter.setArrayListData(transactionArrayList);

        if (transactionArrayList.size()==0){
            noTrans.setText("All History Transactions\nThere is no transaction");
        }

        rvTrans.setAdapter(transAdapter);
        rvTrans.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("lifecycle", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle", "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();



        Log.i("lifecycle", "onRestart");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecycle", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifecycle", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle", "onDestroy");
    }
}
