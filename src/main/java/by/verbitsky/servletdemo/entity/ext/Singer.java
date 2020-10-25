package by.verbitsky.servletdemo.entity.ext;

import by.verbitsky.servletdemo.entity.AudioContent;

public class Singer extends AudioContent {

    private String singerName;

    public Singer() {
    }

    public Singer(int singerId, String singerName) {
        setId(singerId);
        this.singerName = singerName;
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
        if (!(o instanceof Singer)) return false;

        Singer singer = (Singer) o;

        if (getId() != singer.getId()) return false;
        return singerName.equals(singer.singerName);
    }

    @Override
    public int hashCode() {
        int result = (int)getId();
        result = 31 * result + singerName.hashCode();
        return result;
    }
}