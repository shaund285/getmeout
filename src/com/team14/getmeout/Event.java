package com.team14.getmeout;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Event {
	private final String name;
	private final Venue venue;
	private final Bitmap pic;
	private final Deal deal;
	private long millisDate;
	private final String details;
	private final Contact[] goingContacts;
	
	private static final String JSON_NAME = "name";
	private static final String JSON_DETAILS = "details";
	private static final String JSON_DEAL = "deal";
	private static final String JSON_VENUE = "venue";
	private static final String JSON_DATE = "date";
	private static final String JSON_GOING = "going";
	
	/**
	 * 
	 * @param eventJson structure {name,pic,details,deal:{summary,price},venue:{coords,name}} 
	 * @throws JSONException 
	 */
	public Event(Context context, JSONObject eventJson) throws JSONException{
		name = eventJson.getString(JSON_NAME);
		//TODO pic needs to come from json
		pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.results_card_events_pic);
		details = eventJson.getString(JSON_DETAILS);
		venue = new Venue (eventJson.getJSONObject(JSON_VENUE));
		deal = new Deal(eventJson.getJSONObject(JSON_DEAL));
		millisDate = new Date(eventJson.getString(JSON_DATE)).getTime();
		//demo solution, just get the length
		goingContacts = new Contact [eventJson.getJSONArray(JSON_GOING).length()];
	}
	
	public String getName(){
		return name;
	}
	
	
	public long getDate(){	
		return millisDate;
	}
	
	public Contact [] getGoingContacts(){
		return goingContacts;
	}
	
	public float getTotalPrice(){
		//TODO: calculate the total price given the deal and transport 
		return 10;
	}
	
	public Venue getVenue(){
		return venue;
	}
	
	public Bitmap getPic(){
		return pic;
	}
	
	public Deal getDeal(){
		return deal;
	}
	
	public class Deal{
		private static final String JSON_PRICE = "price";
		private static final String JSON_DETAILS = "details";

		private double price; 
		private String details;
		
		public Deal(JSONObject venueJson) throws JSONException{
			price = venueJson.getDouble(JSON_PRICE);
			details = venueJson.getString(JSON_DETAILS);
		}
		
		public double getPrice(){
			return price;
		}
		
		public String getSummary(){
			return details;
		}
		
	}
	
	public class Venue{
		private static final String JSON_COORDS = "coords";
		private static final String JSON_NAME = "name";

		private final String name;
		private final float[] coords;
		 
		public Venue(JSONObject venueJson) throws JSONException{
			name = venueJson.getString(JSON_NAME);
			
			JSONArray coordsArray = venueJson.getJSONArray(JSON_COORDS);
			coords = new float[2];
			coords[0] = (float) coordsArray.getDouble(0);
			coords[1] = (float) coordsArray.getDouble(1);
		}
		
		public String getName(){
			return name;
		}
		
		public float [] getLocation(){
			return coords;
		}
	
	}
}
