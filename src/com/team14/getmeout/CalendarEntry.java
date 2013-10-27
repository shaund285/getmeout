package com.team14.getmeout;

public class CalendarEntry {
	
	public static enum EntryType {
		TYPE_EVENT,
		TYPE_DATE,
		TYPE_TITLE
	};
	
	private Event event = null;
	private EntryType type = null;
	private String text = null;
	
	public CalendarEntry(Event evt) {
		this.event = evt;
		this.type = CalendarEntry.EntryType.TYPE_EVENT;
	}
	
	public CalendarEntry(String date) {
		this.text = date;
		this.type = CalendarEntry.EntryType.TYPE_DATE;
	}
	
	public CalendarEntry() {
		this.type = CalendarEntry.EntryType.TYPE_TITLE;
	}
	
	public Event getEvent() {
		return this.event;
	}
	
	public EntryType getType() {
		return this.type;
	}
	
	public String getText() {
		return this.text;
	}
}