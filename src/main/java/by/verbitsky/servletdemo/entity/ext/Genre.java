package by.verbitsky.servletdemo.entity.ext;

import by.verbitsky.servletdemo.entity.AudioContent;

public class Genre extends AudioContent {

    private String genreName;

    public Genre() {
    }

    public Genre(int genreId, String genreName) {
        setId(genreId);
        this.genreName = genreName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;

        Genre genre = (Genre) o;

        if (getId() != genre.getId()) return false;
        return genreName.equals(genre.genreName);
    }

    @Override
    public int hashCode() {
        int result = (int)getId();
        result = 31 * result + genreName.hashCode();
        return result;
    }
}