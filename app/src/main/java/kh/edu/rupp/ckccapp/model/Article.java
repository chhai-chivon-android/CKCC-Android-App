package kh.edu.rupp.ckccapp.model;

/**
 * CKCCApp
 * Created by leapkh on 12/9/17.
 */

public class Article {

    private String title;
    private long date;
    private String imageUrl;

    public Article(String title, long date, String imageUrl) {
        this.title = title;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
