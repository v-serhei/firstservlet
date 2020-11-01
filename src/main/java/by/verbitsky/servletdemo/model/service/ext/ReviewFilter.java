package by.verbitsky.servletdemo.model.service.ext;

import by.verbitsky.servletdemo.controller.command.AttributeValue;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.entity.ContentType;

public class ReviewFilter extends ContentFilter {
    private String songTitle;
    private long userId;
    private long reviewId;
    boolean findUnique;

    private static final String EMPTY_PARAMETER = "";


    public ReviewFilter() {
        super(ContentType.REVIEW, AttributeValue.DEFAULT_PAGE_NUMBER, AttributeValue.REVIEW_PAGE_REVIEW_PER_PAGE);
        setSongTitle(EMPTY_PARAMETER);
        findUnique = false;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public boolean isFindUnique() {
        return findUnique;
    }

    public void setFindUnique(boolean findUnique) {
        this.findUnique = findUnique;
    }
}
