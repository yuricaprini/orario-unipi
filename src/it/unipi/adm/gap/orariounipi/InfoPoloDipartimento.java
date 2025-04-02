package it.unipi.adm.gap.orariounipi;


import android.graphics.Bitmap;

public class InfoPoloDipartimento{
	
	private Bitmap photo;
	private String name;
	private String color;
	private String address;
	
	/*Constructor*/
	public InfoPoloDipartimento (Bitmap photo,String name,String color, String address){
		
		this.photo=photo;
		this.name=name;
		this.color=color;
		this.address=address;
	}
	
	/*Other methods for getting and setting*/

	public Bitmap getPhoto(){
		return this.photo;
	}
	
	public void setPhoto(Bitmap photo){
		this.photo= photo;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public void setColor(String color){
		this.color=color;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public void setAddress(String address){
		this.address=address;
	}
}
