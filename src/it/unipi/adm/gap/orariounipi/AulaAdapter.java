package it.unipi.adm.gap.orariounipi;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AulaAdapter extends ArrayAdapter<InfoAula> {

	private ArrayList<InfoAula> grouped;

	/*constructor*/
	public AulaAdapter(Context context, int resource, InfoAula[] objects,
			ArrayList<InfoAula> infoaulalistgroup) {
		super(context, resource, objects);
		this.grouped = infoaulalistgroup;
	}



	/*Optimized overriding of getView with ViewHolder, avoiding useless inflating and invocation of findViewById method*/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/*Object used to don't lose the reference to the View*/
		ViewHolder viewHolder = null;


		if (convertView == null) {

			/*Allocating the inflater*/
			LayoutInflater inflater =
					(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.rowaulalist, null);

			/*Allocating and setting the ViewHolder*/
			viewHolder = new ViewHolder();
			viewHolder.aula = (TextView) convertView.findViewById(R.id.aula);
			viewHolder.materia = (TextView) convertView.findViewById(R.id.materia);
			viewHolder.orario = (TextView) convertView.findViewById(R.id.orario);
			viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.PROVA);

			/*memorize the viewHolder in the view*/
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();

		}

		InfoAula infoaula = getItem(position);

		viewHolder.aula.setText(infoaula.getNomeaula());
		viewHolder.materia.setText(infoaula.getLezione());
		viewHolder.orario
				.setText("Dalle " + infoaula.getOrarioinizio() + " alle " + infoaula.getOrariofine());
		viewHolder.layout.removeAllViews();


		/*aggiungo views all'elemento*/
		for (int i = 0; i < this.grouped.size(); i++) {



			if (grouped.get(i).getNomeaula().equals(viewHolder.aula.getText())) {

				TextView lezione = (TextView) View.inflate(this.getContext(), R.layout.prova, null);
				TextView orario = (TextView) View.inflate(this.getContext(), R.layout.prova1, null);
				lezione.setText(grouped.get(i).getLezione());
				orario.setText("Dalle " + grouped.get(i).getOrarioinizio() + " alle "
						+ grouped.get(i).getOrariofine());


				viewHolder.layout.addView(lezione);
				viewHolder.layout.addView(orario);


			}

		}



		return convertView;
	}

	private class ViewHolder {

		public LinearLayout layout;
		public TextView aula;
		public TextView materia;
		public TextView orario;



	}
}
