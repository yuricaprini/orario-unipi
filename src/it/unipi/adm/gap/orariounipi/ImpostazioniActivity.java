package it.unipi.adm.gap.orariounipi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.ads.AdRequest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;



public class ImpostazioniActivity extends ActionBarActivity {

	/* Key value for the intent message*/
	private static final String EXTRA_MESSAGE = "it.unipi.adm.gap.MESSAGE";
	private EditText edit_text_inizio = null;
	private EditText edit_text_fine = null;
	private EditText edit_text_ore_libere = null;
	private EditText edit_text_giorno = null;

	public static String getExtraMessage() {
		return EXTRA_MESSAGE;
	}

	/*Method of the life-cycle activity*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		/*Create the Activity and set the layout*/
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_impostazioni);

		/*retrieve the EditText*/
		edit_text_inizio = (EditText) findViewById(R.id.edit_text_inizio);
		edit_text_fine = (EditText) findViewById(R.id.edit_text_fine);
		edit_text_ore_libere = (EditText) findViewById(R.id.edit_text_ore_libere);
		edit_text_giorno = (EditText) findViewById(R.id.edit_text_giorno);

		/*Setting the date*/
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
		edit_text_giorno.setText(dateFormat.format(new Date(System.currentTimeMillis())));

	}


	/*Method called when the Orario Inizio EditText is clicked*/
	public void startDialogOrarioInizio(View v) {
		CustomTimePickerDialog custom_time_picker_dialog =
				new CustomTimePickerDialog(this, callback_inizio, 9, 0, true);
		custom_time_picker_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
				custom_time_picker_dialog);
		custom_time_picker_dialog.setTitle("Imposta l'orario di inizio");
		custom_time_picker_dialog.show();

	}

	/*Method called when the Orario Fine EditText is clicked*/
	public void startDialogOrarioFine(View v) {
		CustomTimePickerDialog custom_time_picker_dialog =
				new CustomTimePickerDialog(this, callback_fine, 18, 0, true);
		custom_time_picker_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
				custom_time_picker_dialog);
		custom_time_picker_dialog.setTitle("Imposta l'orario di fine");
		custom_time_picker_dialog.show();

	}

	/*Method called when the Ore Libere EditText is clicked*/
	public void startDialogOreLibere(View v) {



		/*Creating an alert dialog*/
		//View dialog_layout = inflater.inflate(R.layout.dialog,null);
		AlertDialog.Builder db = new AlertDialog.Builder(this);
		//db.setView(dialog_layout);
		db.setTitle("Imposta il minimo di ore libere");
		db.setPositiveButton("Annulla", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		final String[] array = {"1:00", "2:00", "4:00", "6:00", "8:00"};

		db.setItems(array, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// The 'which' argument contains the index position
				// of the selected item
				edit_text_ore_libere.setText(array[which]);
				dialog.dismiss();
			}
		});
		final AlertDialog dialog = db.create();

		/*
				ListView list= (ListView) dialog_layout.findViewById(R.id.list_dialog);
				String [] array = {"1:00","2:00","4:00","6:00", "8:00"};
		    ArrayAdapter<String> arrayAdapter =
		            new ArrayAdapter<String>(this, R.layout.rowdialog, R.id.textViewdialog, array);
		    list.setAdapter(arrayAdapter);
		    list.setOnItemClickListener(new OnItemClickListener() 
		    { 
		            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
		            { 
		            	TextView textview = (TextView)v.findViewById(R.id.textViewdialog);
		            	edit_text_ore_libere.setText(textview.getText());
		            	dialog.dismiss();
		            } 
		    });
		    */
		dialog.show();


	}

	/*Method called when Giorno EditText is clicked*/
	public void startDialogGiorno(View v) {

		String data = edit_text_giorno.getText().toString();
		String giorno = (String) data.subSequence(0, data.indexOf("/"));
		String mese = (String) data.subSequence(data.indexOf("/") + 1, data.lastIndexOf("/"));
		String anno = (String) data.subSequence(data.lastIndexOf("/") + 1, data.length());

		int giornoint = Integer.parseInt(giorno);
		int meseint = Integer.parseInt(mese);
		int annoint = Integer.parseInt(anno);

		MyDatePickerDialog dialog =
				new MyDatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int yy, int mm, int dd) {

						edit_text_giorno.setText(
								Integer.toString(dd) + "/" + Integer.toString(mm + 1) + "/" + Integer.toString(yy));
					}
				}, annoint, meseint - 1, giornoint);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", dialog);

		dialog.show();
	}

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

	/*Method to call when Orario odierno image is clicked*/
	public void startOrarioOdierno(View v) {
		if (isConnectedToInternet()) {
			Intent mycreator = getIntent();
			String msg_received = mycreator.getStringExtra(MainActivity.getExtraMessage());
			Log.v("MESSAGE", msg_received);

			String msg_send = "O " + msg_received + " " + edit_text_giorno.getText().toString();
			Intent mychild = new Intent(this, OrarioAulaLiberaActivity.class);
			mychild.putExtra(ImpostazioniActivity.getExtraMessage(), msg_send);
			startActivity(mychild);
		} else {
			AlertDialog.Builder db = new AlertDialog.Builder(this);

			db.setTitle("Ooops!");
			db.setMessage(
					"Sembra che tu non abbia abilitato la tua connessione internet.\n\nControlla la tua connessione dal men� Impostazioni.");
			db.setPositiveButton("Impostazioni", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
				}
			});
			db.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {}
			});


			db.create().show();

		}

	}

	public void startAuleLibere(View v) {
		if (isConnectedToInternet()) {
			Intent mycreator = getIntent();
			String msg_received = mycreator.getStringExtra(MainActivity.getExtraMessage());
			Log.v("MESSAGE", msg_received);

			String msg_send = "L " + msg_received + " " + edit_text_giorno.getText().toString() + " "
					+ edit_text_ore_libere.getText() + " " + edit_text_inizio.getText() + " "
					+ edit_text_fine.getText();

			Intent mychild = new Intent(this, OrarioAulaLiberaActivity.class);
			mychild.putExtra(ImpostazioniActivity.getExtraMessage(), msg_send);
			startActivity(mychild);
		} else {
			AlertDialog.Builder db = new AlertDialog.Builder(this);

			db.setTitle("Ooops!");
			db.setMessage(
					"Sembra che tu non abbia abilitato la tua connessione internet.\n\nControlla la tua connessione dal men� Impostazioni.");
			db.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {}
			});
			db.setPositiveButton("Impostazioni", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
				}
			});

			db.create().show();

		}

	}

	/*Private class for creating a Custom picker with a max e min value*/
	private static class MyDatePickerDialog extends DatePickerDialog {

		public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year,
				int monthOfYear, int dayOfMonth) {
			super(context, callBack, year, monthOfYear, dayOfMonth);
		}

		public void setTitle(CharSequence title) {
			super.setTitle("Imposta il giorno");
		}
	}

	/* Private class for creating a Custom picker with a max e min value*/
	private static class CustomTimePickerDialog extends TimePickerDialog {

		public static final int MIN_HOUR = 9;
		public static final int MAX_HOUR = 18;

		public CustomTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay,
				int minute, boolean is24HourView) {
			super(context, callBack, hourOfDay, minute, is24HourView);
		}

		@Override
		public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
			super.onTimeChanged(timePicker, hourOfDay, minute);
			if (hourOfDay < MIN_HOUR) {
				timePicker.setCurrentHour(MIN_HOUR);
			}
			if (hourOfDay > MAX_HOUR) {
				timePicker.setCurrentHour(MAX_HOUR);
			}
		}
	}

	/*Declaring the callback to call when the time is set on Orario Inizio*/
	private TimePickerDialog.OnTimeSetListener callback_inizio =
			new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					edit_text_inizio
							.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
				}
			};

	/*Declaring the callback to call when the time is set on Orario Fine*/
	private TimePickerDialog.OnTimeSetListener callback_fine =
			new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					edit_text_fine
							.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
				}
			};

}

