package com.team14.getmeout;

import android.graphics.Bitmap;

public class Contact {
	private final Bitmap pic;
	private final String mName;
	
	public Contact (String name, Bitmap image){
		pic = image;
		mName = name;
	}
	
	public String getName(){
		return mName;
	}
		
	public Bitmap getPic(){
		return pic;
	}
	
}
