package com.novika.projectkuuf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class ProductDetailActivity extends AppCompatActivity {

    TextView tvName, tvMinPlay, tvMaxPlay, tvPrice;
    Button btnBuy, btnLocation;
    private final String KEYPROD = "KEYPROD";
    private final String KEYCHOSEN = "KEYCHOSEN";
    private final String KEYPRODTRANS = "KEYPRODTRANS";
    private final String KEY = "KEY";
    KuufDB kuufDB;
    SmsManager smsManager;


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
        setContentView(R.layout.activity_product_detail);

        ActionBar actionBar = getSupportActionBar();

        // tunjukkan back button di action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvName = findViewById(R.id.textName);
        tvMinPlay = findViewById(R.id.textMinPlay);
        tvMaxPlay = findViewById(R.id.textMaxPlay);
        tvPrice = findViewById(R.id.textProdPrice);
        btnBuy = findViewById(R.id.btnBuy);
        btnLocation = findViewById(R.id.btnShowLocation);
        smsManager = SmsManager.getDefault();
        
        kuufDB = new KuufDB(this);
        
        permission();
        
        //Dapatin ID user yang login
        SharedPreferences idSelUser = PreferenceManager.getDefaultSharedPreferences(ProductDetailActivity.this);
        int idd = idSelUser.getInt("SELECTEDID", 0);


        //Dapat id produk yang ditunjukin
        SharedPreferences spp1 = PreferenceManager.getDefaultSharedPreferences(ProductDetailActivity.this);
        int chosenProdId = spp1.getInt(KEYCHOSEN + "ID", 0);

        Products products = kuufDB.getProduct(chosenProdId);
        DataUser dataUser = kuufDB.getUser(idd);

        tvName.setText(String.valueOf(products.getName()));
        tvMinPlay.setText(String.valueOf(products.getMinPlayer()));
        tvMaxPlay.setText(String.valueOf(products.getMaxPlayer()));
        tvPrice.setText(String.valueOf(products.getPrice()));

        Calendar calendar = Calendar.getInstance();
        String datee = DateFormat.getDateInstance().format(calendar.getTime());


        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ProductDetailActivity.this, MapsActivity.class);

                intent.putExtra("LATITUDE", products.getLatitude());
                intent.putExtra("LONGITUDE", products.getLongitude());

                finish();
                startActivity(intent);
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (products.getPrice()>dataUser.getWn()){
                    Toast.makeText(getApplicationContext(),"Your wallet is less than product price", Toast.LENGTH_SHORT).show();
                }

                else {

                    Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);

//                    Transaction data = new Transaction(idTrans + 1, String.valueOf(productlist.get(chosenProdId).getName()), datee, productlist.get(chosenProdId).getPrice());
//                    translist.add(data);

                    Transaction transaction = new Transaction();
                    transaction.prodName = tvName.getText().toString();
                    transaction.prodPrice = Integer.parseInt(tvPrice.getText().toString());
                    transaction.date = datee;
                    transaction.prodId = chosenProdId;
                    transaction.userId = idd;

                    kuufDB.insertTransaction(transaction);


                    int nominall = dataUser.getWn();
                    int price = products.getPrice();

                    nominall = nominall-price;

//                    userList.get(idd-1).setWn(nominal);

                    dataUser.wn = nominall;

                    kuufDB.updateUserWallet(dataUser, idd);
//                    Toast.makeText(getApplicationContext(), String.valueOf(chosenProdId), Toast.LENGTH_SHORT).show();


                    String message = "Transaction success!\n" +
                            "Name: " + dataUser.getUsername() +"\n"+
                            "Product name: " + products.getName() + "\n"+
                            "Product price: " + products.getPrice() + "\n"+
                            "Enjoy your game!";

                    smsManager.sendTextMessage(dataUser.getPhoneNumber(), null, message, null, null);


                    finish();
                    startActivity(intent);
                }
            }
        });

    }

    void permission(){

        int sendPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (sendPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        int receivePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (receivePermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }

        int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if (readPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 1);
        }

    }

}