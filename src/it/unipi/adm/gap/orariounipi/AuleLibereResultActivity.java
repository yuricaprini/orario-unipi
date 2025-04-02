package it.unipi.adm.gap.orariounipi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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


import com.google.android.gms.ads.AdRequest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;


public class AuleLibereResultActivity extends ActionBarActivity {

	private Context context = this;
	private ArrayList<InfoAula> infoaulalistgroup = new ArrayList<InfoAula>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aule_libere_result);



		android.app.ActionBar actionbar = this.getActionBar();
		actionbar.setSubtitle("Risultati per \" Aule Libere \" ");

		new GetListTask().execute(this);

	}

	private class GetListTask extends AsyncTask<Context, InfoAula[], InfoAula[]> {

		private ProgressBar throbber;
		private ListView results;


		private void showThrobber(ListView l, ProgressBar p) {
			p.setVisibility(View.VISIBLE);
			l.setVisibility(View.INVISIBLE);
		}

		private void setThrobber(ProgressBar throbber) {
			this.throbber = throbber;
		}

		private void setResults(ListView results) {
			this.results = results;
		}

		private String getMessageFromPreviousActivity(Intent intent) {

			return intent.getStringExtra(ImpostazioniActivity.getExtraMessage());

		}

		private List<BasicNameValuePair> buildForm(String[] fields, String[] tokens) {

			List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();

			pairs.add(new BasicNameValuePair(fields[0], tokens[1]));// dal
			pairs.add(new BasicNameValuePair(fields[1], tokens[1]));//al

			pairs.add(new BasicNameValuePair(fields[2], tokens[4])); //minimo

			//pairs.add(new BasicNameValuePair(fields[3], tokens[2])); //apertura
			pairs.add(new BasicNameValuePair(fields[3], "9:00"));
			//pairs.add(new BasicNameValuePair(fields[4], tokens[3])); //chiusura
			pairs.add(new BasicNameValuePair(fields[4], "18:00"));
			pairs.add(new BasicNameValuePair(fields[5], "aule_libere")); //query

			return pairs;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			setThrobber((ProgressBar) findViewById(R.id.progressBar1));
			setResults((ListView) findViewById(R.id.listview));

			showThrobber(this.results, this.throbber);
		}

		@Override
		protected InfoAula[] doInBackground(Context... context) {

			InfoAula[] a = null;
			String[] fields = {"dal", "al", "minimo", "apertura", "chiusura", "query"};
			HttpClient client;
			HttpPost post;
			String message, address;
			String[] tokens;

			message = getMessageFromPreviousActivity(getIntent());

			tokens = message.split(" ");

			address = tokens[0];

			client = new DefaultHttpClient();
			post = new HttpPost(address);

			List<BasicNameValuePair> pairs = buildForm(fields, tokens);

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

				System.out.println(js_document.html());

				String CURRENT = "";

				/* *****************PARSING****************** */
				for (Element e : js_document.getAllElements()) {
					int j = 0;
					for (Node n : e.childNodes()) {
						if (n instanceof Comment) {
							System.out.println(n);

							String[] data = n.toString().split("[|]");
							System.out.println(data[0]);
							if (j != 0) {
								String aula = data[2];
								String orarioinizio = data[3];
								String orariofine = data[4];
								String minimo = "Libera per " + data[6].split(" ")[0] + " ore";

								System.out.println(aula + " " + orarioinizio + " " + orariofine + " " + minimo);

								/*FILTRO IN BASE ALLE IMPOSTAZIONI DI ORARIO INIZIO E ORARIO FINE*/
								int inizio = stringToMinutes(orarioinizio);
								int fine = stringToMinutes(orariofine);

								int apertura = stringToMinutes(tokens[2]);
								int chiusura = stringToMinutes(tokens[3]);

								if (((inizio < apertura) && (chiusura < fine))
										|| ((inizio < apertura) && (apertura < fine) && (fine <= chiusura))
										|| ((apertura <= inizio) && (inizio < chiusura) && (chiusura < fine))
										|| ((apertura <= inizio) && (fine <= chiusura))) {

									if (inizio < apertura) {
										//SCELGO APERTURA
										orarioinizio = minutesToString(apertura);
									} else {
										//SCELGO INIZIO
										orarioinizio = minutesToString(inizio);
									}

									if (chiusura < fine) {
										//SCELGO CHIUSURA
										orariofine = minutesToString(chiusura);
									} else {
										//SCELGO FINE 
										orariofine = minutesToString(fine);
									}

									int min = stringToMinutes(orariofine) - stringToMinutes(orarioinizio);
									minimo = "Libera per " + minutesToString(min) + " ore";

									if (!CURRENT.equals(aula)) {
										CURRENT = aula;
										infoaulalist.add(new InfoAula(aula, minimo, orarioinizio, orariofine));

									} else {
										infoaulalistgroup.add(new InfoAula(aula, minimo, orarioinizio, orariofine));

									}

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

		private String minutesToString(int apertura) {

			String result = String.format("%02d:%02d", apertura / 60, apertura % 60);
			return result;
		}

		private int stringToMinutes(String orario) {
			String[] tokens = orario.split(":");
			return Integer.parseInt(tokens[0]) * 60 + Integer.parseInt(tokens[1]);
		}

		@Override
		protected void onPostExecute(InfoAula[] result) {
			System.out.println(result.length);
			//System.out.println(infoaulalistgroup.get(0).getNomeaula()+infoaulalistgroup.get(0).getLezione());
			throbber.setVisibility(View.GONE);

			ListView list = this.results;

			AulaAdapter aulaadapter =
					new AulaAdapter(context, R.layout.rowaulalist, result, infoaulalistgroup);

			list.setAdapter(aulaadapter);
			list.setVisibility(View.VISIBLE);
			if (result.length == 0) {
				AlertDialog.Builder db = new AlertDialog.Builder(AuleLibereResultActivity.this);

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
