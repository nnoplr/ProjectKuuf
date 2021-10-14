package com.novika.projectkuuf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername, tvGender, tvDOB, tvPhone, tvNominal;
    RadioGroup rgTopUp;
    RadioButton selectedTopUp;
    EditText etPassword;
    Button btnConfirm;
    private final String KEY = "KEY";
    ArrayList<DataUser> userList = new ArrayList<>();
    ArrayList<DataUser> dataUserArrayList = new ArrayList<>();

    KuufDB kuufDB;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ActionBar actionBar = getSupportActionBar();

        // tunjukkan back button di action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Log.i("lifecycle", "onProfileCreate:");

        tvUsername = findViewById(R.id.textUsername);
        tvGender = findViewById(R.id.textUserGender);
        tvDOB = findViewById(R.id.textUserDOB);
        tvPhone = findViewById(R.id.textUserPhone);
        tvNominal = findViewById(R.id.textUserWallet);
        etPassword = findViewById(R.id.etPassword);
        btnConfirm = findViewById(R.id.confirmBtn);
        rgTopUp = findViewById(R.id.RadioGroupTopUp);


        //Ambil data user yang login
        SharedPreferences idSelUser = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        int idd = idSelUser.getInt("SELECTEDID", 0);

        kuufDB = new KuufDB(this);

        DataUser dataUser = kuufDB.getUser(idd);


        tvUsername.setText(dataUser.getUsername());
        tvGender.setText(dataUser.getGender());
        tvDOB.setText(dataUser.getDateOfBirth());
        tvPhone.setText(dataUser.getPhoneNumber());
        tvNominal.setText(String.valueOf(dataUser.getWn()));


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = etPassword.getText().toString();
                int nominalAwal = dataUser.getWn();
                int nominalBeli, nominal;


                if (selectedTopUp == null) {
                    Toast.makeText(getApplicationContext(), "Top up nominal must be selected", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(dataUser.getPassword())){
                    Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                }
                else{

                    String topUp = selectedTopUp.getText().toString();
                    if (topUp.equals("Rp.250.000")){
                        nominalBeli = 250000;
                    }
                    else if (topUp.equals("Rp.500.000")){
                        nominalBeli = 500000;
                    }
                    else if (topUp.equals("Rp.1.000.000")){
                        nominalBeli = 1000000;
                    }
                    else{
                        nominalBeli = 0;
                    }

                    nominal = nominalBeli + nominalAwal;

                    Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);

                    String pw = dataUser.getPassword();

                    DataUser dataUserr = kuufDB.getUser(idd);
                    dataUserr.username = tvUsername.getText().toString();
                    dataUserr.password = pw;
                    dataUserr.gender = tvGender.getText().toString();
                    dataUserr.dateOfBirth = tvDOB.getText().toString();
                    dataUserr.phoneNumber = tvPhone.getText().toString();
                    dataUserr.wn = Integer.parseInt(String.valueOf(nominal));

                    kuufDB.updateUserWallet(dataUserr, idd);

                    finish();
                    startActivity(intent);
                }
            }
        });



        rgTopUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedTopUp = group.findViewById(checkedId);
            }
        });


    }
}