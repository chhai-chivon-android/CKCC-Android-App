package kh.edu.rupp.ckccapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * CKCCApp
 * Created by leapkh on 12/9/17.
 */

public class Article {

    @SerializedName("_title")
    private String title;

    //@SerializedName("_date")
    //private long date;

    @SerializedName("_image_url")
    private String imageUrl;

    public Article(String title, long date, String imageUrl) {
        this.title = title;
        //this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
