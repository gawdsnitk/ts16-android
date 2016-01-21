package com.example.ts.models; 

public class EventsDb {
	
	//private variables
	int _id;
	public String _name;
	public String des;
	public String coordinator_1,coordinator_2,phoneno_1,phoneno_2,rules,venue,date;
	
	// Empty constructor
	public EventsDb(){
		
	}
	// constructor
	public EventsDb(int id, String name, String d,String coordinator_1,String coordinator_2,String phone_no1,String phone_no2,String rules,String venue,String date){
		this._id = id;
		this._name = name;
		this.des = d;
		this.coordinator_1=coordinator_1;
		this.coordinator_2=coordinator_2;
		this.phoneno_1=phone_no1;
		this.phoneno_2=phone_no2;
		this.rules=rules;
		this.venue=venue;
		this.date=date;
		
	}
	
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting phone number
	public String getDescription(){
		return this.des;
	}
	
	// setting phone number
	public void setDescription(String d){
		this.des =d;
	}
	public String getCoordinator_1(){
		return this.coordinator_1;
	}
	
	// setting id
	public void setCoordinator_1(String c){
		this.coordinator_1 = c;
	}
	public String getCoordinator_2(){
		return this.coordinator_2;
	}
	
	// setting id
	public void setCoordinator_2(String c){
		this.coordinator_2 = c;
	}
	public String getPhone_no1(){
		return this.phoneno_1;
	}
	
	// setting id
	public void setPhone_no1(String c){
		this.phoneno_1 = c;
	}
	public String getPhone_no2(){
		return this.phoneno_2;
	}
	
	// setting id
	public void setPhone_no2(String c){
		this.phoneno_2 = c;
	}
	public String getRules(){
		return this.rules;
	}
	
	// setting id
	public void setRules(String c){
		this.rules = c;
	}
	
	public String getDate(){
		return this.date;
	}
	
	// setting id
	public void setDate(String c){
		this.date = c;
	}
	
	public String getVenue(){
		return this.venue;
	}
	
	// setting id
	public void setVenue(String c){
		this.venue = c;
	}
}