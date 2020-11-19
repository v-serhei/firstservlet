package by.verbitsky.servletdemo.entity;

import by.verbitsky.servletdemo.entity.ext.Song;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * Class Basket. Describes user basket
 * Used as temporary order storage
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see User
 */
public class Basket {
    private Set<Song> songs;

    public Basket() {
        songs = new HashSet<>();
    }

    public boolean isEmpty() {
        return songs.isEmpty();
    }

    public boolean addSong(Song song) {
        return songs.add(song);
    }

    public boolean removeSong(Long songId) {
        Optional<Song> searchedSong = songs.stream()
                .filter(song -> song.getId() == songId)
                .findAny();
        return searchedSong.filter(song -> songs.remove(song)).isPresent();
    }

    public void clear() {
        songs.clear();
    }

    public Set<Song> getSongs() {
        return Collections.unmodifiableSet(songs);
    }

    public boolean contains(Long songId) {
        long count = songs.stream()
                .filter(song -> song.getId() == songId)
                .count();
        return count > 0;
    }
}
