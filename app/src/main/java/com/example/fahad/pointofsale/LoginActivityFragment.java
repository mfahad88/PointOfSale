package com.example.fahad.pointofsale;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {
    Button btn;
    View view;
    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        final LinearLayout layout=(LinearLayout)view.findViewById(R.id.fragment_container);
        btn=(Button)view.findViewById(R.id.button);
        try {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getContext(),MenuFragment.class);
                    startActivity(i);
                }
            });
        }catch (Exception e){
            Log.e("Error>>>>>>>>>",e.getMessage());
        }
        return view;
    }

}
