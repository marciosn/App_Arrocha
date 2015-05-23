package br.com.qx.ms.app.arrocha.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.qx.ms.app.arrocha.AppController;
import br.com.qx.ms.app.arrocha.R;
import br.com.qx.ms.app.arrocha.model.Video;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Video> videoItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomListAdapter(Activity activity, List<Video> movieItems) {
		this.activity = activity;
		this.videoItems = movieItems;
	}

	@Override
	public int getCount() {
		return videoItems.size();
	}

	@Override
	public Object getItem(int location) {
		return videoItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
		
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView artist = (TextView) convertView.findViewById(R.id.artist);
		TextView duration = (TextView) convertView.findViewById(R.id.duration);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

		// getting movie data for the row
		Video video = videoItems.get(position);

		// thumbnail image
		thumbNail.setImageUrl(video.getImage(), imageLoader);
		
		// title
		title.setText(video.getTitulo());
		artist.setText("Artista: "+video.getArtista());
		duration.setText("Duracao: "+video.getDuracao());
		year.setText(video.getAno());
		return convertView;
	}

}