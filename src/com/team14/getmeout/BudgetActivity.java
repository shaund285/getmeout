package com.team14.getmeout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BudgetActivity extends Activity {

	Button todayBtn;
	Button weekBtn;
	Button tmrwBtn;
	EditText budgetEdt;
	
	int timeFrame = 1;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.budget_fragment_layout);
	    
	    todayBtn = (Button) findViewById(R.id.budget_fragment_mode_today);
	    weekBtn = (Button) findViewById(R.id.budget_fragment_mode_week);
	    tmrwBtn = (Button) findViewById(R.id.budget_fragment_mode_tmrw);
	    budgetEdt = (EditText) findViewById(R.id.budget_fragment_budgetText);
	    
	    budgetEdt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}

	    	
	    });
	}

	
	public void startEventSearch(View v){
		String budgetStr = budgetEdt.getText().toString().trim();
		
		if(!budgetStr.equals("")){
			int budget = Integer.parseInt(budgetStr);
			
			Intent i = new Intent(this,MainActivity.class);
			i.putExtra("budget", budget);
			i.putExtra("timeFrame", timeFrame);
			this.startActivity(i);
			
		}else{
			Toast.makeText(this, "You must input a budget", Toast.LENGTH_SHORT).show();
		}
	}
		
	public void changeMode(View v) {
		todayBtn.setBackgroundResource(R.drawable.today_up);
		todayBtn.setTextColor(Color.BLACK);
		tmrwBtn.setBackgroundResource(R.drawable.tomorrow_up);
		tmrwBtn.setTextColor(Color.BLACK);
		weekBtn.setBackgroundResource(R.drawable.week_up);
		weekBtn.setTextColor(Color.BLACK);
		
		switch(v.getId()){
		case(R.id.budget_fragment_mode_today):
			todayBtn.setBackgroundResource(R.drawable.today_down);
			todayBtn.setTextColor(Color.WHITE);
			timeFrame = 1;
			break;
		case(R.id.budget_fragment_mode_tmrw):
			tmrwBtn.setBackgroundResource(R.drawable.tomorrow_down);
			tmrwBtn.setTextColor(Color.WHITE);
			timeFrame = 2;
			break;
		case(R.id.budget_fragment_mode_week):
			weekBtn.setBackgroundResource(R.drawable.week_down);
			weekBtn.setTextColor(Color.WHITE);
			timeFrame = 3;
			break;
		}
	}
}
