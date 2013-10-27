package com.team14.getmeout;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;

@SuppressLint("ValidFragment")
public class EventListFragment extends ListFragment{
	
	private EventListAdapter mAdapter;
	private final Date startDate;
	private final Date endDate;
	private final float mBudget;
	View filterTray;
	View intensityTray;
	View catagoryTray;
	
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
        
        View view = (View) inflater.inflate(R.layout.event_list_fragment_layout, container, false); 
        
        filterTray = view.findViewById(R.id.filter_tray);
        intensityTray = view.findViewById(R.id.intensity_tray);
        catagoryTray = view.findViewById(R.id.catagory_tray);
        
        final Button intensityBtn = (Button) view.findViewById(R.id.intensity_btn);
        final Button catagoryBtn = (Button) view.findViewById(R.id.catagory_btn);
        
        intensityBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(filterTray.getVisibility() == View.GONE){
					intensityTray.setVisibility(View.VISIBLE);
					catagoryTray.setVisibility(View.GONE);
					expand(getActivity(),filterTray);
					intensityBtn.setBackgroundResource(R.drawable.filter_selected);
				} else {
					if(intensityTray.getVisibility() == View.GONE){
						catagoryTray.setVisibility(View.GONE);
						intensityTray.setVisibility(View.VISIBLE);
						intensityBtn.setBackgroundResource(R.drawable.filter_selected);
						catagoryBtn.setBackgroundResource(R.drawable.filter_normal);
					}else{
						collapse(filterTray);
						intensityBtn.setBackgroundResource(R.drawable.filter_normal);
					}
				}
			}
        	
        });
        
        catagoryBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(filterTray.getVisibility() == View.GONE){
					intensityTray.setVisibility(View.GONE);
					catagoryTray.setVisibility(View.VISIBLE);	
					expand(getActivity(),filterTray);
					catagoryBtn.setBackgroundResource(R.drawable.filter_selected);
				} else {
					if(catagoryTray.getVisibility() == View.GONE){
						catagoryTray.setVisibility(View.VISIBLE);
						intensityTray.setVisibility(View.GONE);
						intensityBtn.setBackgroundResource(R.drawable.filter_normal);
						catagoryBtn.setBackgroundResource(R.drawable.filter_selected);
					}else{
						collapse(filterTray);
						catagoryBtn.setBackgroundResource(R.drawable.filter_normal);
					}
				}
			}
        	
        });
		return view;
 	}
	
	public static void expand(Context context, final View v) {
	
	    v.measure(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	    final int targtetHeight = v.getMeasuredHeight();

	    final LayoutParams lp = v.getLayoutParams();
	    v.getLayoutParams().height = 1;
	    v.setVisibility(View.VISIBLE);
	    
	    int mAnimationTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);
	    
	    android.animation.ValueAnimator animator = ValueAnimator.ofInt(1, targtetHeight).setDuration(mAnimationTime);
 
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                v.setLayoutParams(lp);
            }
        });
        
        animator.setInterpolator(new OvershootInterpolator());
	    animator.start();
	}

	public static void collapse(final View v) {
	    final int initialHeight = v.getMeasuredHeight();

	    Animation a = new Animation()
	    {
	        @Override
	        protected void applyTransformation(float interpolatedTime, Transformation t) {
	            if(interpolatedTime == 1){
	                v.setVisibility(View.GONE);
	            }else{
	                v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
	                v.requestLayout();
	            }
	        }

	        @Override
	        public boolean willChangeBounds() {
	            return true;
	        }
	    };

	    // 1dp/ms
	    a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
	    v.startAnimation(a);
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
