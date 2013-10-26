package com.team14.getmeout;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		
		GoogleMap map = mapFragment.getMap();
		map.getUiSettings().setAllGesturesEnabled(false);
		map.getUiSettings().setZoomControlsEnabled(false);
		
		LatLng sydney = new LatLng(-33.867, 151.206);
		
//		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
		
		map.addMarker(new MarkerOptions()
			.title("Sydney")
			.snippet("The most populos city in Australia.")
			.position(sydney));		
		
//		Fragment fragment = new BudgetFragment();
//		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//		fragmentTransaction.add(R.id.fragment_container, fragment);
//		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
