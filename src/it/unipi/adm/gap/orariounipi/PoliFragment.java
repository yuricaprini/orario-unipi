package it.unipi.adm.gap.orariounipi;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class PoliFragment extends ListFragment {
    
	@Override
    public void onCreate (Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	
    	InfoPoloDipartimento array[]= new InfoPoloDipartimento[6];
    	
    	Resources res = getResources();
    	Bitmap stemma_unipi=BitmapFactory.decodeResource(res, R.drawable.stemma_unipi);
    	array[0]= new InfoPoloDipartimento(stemma_unipi,"Ingegneria","#808282","http://gap.adm.unipi.it/GAP-A-Ingegneria/newGAP-SI.cgi"); 
    	array[1]= new InfoPoloDipartimento(stemma_unipi,"Bonanno","#3ba903","http://gap.adm.unipi.it/GAP-A-Bonanno/newGAP-SI.cgi");
    	array[2]= new InfoPoloDipartimento(stemma_unipi,"SantaMaria","#860022","http://gap.adm.unipi.it/GAP-A-SantaMaria/newGAP-SI.cgi");
    	array[3]= new InfoPoloDipartimento(stemma_unipi,"Fibonacci","#339980","http://gap.adm.unipi.it/GAP-A-Fibonacci/newGAP-SI.cgi");
    	array[4]= new InfoPoloDipartimento(stemma_unipi,"Piagge","#d28202","http://gap.adm.unipi.it/GAP-A-Piagge/newGAP-SI.cgi");
    	array[5]= new InfoPoloDipartimento(stemma_unipi,"Cisanello","#e00050","http://gap.adm.unipi.it/GAP-A-Cisanello/newGAP-SI.cgi");
    
    	CustomAdapter arrayAdapter =new CustomAdapter(this.getActivity(), R.layout.row, array);
        this.setListAdapter(arrayAdapter);
    }
	
	@Override
	public void onListItemClick(ListView l,View v, int position , long id){
		super.onListItemClick(l, v, position, id);
		
		/*Creating the intent to ImpostazioniActivity*/
		Intent intent=new Intent(this.getActivity(),ImpostazioniActivity.class);
		
		/*Start the ImpostazioniActivity*/
//		startActivity(intent);
//		/*Creating the intent*/
//		Intent intent=new Intent(this.getActivity(),OrarioAulaLiberaActivity.class);
//		
		/*Creating the message*/
		ImageView imageview =  (ImageView) v.findViewById(R.id.imageViewList);
		String message= imageview.getContentDescription().toString();

		
		/*Put the extra string in the intent*/
		intent.putExtra(MainActivity.getExtraMessage(), message);
		startActivity(intent);
	}

}