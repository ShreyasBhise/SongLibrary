package songlib.view;

public class Song implements Comparable<Song> {
	private String songTitle;
	private String artistName;
	private String albumName;
	private String year;

	public Song(String song, String artist, String album, String year) {
		songTitle = song;
		artistName = artist;
		albumName = album;
		this.year = year;
	}
	
	public void setTitle(String s) {
		songTitle = s;
	}
	public String getTitle() {
		return songTitle;
	}
	public void setArtist(String s) {
		artistName = s;
	}
	public String getArtist() {
		return artistName;
	}
	public void setAlbum(String s) {
		albumName = s;
	}
	public String getAlbum() {
		return albumName;
	}
	public void setYear(String s) {
		year = s;
	}
	public String getYear() {
		return year;
	}

	public int compareTo(Song s) {
		
		int name = songTitle.compareToIgnoreCase(s.getTitle());
		int artist = artistName.compareToIgnoreCase(s.getArtist());
		
		if(name == 0) 
			return artist;
		
		return name;
	}
	public String toString() {
		return songTitle + " by " + artistName + "\n";
	}
}
