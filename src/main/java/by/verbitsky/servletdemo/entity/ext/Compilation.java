package by.verbitsky.servletdemo.entity.ext;

import by.verbitsky.servletdemo.entity.AudioContent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Compilation extends AudioContent {
    private String compilationTitle;
    private List <Song> songList;
    private String compilationType;
    private LocalDate compilationCreationDate;

    public Compilation() {
        songList = new ArrayList<>();
    }

    public String getCompilationTitle() {
        return compilationTitle;
    }

    public void setCompilationTitle(String compilationTitle) {
        this.compilationTitle = compilationTitle;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public String getCompilationType() {
        return compilationType;
    }

    public void setCompilationType(String compilationType) {
        this.compilationType = compilationType;
    }

    public LocalDate getCompilationCreationDate() {
        return compilationCreationDate;
    }

    public void setCompilationCreationDate(LocalDate compilationCreationDate) {
        this.compilationCreationDate = compilationCreationDate;
    }

    public void addSong(AudioContent song) {
        if (song instanceof Song) {
            songList.add((Song) song);
        }
    }

    public void addAllSongs(Collection<AudioContent> songs) {
        for (AudioContent song : songs) {
            if (song instanceof Song) {
                songList.add((Song) song);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass() != o.getClass()) return false;
        if (o == null) return false;

        Compilation compilation = (Compilation) o;

        if (getId() != compilation.getId()) return false;
        if (!compilationTitle.equals(compilation.compilationTitle)) return false;
        if (!compilationType.equals(compilation.compilationType)) return false;
        return compilationCreationDate.equals(compilation.compilationCreationDate);
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + compilationType.hashCode();
        result = 31 * result + compilationCreationDate.hashCode();
        return result;
    }
}