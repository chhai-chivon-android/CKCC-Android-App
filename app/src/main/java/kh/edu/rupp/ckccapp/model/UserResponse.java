package kh.edu.rupp.ckccapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * CKCCApp
 * Created by leapkh on 10/17/17.
 */

public class UserResponse {

    @SerializedName("_code")
    public int code;

    @SerializedName("_message")
    public String message;

    @SerializedName("_id")
    public int id;

    @SerializedName("_name")
    public String name;

    @SerializedName("_username")
    public String username;

    @SerializedName("_profile_image")
    public String profileImage;

    @SerializedName("_token")
    public String token;

}
