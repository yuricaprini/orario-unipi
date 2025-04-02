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
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;



import com.google.android.gms.ads.AdRequest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class OrarioGiornalieroResultActivity extends ActionBarActivity {

	public View progressbar;
	public ListView listview;
	private Context context = this;
	private ArrayList<InfoAula> infoaulalistgroup = new ArrayList<InfoAula>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_orario_giornaliero);

		System.out.println(getIntent().getExtras().getString(MainActivity.getExtraMessage()));

		android.app.ActionBar actionbar = this.getActionBar();
		actionbar.setSubtitle("Risultati per \" Orario Giornaliero \" ");

		new GetListTask().execute(this);

	}

	private class GetListTask extends AsyncTask<Context, InfoAula[], InfoAula[]> {



		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			/*Setto progressbar e listview*/
			progressbar = (ProgressBar) findViewById(R.id.progressBar1);
			progressbar.setVisibility(View.VISIBLE);
			listview = (ListView) findViewById(R.id.listview);
			listview.setVisibility(View.INVISIBLE);

		}

		/*Codice eseguito in background*/
		@Override
		protected InfoAula[] doInBackground(Context... context) {

			InfoAula[] a = null;

			/*Getting the intent message*/
			Intent intent = getIntent();
			String message = intent.getStringExtra(ImpostazioniActivity.getExtraMessage());

			String key = "";
			String tipo = "";
			String aula = "";
			String dal = "";
			String al = "";
			String query = "";


			/*Splitting the message in tokens*/
			String[] items = message.split(" ");
			String indirizzo = items[0];

			dal = items[1];
			al = items[1];
			if (!items[2].equals("tutte"))
				aula = items[2];
			if (!items[3].equals("tutte"))
				key = items[3];
			if (!items[4].equals("tutte"))
				tipo = items[4];


			/*Creating a client http*/

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(indirizzo);
			List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();

			/*Building the form*/
			pairs.add(new BasicNameValuePair("key", key));
			pairs.add(new BasicNameValuePair("tipo", tipo));
			pairs.add(new BasicNameValuePair("aula", aula));
			pairs.add(new BasicNameValuePair("dal", dal));
			pairs.add(new BasicNameValuePair("al", al));
			pairs.add(new BasicNameValuePair("query", "tab_libera"));


			// Url Encoding the POST parameters
			try {
				post.setEntity(new UrlEncodedFormEntity(pairs));
			} catch (UnsupportedEncodingException e) {
				// writing error to Log
				e.printStackTrace();
			}


			try {

				HttpResponse response = client.execute(post);

				/*Using apache commons IO to convert an InputStream into a String*/
				String risposta = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);


				/*Creating a list*/
				ArrayList<InfoAula> infoaulalist = new ArrayList<InfoAula>();

				/*Parsing the html*/
				Document js_document = Jsoup.parse(risposta);

				String CURRENT = "";

				/****************INIZIO DEL PARSING***********************/
				for (Element e : js_document.getAllElements()) {
					int j = 0;
					for (Node n : e.childNodes()) {
						if (n instanceof Comment) {

							if (j > 0) {
								System.out.println("***************ELEMENTO" + j + "*******************");
								System.out.println(n.toString());
								String[] tokens = n.toString().split("[|]");


								String AULA = tokens[2];

								/*Se l'aula corrente � diversa dall'aula attuale*/

								String INIZIO = tokens[3];
								String FINE = tokens[4];
								String TIPO = tokens[5];
								String ATTIVITA = tokens[6];
								System.out.println(AULA + INIZIO + FINE + TIPO + ATTIVITA);

								if (!CURRENT.equals(AULA)) {
									CURRENT = AULA;
									infoaulalist.add(new InfoAula(AULA, TIPO + ": " + ATTIVITA, INIZIO, FINE));

								} else {
									infoaulalistgroup.add(new InfoAula(AULA, TIPO + ": " + ATTIVITA, INIZIO, FINE));
								}



							}
							j++;
						}

					}
				}

				a = infoaulalist.toArray(new InfoAula[infoaulalist.size()]);


				// this.setListAdapter(arrayAdapter);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				System.out.println("*******************************");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("*******************************");
				e.printStackTrace();
			}
			return a;

		}

		@Override
		protected void onPostExecute(InfoAula[] result) {
			System.out.println(result.length);
			progressbar.setVisibility(View.GONE);
			ListView list = (ListView) findViewById(R.id.listview);

			System.out.println("********************CONTENUTO GROUPED*************************");
			for (int i = 0; i < infoaulalistgroup.size(); i++) {
				System.out.println(
						infoaulalistgroup.get(i).getNomeaula() + " " + infoaulalistgroup.get(i).getLezione());
			}
			System.out.println("********************FINE CONTENUTO GROUPED*************************");


			//    		TextView lezione = (TextView) View.inflate(context, R.layout.prova, null);
			//    		TextView orario = (TextView) View.inflate(context, R.layout.prova1, null);
			//        	lezione.setText(grouped.get(i).getLezione());
			//        	orario.setText("Dalle "+grouped.get(i).getOrarioinizio()+" alle "+grouped.get(i).getOrariofine());

			AulaAdapter aulaadapter =
					new AulaAdapter(context, R.layout.rowaulalist, result, infoaulalistgroup);

			list.setAdapter(aulaadapter);
			list.setVisibility(View.VISIBLE);
			if (result.length == 0) {
				AlertDialog.Builder db = new AlertDialog.Builder(OrarioGiornalieroResultActivity.this);

				db.setTitle("Orario");
				db.setMessage("Nessuna attivit� prevista per oggi.");
				db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {}
				});
				db.create().show();
			}

		}
	}
}
