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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class OrarioAulaLiberaActivity extends ActionBarActivity {
	ProgressBar progressbar;
	ListView listview;
	Context context = this;

	private class GetListTask extends AsyncTask<Context, InfoAula[], InfoAula[]> {

		private ListAdapter aulaadapter;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			/*Setto progressbar e listview*/
			progressbar = (ProgressBar) findViewById(R.id.progressBar1);
			progressbar.setVisibility(View.VISIBLE);
			listview = (ListView) findViewById(R.id.listview);
			listview.setVisibility(View.INVISIBLE);

		}

		@Override
		protected InfoAula[] doInBackground(Context... context) {

			InfoAula[] a = null;

			/* Getting the subject of the listItem */

			/*Getting the intent message*/
			Intent intent = getIntent();
			String message = intent.getStringExtra(MainActivity.getExtraMessage());

			String indirizzo = null;
			String giorno = null;
			String minimo = null;
			String apertura = null;
			String chiusura = null;

			/*Splitting the message in tokens*/
			String[] items = message.split(" ");

			if (items[0].equals("O")) {
				indirizzo = items[1];
				giorno = items[2];
			}
			if (items[0].equals("L")) {
				indirizzo = items[1];
				giorno = items[2];
				minimo = items[3];
				apertura = items[4];
				chiusura = items[5];
				Log.v("FROM ORARIOAULALIBEREACTIVITY", indirizzo);
				Log.v("FROM ORARIOAULALIBEREACTIVITY", giorno);
				Log.v("FROM ORARIOAULALIBEREACTIVITY", minimo);
				Log.v("FROM ORARIOAULALIBEREACTIVITY", apertura);
				Log.v("FROM ORARIOAULALIBEREACTIVITY", chiusura);
			}



			/*Only for developing environment,to avoid network exception on versions superior to GingerBread API 9*/
			//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			//StrictMode.setThreadPolicy(policy);

			/*retrieve the listview associated to this activity*/

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(indirizzo);
			List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
			//			pairs.add(new BasicNameValuePair("dal", "16/01/2014"));
			//			pairs.add(new BasicNameValuePair("al", "16/01/2014"));

			if (items[0].equals("O")) {
				pairs.add(new BasicNameValuePair("query", "orario_oggi_aula"));
			}

			if (items[0].equals("L")) {
				pairs.add(new BasicNameValuePair("dal", giorno));
				pairs.add(new BasicNameValuePair("al", giorno));
				pairs.add(new BasicNameValuePair("minimo", minimo));
				pairs.add(new BasicNameValuePair("apertura", apertura));
				pairs.add(new BasicNameValuePair("chiusura", chiusura));
				pairs.add(new BasicNameValuePair("query", "aule_libere"));
			}

			// Url Encoding the POST parameters
			try {
				post.setEntity(new UrlEncodedFormEntity(pairs));
			} catch (UnsupportedEncodingException e) {
				// writing error to Log
				e.printStackTrace();
			}


			try {

				HttpResponse response = client.execute(post);

				// writing response to log (only the weak reference)
				Log.d("Http Response:", response.toString());


				/*String risposta=readStream(response);*/

				/*Using apache commons IO to convert an InputStream into a String*/
				String risposta = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);

				/*NOT USED String risposta=readStream(response);*/


				/*Print long string on logcat*/
				//longInfo(risposta);

				//NOT USED Log.v("Http Response:", risposta);

				/*Creating a list*/
				ArrayList<InfoAula> infoaulalist = new ArrayList<InfoAula>();

				/*Parsing the html*/
				Document js_document = Jsoup.parse(risposta);
				System.out.println("**********************************************************");
				System.out.println(js_document);

				String aula = null;
				String orarioinizio = null;
				String orariofine = null;
				String lezione = null;

				if (items[0].equals("L")) {
					for (Element e : js_document.getAllElements()) {
						int j = 0;
						for (Node n : e.childNodes()) {
							if (n instanceof Comment) {
								System.out.println(n);

								String[] data = n.toString().split("[|]");
								System.out.println(data[0]);
								if (j != 0) {
									aula = data[2];
									orarioinizio = data[3];
									orariofine = data[4];
									lezione = "Libera per " + data[6].split(" ")[0] + " ore";
									infoaulalist.add(new InfoAula(aula, lezione, orarioinizio, orariofine));
								}
								j++;
							}
						}
					}
				}
				if (items[0].equals("O")) {
					Elements tr_elements = js_document.select("TR");

					Iterator<Element> tr_iterator = tr_elements.listIterator();
					int i = 1;

					/*scorro subito di un elemento che � inutile*/
					tr_iterator.next();

					while (tr_iterator.hasNext()) {
						Element tr_element = tr_iterator.next();
						System.out.println("Elemento" + i);

						Elements td_elements = tr_element.select("TD");
						System.out.println(tr_element.select("a").html());

						aula = tr_element.select("a").html();
						orarioinizio = td_elements.get(3).html();
						orariofine = td_elements.get(4).html();
						lezione = td_elements.get(6).text();

						/*
						  	Iterator<Element> td_iterator = td_elements.listIterator();
						 
						  
						  	int j=0;
						  
						  	while(td_iterator.hasNext())
							{
						  		Element td_element = td_iterator.next();
						  		System.out.println("Elemento td"+ j);
						  		System.out.println(td_element.html());
						  		System.out.println("");
						  		j++;
							}*/
						System.out.println(
								"----------------------------------------------------------------------------------");

						infoaulalist.add(new InfoAula(aula, lezione, orarioinizio, orariofine));
						i++;
					}
				}
				// Log.v("PROVA", elements.select("TABLE").get(0).html());
				a = infoaulalist.toArray(new InfoAula[infoaulalist.size()]);


				// this.setListAdapter(arrayAdapter);
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
		protected void onPostExecute(InfoAula[] result) {
			progressbar.setVisibility(View.GONE);
			ListView list = (ListView) findViewById(R.id.listview);
			//AulaAdapter aulaadapter =new AulaAdapter(context, R.layout.rowaulalist, result , InfoAula[] grouped);

			list.setAdapter(aulaadapter);
			list.setVisibility(View.VISIBLE);
			if (result.length == 0) {
				AlertDialog.Builder db = new AlertDialog.Builder(OrarioAulaLiberaActivity.this);

				db.setTitle("Orario");
				db.setMessage("Nessuna attivit� prevista per oggi.");
				db.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {}
				});
				db.create().show();
			}

		}


	}


	/*The body of the activity*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orario_aulalibera);

		new GetListTask().execute(this);

	}

}

