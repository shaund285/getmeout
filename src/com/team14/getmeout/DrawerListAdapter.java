package com.team14.getmeout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerListAdapter extends ArrayAdapter<CalendarEntry> {
	
	private LayoutInflater layoutInflater;
	
	public DrawerListAdapter(Context context, int resid, ArrayList<Event> eventList) {
		super(context, resid);
		this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.fromEventList(eventList);
	}
	
	public DrawerListAdapter(Context context, int resid) {
		super(context, resid);
		this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//temporary test stuff
			ArrayList<Event> list = new ArrayList<Event>();
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
					Date d = new Date();
					d.setDate(i % 2);
					eventJson.put("date", d.toGMTString());
					JSONArray contacts = new JSONArray();
					contacts.put(1);
					contacts.put(2);
					eventJson.put("going", contacts);
					
					list.add(new Event(this.getContext(), eventJson));
				}catch(JSONException e){
					Log.wtf("wtf", "wtf");
				}
			}
		//end of test stuff
		this.fromEventList(list);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CalendarEntry entry = this.getItem(position);
		View view = null;
		if (entry.getType() == CalendarEntry.EntryType.TYPE_EVENT) {
			Event evt = entry.getEvent();
			view = (View) this.layoutInflater.inflate(R.layout.drawer_list_item, parent, false);
			ImageView image = (ImageView) view.findViewById(R.id.drawer_event_image);
			image.setImageBitmap(evt.getPic());
			TextView text = (TextView) view.findViewById(R.id.drawer_event_name);
			text.setText(evt.getName());
			TextView price = (TextView) view.findViewById(R.id.drawer_event_price);
			price.setText("£" + String.valueOf(evt.getTotalPrice()));
			TextView venue = (TextView) view.findViewById(R.id.drawer_event_venue);
			venue.setText(evt.getVenue().getName()); //this actually is the name of the venue, not the price
			TextView going = (TextView) view.findViewById(R.id.drawer_event_going);
			going.setText(String.valueOf(evt.getGoingContacts().length));
		}
		else if (entry.getType() == CalendarEntry.EntryType.TYPE_DATE) {
			view = (View) this.layoutInflater.inflate(R.layout.drawer_list_day, parent, false);
			TextView date = (TextView) view.findViewById(R.id.drawer_day_date);
			date.setText(entry.getText());
		}
		else if (entry.getType() == CalendarEntry.EntryType.TYPE_TITLE) {
			view = (View) this.layoutInflater.inflate(R.layout.drawer_title_item, parent, false);
		}
		return view;
	}
	
	private void fromEventList(ArrayList<Event> eventList) {
		/*Collections.sort(eventList, new Comparator<Event>() {

			@Override
			public int compare(Event evt1, Event evt2) {
				Date date1 = new Date(evt1.getDate());
				Date date2 = new Date(evt2.getDate());
				date1.setHours(0);
				date1.setMinutes(0);
				date1.setSeconds(0);
				date2.setHours(0);
				date2.setMinutes(0);
				date2.setSeconds(0);
				if (date1.before(date2)) {
					return -1;
				}
				return 1;
			}
			
		});*/
		//initialise list from an event list
		boolean swap = true;
		//bubble sort
		while (swap) {
			swap = false;
			for (int i = 0; i < eventList.size() - 1; i++) {
				Date date1 = new Date(eventList.get(i).getDate());
				Date date2 = new Date(eventList.get(i + 1).getDate());
				//round dates to day number
				Calendar cal = Calendar.getInstance();
		        cal.setTime(date1);
		        cal.set(Calendar.HOUR_OF_DAY, 0);
		        cal.set(Calendar.MINUTE, 0);
		        cal.set(Calendar.SECOND, 0);
		        cal.set(Calendar.MILLISECOND, 0);
		        date1 = cal.getTime();
		        cal.setTime(date2);
		        cal.set(Calendar.HOUR_OF_DAY, 0);
		        cal.set(Calendar.MINUTE, 0);
		        cal.set(Calendar.SECOND, 0);
		        cal.set(Calendar.MILLISECOND, 0);
				date2 = cal.getTime();
				if (date1.after(date2)) {
					//swap elements
					swap = true;
					Event tmp = eventList.get(i);
					eventList.set(i, eventList.get(i + 1));
					eventList.set(i + 1, tmp);
				}
			}
		}
		//build category list from ordered event list
		this.clear();
		Date last = null;
		this.add(new CalendarEntry());
		for (int i = 0; i < eventList.size(); i++) {
			Date current;
			Calendar cal = Calendar.getInstance();
	        cal.setTime(new Date(eventList.get(i).getDate()));
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        current = cal.getTime();
			if (last == null || last.before(current)) {
				//update last day and add day separator entry
				last = current;
				String dateString = new SimpleDateFormat("E dd.MM.yyyy").format(eventList.get(i).getDate()).toString();
				this.add(new CalendarEntry(dateString));
			}
			this.add(new CalendarEntry(eventList.get(i)));
		}
	}

}
