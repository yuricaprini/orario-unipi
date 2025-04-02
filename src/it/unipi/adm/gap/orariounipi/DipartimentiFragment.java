package it.unipi.adm.gap.orariounipi;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class DipartimentiFragment extends ListFragment {
	

	
	@Override
    public void onCreate (Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	
    	new LoadImage().execute(this.getActivity());
    }
	
	private class LoadImage extends AsyncTask<Context, InfoPoloDipartimento[],InfoPoloDipartimento[]> { 

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			InfoPoloDipartimento array[]= new InfoPoloDipartimento[0];
	    	getView();
	    	CustomAdapter arrayAdapter =new CustomAdapter(getActivity(), R.layout.row, array);
	    	setListAdapter(arrayAdapter);
	    	
		}
		
		/*Codice eseguito in background*/
		@Override
		protected InfoPoloDipartimento[] doInBackground(Context...context) {
			
	    	final String blu="#3467bc";
	    	final String ocra="#ed8d38";
	    	final String verde="#65a457";
			
			Resources res= getResources();
	    	Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.informatica);
	    	System.out.println("BITMAP "+bitmap.getByteCount()+" ALTEZZA "+ bitmap.getHeight() + " LARGHEZZA "+bitmap.getWidth());
	    	
	    	InfoPoloDipartimento array[]= new InfoPoloDipartimento[16];
	    	
	    	array[0]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.informatica),"Informatica",blu,"http://gap.adm.unipi.it/GAP-U-Informatica/newGAP-SI.cgi"); 
	    	array[1]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.matematica),"Matematica",blu,"http://gap.adm.unipi.it/GAP-U-Matematica/newGAP-SI.cgi");
	    	array[2]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.chimica),"Chimica",blu,"http://gap.adm.unipi.it/GAP-U-Chimica/newGAP-SI.cgi");
	    	array[3]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.fisica),"Fisica",blu,"http://gap.adm.unipi.it/GAP-U-Fisica/newGAP-SI.cgi");
	    	array[4]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.ingegneria),"Ingegneria",blu,"http://gap.adm.unipi.it/GAP-U-Ingegneria/newGAP-SI.cgi");
	    	array[5]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.biologia),"Biologia",verde,"http://gap.adm.unipi.it/GAP-U-Biologia/newGAP-SI.cgi");
	    	array[6]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.medicina),"Medicina",verde,"http://gap.adm.unipi.it/GAP-U-Medicina/newGAP-SI.cgi");
	    	array[7]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.farmacia),"Farmacia",verde,"http://gap.adm.unipi.it/GAP-U-Farmacia/newGAP-SI.cgi");
	    	array[8]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.veterinaria),"Scienze Veterinarie",verde,"http://gap.adm.unipi.it/GAP-U-Veterinaria/newGAP-SI.cgi");
	    	array[9]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.agraria),"Scienze agrarie,alimentari e agro-ambientali",verde,"http://gap.adm.unipi.it/GAP-U-Agraria/newGAP-SI.cgi");
	    	array[10]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.scienze_terra),"Scienze della terra",verde,"http://gap.adm.unipi.it/GAP-U-ScTerra/newGAP-SI.cgi");
	    	array[11]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.civilta),"Civiltà e forme del sapere",ocra,"http://gap.adm.unipi.it/GAP-U-Civilta/newGAP-SI.cgi");
	    	array[12]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.economia),"Economia",ocra,"http://gap.adm.unipi.it/GAP-U-Economia/newGAP-SI.cgi");
	    	array[13]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.giurisprudenza),"Giurisprudenza",ocra,"http://gap.adm.unipi.it/GAP-U-Giurisprudenza/newGAP-SI.cgi");
	    	array[14]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.scienze_politiche),"Scienze politiche",ocra,"http://gap.adm.unipi.it/GAP-U-ScPolitiche/newGAP-SI.cgi");
	    	array[15]= new InfoPoloDipartimento(BitmapFactory.decodeResource(res, R.drawable.letteratura),"Filologia,letteratura e linguistica",ocra,"http://gap.adm.unipi.it/GAP-U-FilLettLing/newGAP-SI.cgi");
	    	
	    	return array;
	    	
		}
 
		@Override
		protected void onPostExecute(InfoPoloDipartimento[] result) {
			
			
			CustomAdapter arrayAdapter =new CustomAdapter(getActivity(), R.layout.row, result);
	        setListAdapter(arrayAdapter);
		}
	}
	
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		 
		View view = inflater.inflate(R.layout.diplist, container, false);
		return view;
	}
	
	@Override
	public void onListItemClick(ListView l,View v, int position , long id){
		super.onListItemClick(l, v, position, id);
		ImageView imageview =  (ImageView) v.findViewById(R.id.imageViewList);
		
		new AuleRequestTask(this.getActivity().getApplication(),imageview.getContentDescription().toString()).execute();
		
		/*Creating the intent to ImpostazioniActivity*/
		Intent intent =new Intent(this.getActivity(),TipoRicercaActivity.class);
		

		/*Creating the message*/
		String message= imageview.getContentDescription().toString();

		
		/*Put the extra string in the intent*/
		intent.putExtra(MainActivity.getExtraMessage(), message);
		
		/*start the activity*/
		startActivity(intent);
	
	}
	
	
	/**********CLASSE PRIVATA TASK RICHESTA AULE***********/
    private class AuleRequestTask extends AsyncTask<Integer,ArrayList<String>,ArrayList<String>>{
    	private Application app;
    	private String address;
    	
    public AuleRequestTask (Application app,String address) {
    		   this.setApp(app);
    		   this.setAddress(address);
    		  }
    	
    @Override
    protected void onCancelled() {
        super.onCancelled();
        
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    
    @Override
    protected ArrayList<String> doInBackground(Integer... params) {
    	ArrayList<String> a = new ArrayList<String>() ;
    	HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(address);	
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("selector","tab_aula"));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			HttpResponse response = client.execute(post);
			/*Using apache commons IO to convert an InputStream into a String*/
		    String risposta= IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
		    
		    Document js_document=Jsoup.parse(risposta);
		    
		    Elements option_elements=js_document.select("option");
		    
		    Iterator<Element> option_iterator = option_elements.listIterator();
	    
	    	while(option_iterator.hasNext()){
	    		Element option_element = option_iterator.next();
	    		System.out.println(option_element.html());
	    		a.add(option_element.html());
	    	}
		    
		    
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return a;
        
    }

    @Override
    protected void onPostExecute(ArrayList<String> a) {
        // TODO Remove updating view
        super.onPostExecute(a);
        GlobalVariable appState = ((GlobalVariable)app);
        appState.setState(a);
    }


	public void setApp(Application app) {
		this.app = app;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    
}
}