package com.team14.getmeout;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class EventListFragment extends ListFragment{
	
	private EventListAdapter mAdapter;
	private final Date startDate;
	private final Date endDate;
	private final float mBudget;
	
	public EventListFragment(float budget, Date start, Date end){
		super();
		mBudget = budget;
		endDate = end;
		startDate = start;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState){
    	super.onActivityCreated(savedInstanceState);
     
        ArrayList<Event> eventArray = getEvents();
			
		mAdapter = new EventListAdapter(getActivity(), R.layout.event_row_view, eventArray);
        	
        setListAdapter(mAdapter);           
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
    		return null;
    	}
		
        this.setListAdapter(mAdapter);
        
		return inflater.inflate(R.layout.event_list_fragment_layout, container, false); 
 	}
	
	
	private ArrayList<Event> getEvents(){
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		for(int i = 0; i < 10; i++){
			try{
				
				JSONObject dealJson = new JSONObject();
				dealJson.put("price","10");
				dealJson.put("details","Entrance + 2 drinks");
				JSONObject venueJson = new JSONObject();
				venueJson.put("name","night club");
				JSONArray coords = new JSONArray();
				coords.put(1.23);
				coords.put(1.43);
				venueJson.put("coords",coords);
				
				JSONObject eventJson = new JSONObject();
				eventJson.put("name", "Mock Event");
				eventJson.put("details", "Amazing night outs");
				//eventJson.put("pic", value);
				eventJson.put("venue", venueJson);
				eventJson.put("deal", dealJson);
				
				eventList.add(new Event(getActivity(), eventJson));
			}catch(JSONException e){
				Log.wtf(getTag(), "wtf");
			}
		}
	
		return eventList;
	}
}
