package br.com.qx.ms.app.arrocha.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable{
	private String image;
	private String urlVideo;
	private String titulo;
	private String duracao;
	private String artista;
	private String ano;
	
	public Video(){
		image = "";
		urlVideo = "";
		titulo = "";
		duracao = "";
		artista = "";
		ano = "";
	}
	
	public Video(Parcel parcel){
		this.image = parcel.readString();
		this.urlVideo = parcel.readString();
		this.titulo = parcel.readString();
		this.duracao = parcel.readString();
		this.artista = parcel.readString();
		this.ano = parcel.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(image);
		dest.writeString(urlVideo);
		dest.writeString(titulo);
		dest.writeString(duracao);
		dest.writeString(artista);
		dest.writeString(ano);
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}
	public static final Parcelable.Creator<Video> CREATOR = new Creator<Video>() {
		
		@Override
		public Video[] newArray(int size) {
			return new Video[size];
		}
		
		@Override
		public Video createFromParcel(Parcel source) {
			return new Video(source);
		}
	};
}
