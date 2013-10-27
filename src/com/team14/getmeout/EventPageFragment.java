package com.team14.getmeout;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventPageFragment extends Fragment {

	public static final String MapFragmentTag = "MAP_FRAGMENT";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		MapFragment mapFragment = MapFragment.newInstance();
		
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.map_view, mapFragment, MapFragmentTag);
		fragmentTransaction.commit();
		
		EventDetailsFragment detailFragment = new EventDetailsFragment();
		
		FragmentTransaction detailTransaction = getFragmentManager().beginTransaction();
		detailTransaction.add(R.id.details_view, detailFragment);
		detailTransaction.commit();
		
		
		return inflater.inflate(R.layout.event_page_layout, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();	
		
		MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentByTag(MapFragmentTag);
		
		GoogleMap map = mapFragment.getMap();
		map.getUiSettings().setAllGesturesEnabled(false);
		map.getUiSettings().setZoomControlsEnabled(false);

		LatLng sydney = new LatLng(-33.867, 151.206);

		//	map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

		map.addMarker(new MarkerOptions()
		.title("Sydney")
		.snippet("The most populos city in Australia.")
		.position(sydney));	
	}
}