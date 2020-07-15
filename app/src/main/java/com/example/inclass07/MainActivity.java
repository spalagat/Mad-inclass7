package com.example.inclass07;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ExpenseFragment.OnFragmentInteractionListener,AddExpFragment.OnFragmentInteractionListener {

    String[] categories_selection={"Groceries","Invoice","Transportation","Shopping","Rent","Trips","Utilities","Other"};
    private static final String EXP_NAME_KEY="Expense Name";
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,new ExpenseFragment(),"ExpenseFragment").commit();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        Log.d("backstack count", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
        {
            getSupportFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }

    }

    public  void getCategory()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose a Keyword")
                .setSingleChoiceItems(categories_selection, categories_selection.length, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListView lw = ((AlertDialog) dialogInterface).getListView();
                        Object checkedItem = lw.getAdapter().getItem(i);
                        if(isConnected()) {
                            //e1.setText(checkedItem.toString());
                            //Toast.makeText(MainActivity.this, e1.getText().toString(), Toast.LENGTH_SHORT).show();
                            //get_image_url();
                        }

                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
