package com.team14.getmeout;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class ContactListFragment extends ListFragment{
	
	private static ContactListAdapter mAdapter;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState){
    	super.onActivityCreated(savedInstanceState);
     
        ArrayList<Contact> contactArray = getContacts();
			
		mAdapter = new ContactListAdapter(getActivity(), R.layout.contact_row_view, contactArray);
        	
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
	
	@Override
  	public void onListItemClick(ListView listView, View view, int position, long id) {
		CheckBox inviteChk = (CheckBox) view.findViewById(R.id.invite_contact_chk);
		mAdapter.itemClicked(position);
		inviteChk.setChecked(!inviteChk.isChecked());
	} 
	
	private ArrayList<Contact> getContacts(){
		ArrayList<Contact> eventList = new ArrayList<Contact>();
		
		for(int i = 0; i < 10; i++){
			Bitmap contactPic = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_contact_picture_3);
			String contactName = "Contact Name";
			eventList.add(new Contact(contactName, contactPic));
		}
	
		return eventList;
	}

	
}
