package it.unipi.adm.gap.orariounipi;

import android.view.View;

public interface AulaLibera {

	/*Method called when Giorno EditText is clicked*/
	public abstract void startDialogGiorno(View v);

	/*Method called when the Orario Inizio EditText is clicked*/
	public abstract void startDialogOrarioInizio(View v);

	/*Method called when the Orario Fine EditText is clicked*/
	public abstract void startDialogOrarioFine(View v);

	/*Method called when the Ore Libere EditText is clicked*/
	public abstract void startDialogMinimo(View v);

	public abstract void onPressedButton(View v);

}