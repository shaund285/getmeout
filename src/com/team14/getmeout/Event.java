package com.team14.getmeout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

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
	private static final String JSON_PIC = "image";
	
	/**
	 * 
	 * @param eventJson structure {name,image,details,deal:{summary,price},venue:{coords,name}} 
	 * @throws JSONException 
	 */
	public Event(Context context, JSONObject eventJson) throws JSONException{
		name = eventJson.getString(JSON_NAME);
		//TODO pic needs to come from json
		Bitmap tempPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.results_card_events_pic);;
		try{
			String image64 = eventJson.getString(JSON_PIC);
			if(image64.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$")){
				byte[] imageAsBytes = Base64.decode(image64, Base64.NO_WRAP);
				Bitmap image = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
				tempPic = image;
			}
		}catch(Exception e){
			 
		}
		pic = tempPic;
		
		details = eventJson.getString(JSON_DETAILS);
		venue = new Venue (eventJson.getJSONObject(JSON_VENUE));
		deal = new Deal(eventJson.getJSONObject(JSON_DEAL));
		DateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
		String dateString = eventJson.getString(JSON_DATE).replaceAll("(?<=\\d)(st|nd|rd|th)\\b", "");
		try {
			
			millisDate = format.parse(dateString).getTime();
		} catch (ParseException e) {
			DateFormat newFormat = new SimpleDateFormat("E d MMM yyyy");
			try {
				millisDate = newFormat.parse(dateString).getTime();
			} catch (ParseException e1) {
				Log.wtf("DIDNT WORK", "No it Didint");
			}
			
		}
		//demo solution, just get the length
		goingContacts = new Contact [1];
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
		
		public Deal(JSONObject venueJson) {
			try{
			price = venueJson.getDouble(JSON_PRICE);
			details = venueJson.getString(JSON_DETAILS);
			}catch(JSONException e){}
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

		private String name;
		private float[] coords;
		 
		public Venue(JSONObject venueJson) {
			
			coords = new float[2];
			try{
			JSONArray coordsArray = venueJson.getJSONArray(JSON_COORDS);
			name = venueJson.getString(JSON_NAME);
			coords[0] = (float) coordsArray.getDouble(0);
			coords[1] = (float) coordsArray.getDouble(1);
			}catch(JSONException e){
			}
		}
		
		public String getName(){
			return name;
		}
		
		public float [] getLocation(){
			return coords;
		}
	
	}
}
