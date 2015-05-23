package br.com.qx.ms.app.arrocha;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import br.com.qx.ms.app.arrocha.model.Video;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class Splash extends Activity {
	private static final String TAG = Splash.class.getSimpleName();
	private ProgressDialog pDialog;
	private RequestQueue rq;
	private Video video;
	private ArrayList<Video> videos;

	public final String url = "http://marciosn.github.io/JSON/json/videos.json";
	public final String LISTA_VIDEOS = "videos";
	public final String IMAGE = "image";
	public final String URL = "urlVideo";
	public final String TITULO = "titulo";
	public final String ARTISTA = "artista";
	public final String DURACAO = "duracao";
	public final String ANO = "ano";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		rq = Volley.newRequestQueue(Splash.this);
		
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.show();
		
		JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				Log.d(TAG, response.toString());
				Log.i(TAG, response.toString());
				
				hidePDialog();
				
				videos = new ArrayList<Video>();
				
				for(int i = 0; i < response.length(); i++){
					try {
						JSONObject obj = response.getJSONObject(i);
						video = new Video();
						video.setImage(obj.getString(IMAGE));
						video.setUrlVideo(obj.getString(URL));
						video.setTitulo(obj.getString(TITULO));
						video.setDuracao(obj.getString(DURACAO));
						video.setArtista(obj.getString(ARTISTA));
						video.setAno(obj.getString(ANO));
						videos.add(video);
					} catch (JSONException e) {
						e.printStackTrace();
					}	
				}
				Intent intent = new Intent(Splash.this, List_Videos.class);
				intent.putParcelableArrayListExtra(LISTA_VIDEOS, videos);
				startActivity(intent);
				finish();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				hidePDialog();
				Toast.makeText(Splash.this, "Problem!", Toast.LENGTH_SHORT).show();
			}
		});
		request.setTag(TAG);
		rq.add(request);
	}
	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
		 //Toast.makeText(MainActivity.this, "Tamanho Array "+List.size(), Toast.LENGTH_SHORT).show();
	}
}
