package by.verbitsky.servletdemo.model.service.ext;

import by.verbitsky.servletdemo.controller.command.AttributeValue;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.entity.ContentType;

public class SongFilter extends ContentFilter {
    private String songTitle;
    private String songGenre;
    private String singerName;
    private String albumTitle;
    private long compilationId;
    private long orderId;
    private static final String EMPTY_PARAMETER = "";

    public SongFilter() {
        super(ContentType.SONG, AttributeValue.DEFAULT_PAGE_NUMBER, AttributeValue.MAIN_PAGE_SONG_PER_PAGE);
        setSongTitle(EMPTY_PARAMETER);
        setSongGenre(EMPTY_PARAMETER);
        setSingerName(EMPTY_PARAMETER);
        setAlbumTitle(EMPTY_PARAMETER);
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSongTitle(String songTitle) {
        if (songTitle != null) {
            this.songTitle = songTitle;
        }
    }

    public void setSongGenre(String songGenre) {
        if (songGenre !=null) {
            this.songGenre = songGenre;
        }
    }

    public void setSingerName(String singerName) {
        if (singerName != null){
            this.singerName = singerName;
        }
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        if (albumTitle != null){
            this.albumTitle = albumTitle;
        }
    }

    public long getCompilationId() {
        return compilationId;
    }

    public void setCompilationId(long compilationId) {
        this.compilationId = compilationId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
