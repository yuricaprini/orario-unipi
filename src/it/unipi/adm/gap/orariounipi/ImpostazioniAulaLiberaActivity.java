package it.unipi.adm.gap.orariounipi;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


public class ImpostazioniAulaLiberaActivity extends ActionBarActivity implements AulaLibera {
	
	private EditText edit_text_apertura;
	private EditText edit_text_data_aule_libere;
	private EditText edit_text_chiusura;
	private EditText edit_text_minimo; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_impostazioni_aule_libere);
		
		
		ActionBar actionbar = this.getSupportActionBar();
		actionbar.setSubtitle("Impostazioni per \" Aule Libere \"");
		
		edit_text_data_aule_libere=(EditText) this.findViewById(R.id.edit_text_data_aule_libere);
		edit_text_apertura= (EditText) this.findViewById(R.id.edit_text_apertura);
		edit_text_chiusura= (EditText) this.findViewById(R.id.edit_text_chiusura);
		edit_text_minimo= (EditText) this.findViewById(R.id.edit_text_minimo);
		
		/*Setting the date*/
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN); 
		edit_text_data_aule_libere.setText(dateFormat.format(new Date(System.currentTimeMillis())));
		
		System.out.println(this.getIntent().getStringExtra(MainActivity.getExtraMessage()));
	}
	
	/*Method called when Giorno EditText is clicked*/
	/* (non-Javadoc)
	 * @see it.unipi.adm.gap.orariounipi.AulaLibera#startDialogGiorno(android.view.View)
	 */
	@Override
	public void startDialogGiorno(View v){
	  
		String data=edit_text_data_aule_libere.getText().toString();
		String giorno = (String) data.subSequence(0, data.indexOf("/"));
		String mese = (String) data.subSequence(data.indexOf("/")+1, data.lastIndexOf("/"));
		String anno = (String) data.subSequence(data.lastIndexOf("/")+1,data.length());
		
		int giornoint=Integer.parseInt(giorno);
		int meseint= Integer.parseInt(mese);
		int annoint=Integer.parseInt(anno);
		
		MyDatePickerDialog dialog= new MyDatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
			@Override
			public void onDateSet(DatePicker view,int yy, int mm, int dd){
				
				edit_text_data_aule_libere.setText(Integer.toString(dd)+"/"+Integer.toString(mm+1)+"/"+Integer.toString(yy));
			}
		},annoint, meseint-1, giornoint);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", dialog);
		
		dialog.show();
	}
	
	
	
	/*Method called when the Orario Inizio EditText is clicked*/
	/* (non-Javadoc)
	 * @see it.unipi.adm.gap.orariounipi.AulaLibera#startDialogOrarioInizio(android.view.View)
	 */
	@Override
	public void startDialogOrarioInizio(View v){
		CustomTimePickerDialog custom_time_picker_dialog= new CustomTimePickerDialog(this,callback_inizio,9,0,true);
		custom_time_picker_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", custom_time_picker_dialog);
		custom_time_picker_dialog.setTitle("Imposta l'orario di inizio");
		custom_time_picker_dialog.show();
		
	}
	

	/*Method called when the Orario Fine EditText is clicked*/
	/* (non-Javadoc)
	 * @see it.unipi.adm.gap.orariounipi.AulaLibera#startDialogOrarioFine(android.view.View)
	 */
	@Override
	public void startDialogOrarioFine(View v){
		CustomTimePickerDialog custom_time_picker_dialog= new CustomTimePickerDialog(this,callback_fine,18,0,true);
		custom_time_picker_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", custom_time_picker_dialog);
		custom_time_picker_dialog.setTitle("Imposta l'orario di fine");
		custom_time_picker_dialog.show();
		
	}
	
	/*Method called when the Ore Libere EditText is clicked*/
	/* (non-Javadoc)
	 * @see it.unipi.adm.gap.orariounipi.AulaLibera#startDialogMinimo(android.view.View)
	 */
	@Override
	public void startDialogMinimo(View v){
		
		
		
        /*Creating an alert dialog*/
		//View dialog_layout = inflater.inflate(R.layout.dialog,null);
		AlertDialog.Builder db = new AlertDialog.Builder(this);
		//db.setView(dialog_layout);
		db.setTitle("Imposta il minimo di ore libere");
		db.setPositiveButton("Annulla", new 
		    DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	
		            }
		        });
		final String[] array = {"1:00","2:00","4:00","6:00","8:00"};
		
		db.setItems(array,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            // The 'which' argument contains the index position
            // of the selected item
            	edit_text_minimo.setText(array[which]);
            	dialog.dismiss();
            }
        });
		
		final AlertDialog dialog = db.create();
        dialog.show();

		
	}
	
	/* (non-Javadoc)
	 * @see it.unipi.adm.gap.orariounipi.AulaLibera#onPressedButton(android.view.View)
	 */
	@Override
	public void onPressedButton(View v){
		
		this.getIntent().getExtras();
		
		startActivity( new Intent (this,AuleLibereResultActivity.class)
								.putExtra(MainActivity.getExtraMessage(), this.getIntent().getExtras().getString(MainActivity.getExtraMessage())
																												+" "+edit_text_data_aule_libere.getText().toString()
																												+" "+edit_text_apertura.getText().toString()
																												+" "+edit_text_chiusura.getText().toString()
																												+" "+edit_text_minimo.getText().toString()));
		
	}
	/*Private class for creating a Custom picker with a max e min value*/
	private static class MyDatePickerDialog extends DatePickerDialog {

		   public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
		       super(context, callBack, year, monthOfYear, dayOfMonth);
		   }

		   public void setTitle(CharSequence title) {
		       super.setTitle("Imposta il giorno");
		   }
		}
	/* Private class for creating a Custom picker with a max e min value*/
	private static class CustomTimePickerDialog extends TimePickerDialog{

		    public static final int MIN_HOUR=9;
		    public static final int MAX_HOUR=18;
		    
		    public CustomTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
		    super(context, callBack, hourOfDay, minute, is24HourView);
		    }
		    
		    @Override
		    public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
		        super.onTimeChanged(timePicker, hourOfDay, minute);
		        if(hourOfDay < MIN_HOUR){
		        	timePicker.setCurrentHour(MIN_HOUR);
		        }
		        if(hourOfDay > MAX_HOUR){
		        	timePicker.setCurrentHour(MAX_HOUR);
		        }
		    }
	}
	
	/*Declaring the callback to call when the time is set on Orario Inizio*/
	private TimePickerDialog.OnTimeSetListener callback_inizio = new TimePickerDialog.OnTimeSetListener() {
		   

			@Override
		    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		    	edit_text_apertura.setText(String.format("%02d", hourOfDay) + ":" +String.format("%02d", minute));
		    }
	};
	
	/*Declaring the callback to call when the time is set on Orario Fine*/
	private TimePickerDialog.OnTimeSetListener callback_fine = new TimePickerDialog.OnTimeSetListener() {
		    @Override
		    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		    	edit_text_chiusura.setText(String.format("%02d", hourOfDay) + ":" +String.format("%02d", minute));
		    }
	};
}
