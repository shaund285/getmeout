package com.team14.getmeout;

import java.util.Date;

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
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.budget_fragment_layout, container, false);
        
        //add button callback
        Button b = (Button) v.findViewById(R.id.budget_fragment_layout_go);
        b.setOnClickListener(this);
        //init default search mode
        b = (Button) v.findViewById(R.id.budget_fragment_mode);
        b.setOnClickListener(this);
		return v;
	}
	
	/**
	 * OnClickListener interface
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.budget_fragment_layout_go:
				this.toEventList();
				break;
			case R.id.budget_fragment_mode:
				this.changeMode(v);
				break;
		}
	}
	
	private void toEventList() {
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
				Date start = new Date();
				Date end = new Date();
				Button modeButton = (Button) this.getView().findViewById(R.id.budget_fragment_mode);
				String weekString = this.getActivity().getResources().getString(R.string.budget_fragment_mode_week);
				if (modeButton.getText().equals(weekString)) {
					//if serching for this week's events add 7 days
					end.setDate(start.getDay() + 7);
				}
				else {
					//if serching for today'sevents add 24h
					end.setDate(end.getDate() + 1);
				}
				//switch fragment
				Fragment listFragment = new EventListFragment(budget, start, end);
				budgetField.setText(start.toGMTString() + " " + end.toGMTString());
				FragmentManager fm = this.getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.fragment_container, listFragment);
				//ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.addToBackStack(null);
				ft.commit();
	}
	
	private void changeMode(View v) {
		Button modeButton = (Button) v.findViewById(R.id.budget_fragment_mode);
		String weekString = this.getActivity().getResources().getString(R.string.budget_fragment_mode_week);
		if (modeButton.getText().equals(weekString)) {
			modeButton.setText(R.string.budget_fragment_mode_today);
		}
		else {
			modeButton.setText(R.string.budget_fragment_mode_week);
		}
		
	}
}
