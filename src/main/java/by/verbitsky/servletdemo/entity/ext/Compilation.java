package by.verbitsky.servletdemo.entity.ext;

import by.verbitsky.servletdemo.entity.AudioContent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        return Collections.unmodifiableList(songList);
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
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Compilation compilation = (Compilation) o;
        return getId() != compilation.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}