package by.verbitsky.servletdemo.model.service.ext;

import by.verbitsky.servletdemo.controller.command.AttributeValue;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.model.service.ContentType;

public class SongFilter extends ContentFilter {
    private String songTitle;
    private String songGenre;
    private String singerName;

    public SongFilter(String songTitle, String songGenre, String singerName, int pageNumber) {
        super(ContentType.SONG, pageNumber, AttributeValue.MAIN_PAGE_SONG_PER_PAGE);
        setSongTitle(songTitle);
        setSongGenre(songGenre);
        setSingerName(singerName);
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
