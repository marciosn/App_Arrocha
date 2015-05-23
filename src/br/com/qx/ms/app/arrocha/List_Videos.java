package br.com.qx.ms.app.arrocha;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.qx.ms.app.arrocha.adapter.CustomListAdapter;
import br.com.qx.ms.app.arrocha.model.Video;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class List_Videos extends Activity implements OnItemClickListener{
	private static final String TAG = List_Videos.class.getSimpleName();
	private ListView listView;
	private CustomListAdapter adapter;
	private ArrayList<Video> list;
	private RequestQueue rq;
	private ImageLoader imageLoader;
	
	public final String LISTA_VIDEOS = "videos";
	public final String ID_VIDEO = "id_video";
	public final String TITULO = "titulo";
	public final String ARTISTA = "artista";
	public final String DURACAO = "duracao";
	public final String ANO = "ano";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list__videos);
		
		listView = (ListView) findViewById(R.id.list);
		
		list = getIntent().getParcelableArrayListExtra(LISTA_VIDEOS);
		rq = Volley.newRequestQueue(List_Videos.this);
		
		imageLoader = new ImageLoader(rq, new ImageLoader.ImageCache() {
			private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);
			
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				cache.put(url, bitmap);
			}
			
			@Override
			public Bitmap getBitmap(String url) {
				return cache.get(url);
			}
		});
		adapter = new CustomListAdapter(this, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		Intent intent = new Intent(List_Videos.this, AssistirVideo.class);
		Video video = list.get(position);
		intent.putExtra(ID_VIDEO, getIDVideo(video.getUrlVideo()));
		intent.putExtra(TITULO, video.getTitulo());
		intent.putExtra(ARTISTA, video.getArtista());
		intent.putExtra(DURACAO, video.getDuracao());
		intent.putExtra(ANO, video.getAno());
		startActivity(intent);
	}
	
	public String getIDVideo(String url){
		Uri uri = Uri.parse(url);
		uri = Uri.parse(uri.getQueryParameter("v"));
		String id = uri.toString();
		return id;
	}
}
