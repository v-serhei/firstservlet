package by.verbitsky.servletdemo.model.service.ext;

import by.verbitsky.servletdemo.controller.command.AttributeValue;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.entity.ContentType;

public class SongFilter extends ContentFilter {
    private String songTitle;
    private String songGenre;
    private String singerName;
    private static final String EMPTY_PARAMETER = "";

    public SongFilter() {
        super(ContentType.SONG, AttributeValue.DEFAULT_PAGE_NUMBER, AttributeValue.MAIN_PAGE_SONG_PER_PAGE);
        setSongTitle(EMPTY_PARAMETER);
        setSongGenre(EMPTY_PARAMETER);
        setSingerName(EMPTY_PARAMETER);
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
        this.songTitle = songTitle;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }
}
