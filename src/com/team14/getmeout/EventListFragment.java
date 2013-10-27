package com.team14.getmeout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
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
     
		mAdapter = new EventListAdapter(getActivity(), R.layout.event_row_view, null);
		
		try {
			new EventsGetter().execute(new URI("http://uri.com"));
		} catch (URISyntaxException e) {
			// TODO
			Log.e("URI error", "Invaldi URI");
		}
        	
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
        
        Button intensityBtn = (Button) view.findViewById(R.id.intensity_btn);
        intensityBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(filterTray.getVisibility() == View.GONE){
					intensityTray.setVisibility(View.VISIBLE);
					catagoryTray.setVisibility(View.GONE);
					expand(getActivity(),filterTray);
				} else {
					if(intensityTray.getVisibility() == View.GONE){
						catagoryTray.setVisibility(View.GONE);
						intensityTray.setVisibility(View.VISIBLE);
					}else{
						collapse(filterTray);
					}
				}
			}
        	
        });
        
        Button catagoryBtn = (Button) view.findViewById(R.id.catagory_btn);
        catagoryBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(filterTray.getVisibility() == View.GONE){
					intensityTray.setVisibility(View.GONE);
					catagoryTray.setVisibility(View.VISIBLE);	
					expand(getActivity(),filterTray);
				} else {
					if(catagoryTray.getVisibility() == View.GONE){
						catagoryTray.setVisibility(View.VISIBLE);
						intensityTray.setVisibility(View.GONE);
					}else{
						
						collapse(filterTray);
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
	
	private class EventsGetter extends AsyncTask<URI, Integer, JSONArray> {

		@Override
		protected JSONArray doInBackground(URI... uri) {
			HttpClient client = new DefaultHttpClient();
			HttpGet req = new HttpGet(uri[0]);
			HttpResponse response;
			try {
				response = client.execute(req);
				HttpEntity entity = response.getEntity();
				InputStream istream = entity.getContent();
				if (istream == null) {
					//error
					Log.e("RequestError", "Invalid Istream");
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
				String jsonString = "";
				String line = "";
				while ((line = reader.readLine()) != null) {
					jsonString += line;
				}
				istream.close();
				return new JSONArray(jsonString);
			}
			catch (ClientProtocolException ex) {
				Log.e("RequestError", ex.toString());
			}
			catch (IOException ex)  {
				Log.e("RequestError", ex.toString());
			}
			catch (JSONException ex) {
				Log.e("RequestError", ex.toString());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(JSONArray json) {
			try {
				for (int i = 0; i < json.length(); i++) {
					mAdapter.add(new Event(getActivity(), json.getJSONObject(i)));
				}
				mAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				Log.e("ParseError", "Error while parsing json response");
			}
		}
	}
}
