package com.example.inclass07;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddExpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AddExpFragment extends Fragment {
    String[] categories_selection={"Groceries","Invoice","Transportation","Shopping","Rent","Trips","Utilities","Other"};
    String ExpenseAmount,ExpenseName,ExpenseCategory;
    EditText category;
    EditText expenseName,expenseAmount;
    Button addExpenseButton,cancelExpButton;


    private OnFragmentInteractionListener mListener;

    public AddExpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(R.string.add_exp_button);

        return inflater.inflate(R.layout.fragment_add_exp, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        category=getActivity().findViewById(R.id.catSel_etext);
        expenseName=getActivity().findViewById(R.id.expName_etext);
        expenseAmount=getActivity().findViewById(R.id.amount_etext);
        addExpenseButton=getActivity().findViewById(R.id.add_exp_button);
        cancelExpButton=getActivity().findViewById(R.id.cancel_exp_button);

        getActivity().findViewById(R.id.catSel_etext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Choose Category")
                        .setSingleChoiceItems(categories_selection, categories_selection.length, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ListView lw = ((AlertDialog) dialogInterface).getListView();
                                Object checkedItem = lw.getAdapter().getItem(i);
                                category.setText(checkedItem.toString());

                            }
                        });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseFragment expenseFragment = new ExpenseFragment();
                Expense expense = new Expense();
                if ((expenseName.getText().toString()).equals("")) {
                    expenseName.setError("Please enter the expense name");
                    expenseName.requestFocus();

                } else if ((category.getText().toString()).equals("")) {
                    category.setError("Please choose a category");
                    category.requestFocus();
                } else if ((expenseAmount.getText().toString()).equals("")) {
                    expenseAmount.setError("Enter the amount");
                    expenseAmount.requestFocus();
                } else {
                    ExpenseName = expenseName.getText().toString();
                    ExpenseCategory = category.getText().toString();
                    ExpenseAmount = expenseAmount.getText().toString();
                    Date exp_date = new Date();
                    String strDateFormat = "MM/dd/YYYY";
                    DateFormat d1 = new SimpleDateFormat(strDateFormat);
                    String formattedDate = d1.format(exp_date);

                    expense.expName = ExpenseName;
                    expense.expAmount = ExpenseAmount;

                    Bundle bundle = new Bundle();
                    bundle.putString("EXP_NAME_KEY", ExpenseName);
                    bundle.putString("EXP_CAT_KEY", ExpenseCategory);
                    bundle.putString("EXP_AMT_KEY", ExpenseAmount);
                    bundle.putString("EXP_DATE_KEY", formattedDate);
                    bundle.putSerializable("EXP_TOTAL", expense);
                    //expenseFragment.getDataFromOther(bundle);
                    expenseFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.main_container,expenseFragment).commit();
                    Log.d("date", formattedDate);
                    Log.d("Expense values", "The values are " + ExpenseName + " " + ExpenseCategory + " " + ExpenseAmount);

                }
            }
        });



        cancelExpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getFragmentManager().beginTransaction().replace(R.id.main_container,new ExpenseFragment()).commit();
                /*FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack"+fm.popBackStackImmediate());
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                    //super.onBackPressed();
                }*/
            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
