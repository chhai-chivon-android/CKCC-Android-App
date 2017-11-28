package kh.edu.rupp.ckccapp.model;

import com.google.firebase.database.PropertyName;

/**
 * CKCCApp
 * Created by leapkh on 11/28/17.
 */

public class Course {

    private int id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String imageUrl;
    private String startDate;
    private String endDate;

    public Course() {
    }

    public Course(int id, String title, String description, String thumbnailUrl, String imageUrl, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @PropertyName("thumbnail_url")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @PropertyName("thumbnail_url")
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @PropertyName("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    @PropertyName("image_url")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @PropertyName("start_date")
    public String getStartDate() {
        return startDate;
    }

    @PropertyName("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @PropertyName("end_date")
    public String getEndDate() {
        return endDate;
    }

    @PropertyName("end_date")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
