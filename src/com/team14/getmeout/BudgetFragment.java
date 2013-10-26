package com.team14.getmeout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class BudgetFragment extends Fragment implements OnClickListener{
	
	public static enum PlanningMode {
		PLANNING_NOW,
		PLANNING_WEEK,
		PLANNING_TODAY
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.budget_fragment_layout, container, false);
        
        //add button callback
        Button b = (Button) v.findViewById(R.id.budget_fragment_layout_go);
        b.setOnClickListener(this);
        
		return v;
	}
	
	/**
	 * OnClickListener interface
	 */
	@Override
	public void onClick(View v) {
		//move to list fragment if a good amount of money have been inserted in the 
		//text field
		//get text field info as float
		float budget;
		EditText budgetField = (EditText) this.getView().findViewById(R.id.budget_fragment_budgetText);
		try {
			budget = Float.valueOf(budgetField.getText().toString());
		}
		catch (NumberFormatException e) {
			//invalid number format, notify with a popup
			budgetField.setText("Invalid Number");
			return;
		}
		//get planning mode
		
		Fragment listFragment = new EventListFragment(budget);
		FragmentManager fm = this.getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fragment_container, listFragment);
		ft.commit();	
	}
}
