package by.verbitsky.servletdemo.entity;

/**
 * Class Audio content. Base abstraction of application content
 * Audio content child objects reflect records of application data base
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */
public abstract class AudioContent {
    /** content id in data base */
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
