package com.team14.getmeout;

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

public class DrawerToggle extends ActionBarDrawerToggle{

	private Activity activity;
	
    public DrawerToggle(Activity activity, DrawerLayout drawerLayout,
						int drawerImageRes, int openDrawerContentDescRes,
						int closeDrawerContentDescRes) {
		super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes,
				closeDrawerContentDescRes);
		this.activity = activity;
	}

	public void onDrawerClosed(View view) {
    	this.activity.getActionBar().setTitle(R.string.app_name);
    	//this.activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    public void onDrawerOpened(View drawerView) {
    	this.activity.getActionBar().setTitle(R.string.drawer_title);
        //this.activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }
}
