package com.novika.projectkuuf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransAdapter extends RecyclerView.Adapter<TransAdapter.ViewHolder> {


    Context ctx;

    ArrayList<Transaction> translist;
    ArrayList<Transaction> transactionArrayList = new ArrayList<>();

    KuufDB kuufDB;

    private final String KEYPRODTRANS = "KEYPRODTRANS";



    @NonNull
    @Override
    public TransAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflater
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.transactions_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransAdapter.ViewHolder holder, int position) {

        //inisialisasi data
        kuufDB = new KuufDB(ctx);
        holder.tvName.setText(transactionArrayList.get(position).getProdName());
        holder.tvDate.setText(transactionArrayList.get(position).getDate());
        holder.tvPrice.setText(""+transactionArrayList.get(position).getProdPrice());


        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                transactionArrayList.remove(position);

                //Simpan Transaction
                Intent intent = new Intent(ctx, HomeActivity.class);

                if (transactionArrayList!=null){
                    kuufDB.deleteTransaction(transactionArrayList.get(position).getTransId());
//                    Toast.makeText(ctx,String.valueOf(transactionArrayList.get(position).getTransId()),Toast.LENGTH_SHORT).show();

                }

                SharedPreferences idSelUser = PreferenceManager.getDefaultSharedPreferences(ctx);
                int idd = idSelUser.getInt("SELECTEDID", 0);
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }

    public void setArrayListData(ArrayList<Transaction> transactionArrayList){
        this.transactionArrayList = transactionArrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice, tvDate;
        Button deleteBtn;
        FrameLayout frTrans;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.textName);
            tvPrice = itemView.findViewById(R.id.textPrice);
            tvDate = itemView.findViewById(R.id.textTransDate);
            deleteBtn = itemView.findViewById(R.id.btnDelete);
            frTrans = itemView.findViewById(R.id.frTrans);
        }
    }

}
