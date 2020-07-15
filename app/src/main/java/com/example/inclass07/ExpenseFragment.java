package com.example.inclass07;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpenseFragment extends Fragment {
public static Expense e=null;
     public static ArrayList<Expense> displayList=new ArrayList<>();
     Exp_adapter t1;
     ListView expdetailsList;
     TextView nodata;


    private OnFragmentInteractionListener mListener;

    public ExpenseFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(R.string.exp_app);
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
if(getArguments()!=null&&getArguments().containsKey("EXP_TOTAL")){
   Expense expense=new Expense();
   expense.expName=getArguments().getString("EXP_NAME_KEY");
    expense.expCat=getArguments().getString("EXP_CAT_KEY");
    expense.expAmount=getArguments().getString("EXP_AMT_KEY");
    expense.expDate=getArguments().getString("EXP_DATE_KEY");
    displayList.add(expense);
    Log.d("catttt",expense.expCat);

}
        nodata = view.findViewById(R.id.expense_text);
        expdetailsList = view.findViewById(R.id.mainListView);
if (displayList.size()==0){


    Log.d("sanjuuu",expdetailsList.toString());
    expdetailsList.setVisibility(View.INVISIBLE);
}
else{
    t1 = new Exp_adapter(getActivity(),R.layout.exp_details,displayList);
    expdetailsList.setAdapter(t1);
}

        return view;
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
  public void getDataFromOther(Bundle bundle)
        {
            String value1= bundle.getString("EXP_NAME_KEY");
            Log.d("check frags",value1);
            Expense expense = (Expense) bundle.getSerializable("EXP_TOTAL");
            //displayList.add(bundle.getString("EXP_AMT_KEY"));
            displayList.add(expense);



        }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Button addButton=getActivity().findViewById(R.id.add_button);
        expdetailsList=getActivity().findViewById(R.id.mainListView);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new AddExpFragment(),"AddExpFragment").commit();
            }
        });

        if(displayList.size()>0) {
            nodata.setVisibility(View.INVISIBLE);
            expdetailsList.setVisibility(View.VISIBLE);
        }
        expdetailsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                displayList.remove(position);
                Log.d("display_size",String.valueOf(displayList.size()));
                t1.notifyDataSetChanged();
                Toast.makeText(getContext(),"Expense deleted",Toast.LENGTH_SHORT).show();
                if(displayList.size()==0){
                    nodata.setVisibility(View.VISIBLE);
                    expdetailsList.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        expdetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense expense = displayList.get(position);
                ShowExpenseFragment showExpenseFragment=new ShowExpenseFragment();

                Bundle bundle=new Bundle();
                bundle.putSerializable("EXP_KEY",expense);
                showExpenseFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container,showExpenseFragment,"ShowExpense").commit();

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
