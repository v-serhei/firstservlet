package by.verbitsky.servletdemo.entity.ext;

import by.verbitsky.servletdemo.entity.AudioContent;

import java.time.LocalDate;

public class Song extends AudioContent {
    private String songTitle;
    private String authorName;
    private String albumTitle;
    private String genre;
    private LocalDate uploadDate;

    public Song() {
    }

    public Song(long id, String songTitle, String authorName, String albumTitle, String genre, LocalDate uploadDate) {
        setId(id);
        this.songTitle = songTitle;
        this.authorName = authorName;
        this.albumTitle = albumTitle;
        this.genre = genre;
        this.uploadDate = uploadDate;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        if (getId() != song.getId()) return false;
        if (!songTitle.equals(song.songTitle)) return false;
        if (!authorName.equals(song.authorName)) return false;
        if (!albumTitle.equals(song.albumTitle)) return false;
        return genre.equals(song.genre);
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + songTitle.hashCode();
        result = 31 * result + authorName.hashCode();
        result = 31 * result + albumTitle.hashCode();
        result = 31 * result + genre.hashCode();
        return result;
    }
}