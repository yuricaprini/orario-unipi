package it.unipi.adm.gap.orariounipi;

import com.google.android.gms.ads.AdRequest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	/* Key value for the intent message*/
	private static final String EXTRA_MESSAGE = "it.unipi.adm.gap.MESSAGE";
	private MainActivity activity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		/*Call to super and look up the layout*/
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* Obtaining the ActionBar Object*/

		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		/*Adding the tabs*/
		Tab tab1 = actionBar.newTab().setText(R.string.dipartimenti).setTabListener(
				new TabListener<DipartimentiFragment>(this, "dipartimenti", DipartimentiFragment.class));
		Tab tab2 = actionBar.newTab().setText(R.string.aree)
				.setTabListener(new TabListener<PoliFragment>(this, "poli", PoliFragment.class));

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
	}

	/* On the return from Back Button pressed */
	@Override
	protected void onResume() {

		super.onResume();

	}

	/*Method to know if the device is connected to the internet. Returns a boolean*/
	public boolean isConnectedToInternet() {

		ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	/*Method to inflate the menu layout*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*Method to know what to do when an item is selected*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {

			case R.id.action_settings:
				openInfo();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/*Method that display the Dialog for the Info*/
	public void openInfo() {

		AlertDialog.Builder db = new AlertDialog.Builder(this);

		db.setMessage("App non ufficiale del Sistema di Interrogazione GAP Unipi.\n\nVersione: 1.0");
		db.setTitle("Informazioni");

		db.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		final AlertDialog dialog = db.create();

		dialog.show();
	}


	/*Method to get the field EXTRA_MESSAGE*/
	public static String getExtraMessage() {
		return EXTRA_MESSAGE;
	}
}
