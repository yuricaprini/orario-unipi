package it.unipi.adm.gap.orariounipi;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class TipoRicercaActivity extends ActionBarActivity {
	


	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_ricerca);
		
		/*Set the action bar*/
		ActionBar actionBar = getSupportActionBar();
		actionBar.setSubtitle(" Tipo di ricerca");
		
		/*Request for AdMob banner*/
		AdView adView = (AdView)this.findViewById(R.id.tipo_ricerca_adview);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
		adView.loadAd(adRequest);
		
		
		
	}
	
	public void startImpostazioniOrarioGiornaliero(View v){
		
		Intent intent = new Intent(this,ImpostazioniOrarioGiornalieroActivity.class);
		intent.putExtra(MainActivity.getExtraMessage(), this.getIntent().getExtras().getString(MainActivity.getExtraMessage()));
		startActivity(intent);
	}
	
	public void startImpostazioniAulaLibera(View v){
		
		Intent intent = new Intent(this,ImpostazioniAulaLiberaActivity.class);
		intent.putExtra(MainActivity.getExtraMessage(), this.getIntent().getExtras().getString(MainActivity.getExtraMessage()));
		startActivity(intent);
	}

}
