package com.team14.getmeout;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {
	private DrawerLayout profileDrawer;
    private ListView profileList;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerListAdapter profileListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.profileDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // set up the drawer's list view with items and click listener
		this.profileList = (ListView) findViewById(R.id.left_drawer);
		this.profileList.setOnItemClickListener(new DrawerItemClickListener());
		
        // Set the adapter for the list view
		this.profileListAdapter = new DrawerListAdapter(this.getBaseContext(), R.layout.drawer_list_item);
        profileList.setAdapter(this.profileListAdapter);
        
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        this.drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                profileDrawer,         /* DrawerLayout object */
                R.drawable.ic_launcher,  /* nav drawer image to replace 'Up' caret */
                R.string.accessibility_drawer_open,  /* "open drawer" description for accessibility */
                R.string.accessibility_drawer_close  /* "close drawer" description for accessibility */
                );
        profileDrawer.setDrawerListener(drawerToggle);
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
        this.getActionBar().setDisplayHomeAsUpEnabled(true);
        this.getActionBar().setHomeButtonEnabled(true);
		
		Fragment fragment = new EventPageFragment();
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
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.drawerToggle.syncState();
    }
     
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.drawerToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
        if (item.getItemId() == android.R.id.home) {
            if (this.profileDrawer.isDrawerOpen(this.profileList)) {
                this.profileDrawer.closeDrawer(this.profileList);
            } else {
                this.profileDrawer.openDrawer(this.profileList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
