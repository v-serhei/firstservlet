package by.verbitsky.servletdemo.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Basket {
    private Set<Long> songs;

    public Basket() {
        songs = new HashSet<>();
    }

    public boolean isEmpty() {
        return songs.isEmpty();
    }

    public boolean addSong(Long songId) {
        return songs.add(songId);
    }

    public boolean removeSong(Long songId) {
        return songs.remove(songId);
    }

    public void clear() {
        songs.clear();
    }

    public Set<Long> getSongs() {
        return Collections.unmodifiableSet(songs);
    }

    public boolean contains(Long songId) {
        return songs.contains(songId);
    }
}
