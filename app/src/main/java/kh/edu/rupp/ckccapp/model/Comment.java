package kh.edu.rupp.ckccapp.model;

/**
 * CKCCApp
 * Created by leapkh on 11/28/17.
 */

public class Comment {

    private String course;
    private String user;
    private String content;
    private long time;

    public Comment() {
    }

    public Comment(String course, String user, String content, long time) {
        this.course = course;
        this.user = user;
        this.content = content;
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
