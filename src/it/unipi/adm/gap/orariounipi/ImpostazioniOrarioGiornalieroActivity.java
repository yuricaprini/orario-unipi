package it.unipi.adm.gap.orariounipi;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ImpostazioniOrarioGiornalieroActivity extends ActionBarActivity{
	
	private EditText edit_text_giorno;
	private EditText edit_text_aula;
	private EditText edit_text_insegnamento;
	private EditText edit_text_tipo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_impostazioni_orario_giornaliero);
		
		ActionBar actionBar= this.getSupportActionBar();
		actionBar.setSubtitle(" Impostazioni per \" Orario Giornaliero \"");
		
		/*retrieve the EditText*/
		edit_text_giorno=(EditText)findViewById(R.id.edit_text_giorno);
		edit_text_aula=(EditText)findViewById(R.id.edit_text_aula);
		edit_text_insegnamento=(EditText)findViewById(R.id.edit_text_insegnamento);

		edit_text_tipo=(EditText)findViewById(R.id.edit_text_tipo);
		
		/*Setting the date*/
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN); 
		edit_text_giorno.setText(dateFormat.format(new Date(System.currentTimeMillis())));
		

		edit_text_insegnamento.setOnEditorActionListener(new OnEditorActionListener() {        
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if(actionId==EditorInfo.IME_ACTION_DONE){
		        	
		            //Clear focus here from edittext
		        	//edit_text_insegnamento.onEditorAction(EditorInfo.IME_ACTION_DONE);
		        	edit_text_insegnamento.clearFocus();
		        	
		        	InputMethodManager imm = (InputMethodManager)getSystemService(
		        		      Context.INPUT_METHOD_SERVICE);
		        		imm.hideSoftInputFromWindow(edit_text_insegnamento.getWindowToken(), 0);
		        	
		        }
		    return false;
		    }
		});
		
		
		edit_text_tipo.setOnEditorActionListener(new OnEditorActionListener() {        
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if(actionId==EditorInfo.IME_ACTION_DONE){
		        	
		            //Clear focus here from edittext
		        	//edit_text_insegnamento.onEditorAction(EditorInfo.IME_ACTION_DONE);
		        	edit_text_tipo.clearFocus();
		        	
		        	InputMethodManager imm = (InputMethodManager)getSystemService(
		        		      Context.INPUT_METHOD_SERVICE);
		        		imm.hideSoftInputFromWindow(edit_text_tipo.getWindowToken(), 0);
		        	
		        }
		    return false;
		    }
		});
		
		
		
	}
	
	public void startDialogAula(View v){
		GlobalVariable g=(GlobalVariable)this.getApplicationContext();
		String[] a={""};
		a = g.getState().toArray(a);
		final String[] b=a;
		AlertDialog.Builder db = new AlertDialog.Builder(this);

		db.setTitle("Scegli l'aula");
		db.setPositiveButton("Annulla", new 
		    DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	
		            }
		        });
		
		db.setItems(a,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // The 'which' argument contains the index position
            // of the selected item
            	edit_text_aula.setText(b[which]);
            	dialog.dismiss();
            }
        });
		AlertDialog dialog = db.create();
        dialog.show();

	}
	
	public void onPressedButton(View v){
		
		this.getIntent().getExtras();
		
		startActivity( new Intent (this,OrarioGiornalieroResultActivity.class)
								.putExtra(MainActivity.getExtraMessage(), this.getIntent().getExtras().getString(MainActivity.getExtraMessage())
																												+" "+edit_text_giorno.getText().toString()
																												+" "+edit_text_aula.getText().toString()
																												+" "+edit_text_insegnamento.getText().toString()
																												+" "+edit_text_tipo.getText().toString()));
		
	}
	/*Method called when Giorno EditText is clicked*/
	public void startDialogGiorno(View v){
	  
		String data=edit_text_giorno.getText().toString();
		String giorno = (String) data.subSequence(0, data.indexOf("/"));
		String mese = (String) data.subSequence(data.indexOf("/")+1, data.lastIndexOf("/"));
		String anno = (String) data.subSequence(data.lastIndexOf("/")+1,data.length());
		
		int giornoint=Integer.parseInt(giorno);
		int meseint= Integer.parseInt(mese);
		int annoint=Integer.parseInt(anno);
		
		MyDatePickerDialog dialog= new MyDatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
			@Override
			public void onDateSet(DatePicker view,int yy, int mm, int dd){
				
				edit_text_giorno.setText(Integer.toString(dd)+"/"+Integer.toString(mm+1)+"/"+Integer.toString(yy));
			}
		},annoint, meseint-1, giornoint);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", dialog);
		
		dialog.show();
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
}
