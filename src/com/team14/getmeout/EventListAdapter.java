package com.team14.getmeout;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<Event>{
	private static LayoutInflater mInflater;

	protected class ViewHolder{
		public ImageView image;
		public TextView name;
		public TextView details;
		public TextView date;
		public TextView price;
		public ImageView transport;	
		public TextView venueName;
		public TextView travelTime;
	}
	
	public EventListAdapter(Context context, int resid, ArrayList<Event> items) {
		super(context, resid, items);	
		mInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Event mEvent = this.getItem(position);
		ViewHolder holder;
		View view;
		if (convertView == null) {
			view = (View) mInflater.inflate(R.layout.event_row_view, parent,false);
			holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.event_image);
			holder.name = (TextView) view.findViewById(R.id.name_view);
			//holder.details = (TextView) view.findViewById(R.id.details_view);
		
			holder.date = (TextView) view.findViewById(R.id.date_view);
			holder.price = (TextView) view.findViewById(R.id.price_view);
			holder.transport = (ImageView) view.findViewById(R.id.transport_view);
			holder.venueName = (TextView) view.findViewById(R.id.venue_name);
			holder.travelTime = (TextView) view.findViewById(R.id.travel_time_view);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		holder.image.setImageBitmap(mEvent.getPic());
		holder.name.setText(mEvent.getName());
		//holder.details.setText(mEvent.getName());
		//holder.date.setText(mEvent.getDate()); // Might need additional formatting...
		holder.price.setText("£"+String.valueOf(mEvent.getTotalPrice()));
		//holder.transport.setImageResource(....);
		//holder.venueName.setText(mEvent.getVenue().getName());
		//holder.travelTime.setText(....);
		
		
		return view;
	}
	

}
