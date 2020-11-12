package by.verbitsky.servletdemo.entity.ext;

import by.verbitsky.servletdemo.entity.AudioContent;

import java.time.LocalDate;

public class Album extends AudioContent {

    private static final String OPENED_BRACER = "(";
    private static final String CLOSED_BRACER = ")";
    private static final String TITLE_DELIMITER = " ";
    private String albumTitle;
    private String singerName;
    private long singerId;
    private LocalDate albumDate;
    private String mergedTitle;

    public Album() {
    }

    public String getMergedTitle() {
        return mergedTitle;
    }

    public void setMergedTitle() {
        this.mergedTitle = albumTitle
                .concat(TITLE_DELIMITER)
                .concat(OPENED_BRACER)
                .concat(singerName)
                .concat(CLOSED_BRACER);
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public long getSingerId() {
        return singerId;
    }

    public void setSingerId(long singerId) {
        this.singerId = singerId;
    }

    public LocalDate getAlbumDate() {
        return albumDate;
    }

    public void setAlbumDate(LocalDate albumDate) {
        this.albumDate = albumDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        if (singerId != ((Album) o).getSingerId()) return false;
        return getId() == ((Album) o).getId();
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (int) (singerId ^ (singerId >>> 32));
        return result;
    }
}