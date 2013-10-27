package com.team14.getmeout;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactListAdapter extends ArrayAdapter<Contact>{
	private static LayoutInflater mInflater;

	private HashMap<Integer,Boolean> checkedContacts = new HashMap<Integer,Boolean>(); 

	protected class ViewHolder{
		public ImageView image;
		public TextView name;
		public CheckBox inviteChk;
	}
	
	public ContactListAdapter(Context context, int resid, ArrayList<Contact> items) {
		super(context, resid, items);	
		mInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Contact contact = this.getItem(position);
		ViewHolder holder;
		View view;
		if (convertView == null) {
			view = (View) mInflater.inflate(R.layout.contact_row_view, parent,false);
			holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.contact_image);
			holder.name = (TextView) view.findViewById(R.id.contact_name);
			holder.inviteChk = (CheckBox) view.findViewById(R.id.invite_contact_chk);
			holder.inviteChk.setFocusable(false);
			holder.inviteChk.setClickable(false);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		holder.image.setImageBitmap(contact.getPic());
		holder.name.setText(contact.getName());
		holder.inviteChk.setChecked(checkedContacts.containsKey(position) ? checkedContacts.get(position):false);
		return view;
	}
	
	public void itemClicked(int position){
		checkedContacts.put(position, checkedContacts.containsKey(position)? !checkedContacts.get(position):true);
	}

}
