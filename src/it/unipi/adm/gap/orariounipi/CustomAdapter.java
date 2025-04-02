package it.unipi.adm.gap.orariounipi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<InfoPoloDipartimento>  {
	
	 public CustomAdapter(Context context, int resource,InfoPoloDipartimento [] objects) {
	        super(context, resource, objects);
	    }
	 
	 /*Optimized overriding of getView with ViewHolder, avoiding useless inflating and invocation of findViewById method*/
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent){
		 
		 	/*Object used to don't lose the reference to the View*/
	        ViewHolder viewHolder = null;
	        
	        if (convertView == null) {
	        	
	        	/*Allocating the inflater*/
	            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);	            
	            convertView = inflater.inflate(R.layout.row, null);
	            
	            /*Allocating and setting the ViewHolder*/
	            viewHolder = new ViewHolder();
	            viewHolder.photo = (ImageView)convertView.findViewById(R.id.imageViewList);
	            viewHolder.name = (TextView)convertView.findViewById(R.id.textViewList);
	         
	            /*memorize the viewHolder in the view*/
	            convertView.setTag(viewHolder);
	            
	        } else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }
	        
	        InfoPoloDipartimento infopolodipartimento = getItem(position);
	        viewHolder.photo.setImageBitmap( infopolodipartimento.getPhoto() );
	        /*Setting custom background and description per case*/
	        viewHolder.photo.setBackgroundColor(Color.parseColor(infopolodipartimento.getColor()));
	        viewHolder.photo.setContentDescription(infopolodipartimento.getAddress());
	        viewHolder.name.setText(infopolodipartimento.getName());
	        
	        return convertView;
	 }
	 
	 private class ViewHolder {
	        public ImageView photo;
	        public TextView name;
	        
	    }
}