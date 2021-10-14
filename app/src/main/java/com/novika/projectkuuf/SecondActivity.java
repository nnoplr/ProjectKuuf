package com.novika.projectkuuf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    EditText regisUsername, regisPassword, regisPhone, regisConfirmPassword;
    RadioGroup regisGroupGender;
    RadioButton selectedGender;
    CheckBox regisCheckbox;
    Button regisBtn2, loginBtn2, pickDateBtn;
    DatePicker picker;
    String tanggal, bulan, tahun;
    private final String KEY = "KEY";

    KuufDB kuufDB;
    ArrayList<DataUser> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        kuufDB = new KuufDB(this);

        regisUsername = findViewById(R.id.RegisIsiUsername);
        regisPassword = findViewById(R.id.RegisIsiPassword);
        regisPhone = findViewById(R.id.RegisIsiPhone);
        regisConfirmPassword = findViewById(R.id.RegisIsiConfirmPassword);
        regisGroupGender = findViewById(R.id.RadioGroupGender);
        regisCheckbox = findViewById(R.id.RegisCheckBox);
        regisBtn2 = findViewById(R.id.BtnRegister2);
        loginBtn2 = findViewById(R.id.BtnLogin2);
        pickDateBtn = findViewById(R.id.BtnPickDate);


        regisBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = regisUsername.getText().toString();
                String password = regisPassword.getText().toString();
                String phoneNumber = regisPhone.getText().toString();
                String gender, dateOfBirth;
                int countA = 0, countB = 0, countC = 0, countNumber=0;
                // Asumsi saya string alphanumeric adalah string gabungan huruf kapital, non-kapital,  dan angka;
                if (password.length() > 8) {
                    int temp = 0;
                    char a;
                    for (int N = 0; N < password.length(); N++) {
                        a = password.charAt(N);
                        temp = (int) a;
                        if (temp >= 65 && temp <= 90) {
                            countA++;
                        } else if (temp >= 97 && temp <= 122) {
                            countB++;
                        } else if (temp >= 48 && temp <= 57) {
                            countC++;
                        }
                    }
                }

                if(phoneNumber.length() >= 10 && phoneNumber.length() <= 12){
                    int temp = 0;
                    char a;
                    for (int N = 0; N < phoneNumber.length(); N++) {
                        a = phoneNumber.charAt(N);
                        temp = (int) a;
                        if (temp < 48 || temp > 57) {
                            countNumber++;
                        }
                    }
                }

//                if(username.equals("")){
//                    Toast.makeText(getApplicationContext(),"Username field must be filled", Toast.LENGTH_SHORT).show();
//                }

                if (username.length() < 6 || username.length() > 12) {
                    Toast.makeText(getApplicationContext(), "Username must be between 6 and 12 characters", Toast.LENGTH_SHORT).show();
                }
//                else if(password.equals("")){
//                    Toast.makeText(getApplicationContext(),"Password field must be filled", Toast.LENGTH_SHORT).show();
//                }
                else if (password.length() <= 8) {
                    Toast.makeText(getApplicationContext(), "Password must be more than 8", Toast.LENGTH_SHORT).show();
                }

                else if (countA == 0 || countB == 0 || countC == 0) {
                    Toast.makeText(getApplicationContext(), "Password must contains alphanumeric", Toast.LENGTH_SHORT).show();
                }

                else if (!regisConfirmPassword.getText().toString().equals(password)) {
                    Toast.makeText(getApplicationContext(), "Confirmation password must be the same with password", Toast.LENGTH_SHORT).show();
                }

                else if (phoneNumber.length() < 10 || phoneNumber.length() > 12) {
                    Toast.makeText(getApplicationContext(), "Phone number must be between 10 and 12 digits", Toast.LENGTH_SHORT).show();
                }
                else if (countNumber>0) {
                    Toast.makeText(getApplicationContext(), "Phone number must contains only numbers", Toast.LENGTH_SHORT).show();
                }

                //DOB
                else if (tanggal == null || bulan == null || tahun == null) {
                    Toast.makeText(getApplicationContext(), "Date of Birth must be filled", Toast.LENGTH_SHORT).show();
                }

//                else if(phoneNumber.equals("")){
//                    Toast.makeText(getApplicationContext(),"Phone Number field must be filled", Toast.LENGTH_SHORT).show();
//                }
                else if (selectedGender == null) {
                    Toast.makeText(getApplicationContext(), "Gender must be selected", Toast.LENGTH_SHORT).show();
                }
                else if (!regisCheckbox.isChecked()) {
                    Toast.makeText(getApplicationContext(), "User must agree for terms and condition", Toast.LENGTH_SHORT).show();
                }
                else {
                    dateOfBirth = tanggal + " - " + bulan + " - " + tahun;
                    gender = selectedGender.getText().toString();
                    int id = userList.size() + 1;
                    DataUser data = new DataUser(id, username, password, phoneNumber,  gender, 0, dateOfBirth);
                    userList.add(data);


                    DataUser dataUser = new DataUser();
                    dataUser.username = username;
                    dataUser.password = password;
                    dataUser.phoneNumber = phoneNumber;
                    dataUser.gender = gender;
                    dataUser.wn = 0;
                    dataUser.dateOfBirth = dateOfBirth;

                    kuufDB.insertUser(dataUser);

                    Intent intent = new Intent(SecondActivity.this, MainActivity.class);

                    Toast.makeText(getApplicationContext(), "Registration succeed", Toast.LENGTH_SHORT).show();

                    finish();
                    startActivity(intent);
                }
            }
        });


        regisGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedGender = group.findViewById(checkedId);
            }
        });

        loginBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);

                finish();
                startActivity(intent);
            }
        });

        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                View view = getLayoutInflater().inflate(R.layout.date_picker, null);
                picker = view.findViewById(R.id.datePicker);
                builder.setView(view);
                builder.setTitle("Date of Birth");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tanggal = String.valueOf(picker.getDayOfMonth());
                        bulan = String.valueOf(picker.getMonth() + 1);
                        tahun = String.valueOf(picker.getYear());
                        Toast.makeText(getApplicationContext(), tanggal + " - " + bulan + " - " + tahun, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }
}
