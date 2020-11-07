package by.verbitsky.servletdemo.entity.ext;

import by.verbitsky.servletdemo.entity.AudioContent;

public class Singer extends AudioContent {

    private String singerName;

    public Singer() {
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Singer singer = (Singer) o;

        return getId() == singer.getId();
    }

    @Override
    public int hashCode() {

        return (int) (getId() ^ (getId() >>> 32));
    }
}
