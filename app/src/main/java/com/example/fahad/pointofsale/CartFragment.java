package com.example.fahad.pointofsale;


import android.hardware.camera2.DngCreator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fahad.pointofsale.Db.DbHelper;

import java.util.List;

import dao.SalesTable;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment{
    private TableLayout tbl_Product;
    private TextView totalAmt;
    private View view;


    public CartFragment(){

    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(view.getContext(), "onResume", Toast.LENGTH_SHORT).show();
        new getSales().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_cart, container, false);
        tbl_Product=(TableLayout)view.findViewById(R.id.table_Product);
        totalAmt=(TextView)view.findViewById(R.id.textView_Total);
        //totalAmt.setText("0.00");

        return view;
    }

    public void fillTable(String productName,String Qty,String unitPrice){
        TableRow tr=new TableRow(view.getContext());
        TextView product_tv=new TextView(view.getContext());
        TextView product_qty=new TextView(view.getContext());
        TextView product_unitPrice=new TextView(view.getContext());
        product_tv.setText(productName);
        product_qty.setText(Qty);
        product_unitPrice.setText(unitPrice);
        TableRow.LayoutParams trParams =new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
        trParams.setMargins(45,0,45,0);
        product_tv.setLayoutParams(trParams);
        product_qty.setLayoutParams(trParams);
        product_unitPrice.setLayoutParams(trParams);
        tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        tr.addView(product_tv);
        tr.addView(product_qty);
        tr.addView(product_unitPrice);
        tbl_Product.addView(tr);
    }


    private class getSales extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DbHelper dbHelper=new DbHelper(view.getContext());
            try {
                for (int i = 0; i < dbHelper.getSales().size(); i++) {
                    fillTable(dbHelper.getSales().get(i).getProductName(), dbHelper.getSales().get(i).getQuantity(), dbHelper.getSales().get(i).getPrice());
                }
            }catch (Exception e){
                Log.e("Error",e.getMessage());
            }
        }
    }
}
