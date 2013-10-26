package com.team14.getmeout;

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
	private final String details;
	
	private static final String JSON_NAME = "name";
	private static final String JSON_DETAILS = "details";
	private static final String JSON_DEAL = "deal";
	private static final String JSON_VENUE = "venue";
	
	/**
	 * 
	 * @param eventJson structure {name,pic,details,deal:{summary,price},venue:{coords,name}} 
	 * @throws JSONException 
	 */
	public Event(Context context, JSONObject eventJson) throws JSONException{
		name = eventJson.getString(JSON_NAME);
		pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_contact_picture_3);
		details = eventJson.getString(JSON_DETAILS);
		venue = new Venue (eventJson.getJSONObject(JSON_VENUE));
		deal = new Deal(eventJson.getJSONObject(JSON_DEAL));
	}
	
	public String getName(){
		return name;
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
		
		public String getPrice(){
			return name;
		}
		
		public float [] getLocation(){
			return coords;
		}
	
	}
}
