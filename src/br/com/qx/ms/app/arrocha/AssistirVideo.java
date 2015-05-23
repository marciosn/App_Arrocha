package br.com.qx.ms.app.arrocha;

import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class AssistirVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

	public final String KEY = "AIzaSyD1BAa30U-wOKE0ks8bEeVOq5n2NbVKE2o";
	public final String ID_VIDEO = "id_video";
	public final String TITULO = "titulo";
	public final String ARTISTA = "artista";
	public final String DURACAO = "duracao";
	public final String ANO = "ano";
	
	private YouTubePlayerView youtube;
	
	private String id_video;
	private String titulo;
	private String artista;
	private String duracao;
	private String ano;
	
	private TextView tituloTV;
	private TextView artistaTV;
	private TextView duracaoTV;
	private TextView anoTV;
	private TextView canalTV;
	
	private RequestQueue rq;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assistir_video);
		
		Intent i = getIntent();
		
		id_video = i.getExtras().getString(ID_VIDEO);
		titulo = i.getExtras().getString(TITULO);
		artista = i.getExtras().getString(ARTISTA);
		duracao = i.getExtras().getString(DURACAO);
		ano = i.getExtras().getString(ANO);
				
		youtube = (YouTubePlayerView) findViewById(R.id.youtube);
		youtube.initialize(KEY, this);
		
		tituloTV = (TextView) findViewById(R.id.titulo);
		artistaTV = (TextView) findViewById(R.id.artistaTV);
		duracaoTV = (TextView) findViewById(R.id.duracaoTV);
		anoTV = (TextView) findViewById(R.id.ano);
		
		setTitle(titulo);
		setInformations(titulo, artista, duracao, ano);
		
		Pattern pattern = Pattern.compile("(Canal no Youtube)");
		canalTV = (TextView) findViewById(R.id.canal_youtube);
		Linkify.addLinks(canalTV, pattern, "https://www.youtube.com/user/pabloavozromantica");
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assistir_video, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
	
	
	
	public void setInformations(String titulo, String artista, String duracao, String ano){
		tituloTV.setText("Música: " + titulo);
		artistaTV.setText("Cantor: " +  artista);
		duracaoTV.setText("Duração: " + duracao);
		anoTV.setText("Ano: " + ano);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void getRating(){
		String url = "https://www.googleapis.com/youtube/v3/videos/getRating?id="+ id_video + "&key=" + KEY;
		Toast.makeText(	AssistirVideo.this,url, Toast.LENGTH_LONG).show();
		rq = Volley.newRequestQueue(AssistirVideo.this);
		StringRequest request = new StringRequest(Request.Method.GET, url,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("ASSISTIR VIDEO", response);
						Toast.makeText(	AssistirVideo.this,response, Toast.LENGTH_LONG).show();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("ASSISTIR VIDEO ERROR", error.getMessage());
						Toast.makeText(	AssistirVideo.this ,error.toString(), Toast.LENGTH_LONG).show();
					}
				});
				request.setTag("Get Rating");
				rq.add(request);
	}
	
	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		Toast.makeText(this, "onInitializationFailure()", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player,
			boolean loadAgain) {
		if(!loadAgain){
			player.cueVideo(id_video);
		}
		
	}
}
