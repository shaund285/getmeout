package com.team14.getmeout;

import java.util.Date;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Bundle extras = this.getIntent().getExtras();
		float budget = extras.getFloat("budget");
		int timeFrame = extras.getInt("timeFrame");
		
		Date startDate = new Date();
		Date endDate = new Date();
		
		switch(timeFrame){
		case(1):
			endDate.setDate(startDate.getDay()+1);
			break;
		case(2):
			startDate.setDate((new Date()).getDay()+1);
			endDate.setDate(startDate.getDay()+2);
			break;
		case(3):
		default:
			endDate.setDate(startDate.getDay()+7);
		}
		
		
		Fragment fragment = new EventListFragment(budget,startDate,endDate);
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, fragment);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
