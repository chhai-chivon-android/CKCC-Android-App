package kh.edu.rupp.ckccapp.model;

/**
 * CKCCApp
 * Created by leapkh on 10/26/17.
 */

public class LoginHistory {

    private int id;
    private String username;
    private long date;
    private boolean isSuccess;

    public LoginHistory(int id, String username, long date, boolean isSuccess) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.isSuccess = isSuccess;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
