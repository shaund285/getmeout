package com.team14.getmeout;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("ValidFragment")
public class EventPageFragment extends Fragment {

	private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;
    
	public static final String MapFragmentTag = "MAP_FRAGMENT";
	
	public EventPageFragment(Event event) {
		
	}
	
    private void setUpMapIfNeeded(View inflatedView) {
        if (mMap == null) {
            mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View inflatedView = inflater.inflate(R.layout.event_page_layout, container, false);

        try {
            MapsInitializer.initialize(getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO handle this situation
        }

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        setUpMapIfNeeded(inflatedView);

        
		
		EventDetailsFragment detailFragment = new EventDetailsFragment();
		
		FragmentTransaction detailTransaction = getFragmentManager().beginTransaction();
		detailTransaction.add(R.id.details_view, detailFragment);
		detailTransaction.commit();
		
		
		return inflatedView;
	}
	
	@Override
	public void onStart() {
		super.onStart();	
		
		/*MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentByTag(MapFragmentTag);
		
		GoogleMap map = mapFragment.getMap();
		map.getUiSettings().setAllGesturesEnabled(false);
		map.getUiSettings().setZoomControlsEnabled(false);

		LatLng sydney = new LatLng(-33.867, 151.206);

		//	map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

		map.addMarker(new MarkerOptions()
		.title("Sydney")
		.snippet("The most populos city in Australia.")
		.position(sydney));	*/
	}
}
