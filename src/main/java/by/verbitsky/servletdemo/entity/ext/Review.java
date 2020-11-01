package by.verbitsky.servletdemo.entity.ext;

import by.verbitsky.servletdemo.entity.AudioContent;

public class Review extends AudioContent {
    private static final String PREFIX_NAME = " (";
    private static final String POSTFIX_NAME = " )";
    private String reviewText;
    private String songTitle;
    private String userName;
    private long songId;

    public Review() {
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle, String singerName) {
        StringBuilder sb = new StringBuilder();
        sb.append(songTitle);
        sb.append(PREFIX_NAME);
        sb.append(singerName);
        sb.append(POSTFIX_NAME);
        this.songTitle = sb.toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass() != o.getClass()) return false;
        if (o == null) return false;
        Review review = (Review) o;

        if(getSongId() != ((Review) o).getSongId()) return false;
        if (!reviewText.equals(review.reviewText)) return false;
        if (!songTitle.equals(review.songTitle)) return false;
        return userName.equals(review.userName);
    }

    @Override
    public int hashCode() {
        int result = reviewText.hashCode();
        result = 31 * result + songTitle.hashCode();
        result = 31 * result + userName.hashCode();
        return result;
    }
}
