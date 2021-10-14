package com.novika.projectkuuf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {

    ArrayList<Products> productlist = new ArrayList<>();

    RecyclerView rvProduct;
    KuufDB kuufDB;

    private final String KEYPROD = "KEYPROD";

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
        setContentView(R.layout.activity_store);

        ActionBar actionBar = getSupportActionBar();

        // tunjukkan back button di action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        rvProduct = findViewById(R.id.rvProduct);

        kuufDB = new KuufDB(this);
        ArrayList<Products> productsArrayList = kuufDB.getAllProducts();

        // Adapter Products
        ProductAdapter productAdapter = new ProductAdapter();
        productAdapter.setArrayListData(productsArrayList);

        if (productsArrayList.size() == 0) {
//            String url = "https://api.jsonbin.io/b/5eb51c6947a2266b1474d701/7";

//            UrlReader ur = new UrlReader();
//            String result = ur.getUrlReader();

            String jsonString = "{\"items\":[{\"name\":\"Exploding Kitten\",\"min_player\":2," +
                    "\"max_player\":5,\"price\":250000,\"created_at\":\"20/1/2017\",\"latitude\":\"-6.912035\"," +
                    "\"longitude\":\"106.265139\"},{\"name\":\"Card Against Humanity\",\"min_player\":2,\"max_player\":4," +
                    "\"price\":182500,\"created_at\":\"9/8/2014\",\"latitude\":\"-7.586037\",\"longitude\":\"108.126810\"},{\"name\":" +
                    "\"Bang Dice Game\",\"min_player\":3,\"max_player\":8,\"price\":355000,\"created_at\":\"1/12/2016\",\"latitude\":" +
                    "\"-5.345676\",\"longitude\":\"103.806584\"},{\"name\":\"Arkham Horror\",\"min_player\":3,\"max_player\":8,\"price\":" +
                    "250000,\"created_at\":\"2/1/2015\",\"latitude\":\"-3.743289\",\"longitude\":\"101.789556\"},{\"name\":\"The Dark Moon\"," +
                    "\"min_player\":2,\"max_player\":7,\"price\":560000,\"created_at\":\"27/10/2018\",\"latitude\":\"-6.782312\",\"longitude\":" +
                    "\"108.890254\"},{\"name\":\"Pandemic\",\"min_player\":2,\"max_player\":5,\"price\":1250000,\"created_at\":\"1/1/2013\",\"latitude" +
                    "\":\"1.816432\",\"longitude\":\"104.804334\"},{\"name\":\"The Werewolf Ultimate\",\"min_player\":5,\"max_player\":12,\"price\":325000," +
                    "\"created_at\":\"28/10/2014\",\"latitude\":\"-6.890323\",\"longitude\":\"106.632134\"}]}";

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray products = jsonObject.getJSONArray("items");

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject obj = products.getJSONObject(i);

                        String name = obj.getString("name");
                        int minPlayer = obj.getInt("min_player");
                        int maxPlayer = obj.getInt("max_player");
                        int prodPrice = obj.getInt("price");
                        String created = obj.getString("created_at");
                        String latitude = obj.getString("latitude");
                        String longitude = obj.getString("longitude");
                        double latitude1 = Float.parseFloat(latitude);
                        double longitude1 = Float.parseFloat(longitude);

                        Products products1 = new Products(i, name, minPlayer, maxPlayer, prodPrice, latitude1, longitude1);
                        productsArrayList.add(products1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        rvProduct.setAdapter(productAdapter);
        productAdapter.setArrayListData(productsArrayList);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("lifecycle", "onRestart");

    }
}