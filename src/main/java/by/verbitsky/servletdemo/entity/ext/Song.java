package by.verbitsky.servletdemo.entity.ext;

import by.verbitsky.servletdemo.entity.AudioContent;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Song extends AudioContent {
    private static final String OPENED_BRACER = "(";
    private static final String CLOSED_BRACER = ")";
    private static final String TITLE_DELIMITER = " ";

    private String songTitle;
    private String songMergedTitle;
    private String authorName;
    private String albumTitle;
    private String genre;
    private String filePath;
    private LocalDate albumCreationDate;
    private LocalDate uploadDate;
    private BigDecimal price;
    private long singerId;
    private long albumId;
    private long genreId;


    public String getSongMergedTitle() {
        return songMergedTitle;
    }

    public void setSongMergedTitle() {
        this.songMergedTitle = songTitle
                .concat(TITLE_DELIMITER)
                .concat(OPENED_BRACER)
                .concat(authorName)
                .concat(CLOSED_BRACER);
    }

    public long getSingerId() {
        return singerId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String fileName) {
        this.filePath = fileName;
    }

    public void setSingerId(long singerId) {
        this.singerId = singerId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public Song() {
        price = BigDecimal.ZERO;
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

    public LocalDate getAlbumCreationDate() {
        return albumCreationDate;
    }

    public void setAlbumCreationDate(LocalDate albumCreationDate) {
        this.albumCreationDate = albumCreationDate;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Song song = (Song) o;
        return getId() == song.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}