package kh.edu.rupp.ckccapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.model.Comment;

/**
 * CKCCApp
 * Created by leapkh on 11/28/17.
 */

public class CourseDetailActivity extends AppCompatActivity {

    private EditText etxtComment;

    private String courseId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_detail);

        // Replace built-in ActionBar with Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tlb_main);
        setSupportActionBar(toolbar);
        setTitle("CKCC Mobile App");

        // Get course data
        Intent intent = getIntent();
        courseId = intent.getIntExtra("id", 0) + "";
        String title = intent.getStringExtra("title");
        String imageUrl = intent.getStringExtra("image_url");
        String startDate = intent.getStringExtra("start_date");
        String description = intent.getStringExtra("description");

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(title);
        TextView txtStartDate = (TextView) findViewById(R.id.txt_start_date);
        txtStartDate.setText(startDate);
        TextView txtDescription = (TextView) findViewById(R.id.txt_description);
        txtDescription.setText(description);

        etxtComment = (EditText) findViewById(R.id.etxt_comment);
    }

    public void onCommentClick(View view) {
        String content = etxtComment.getText().toString();
        String user = "ckcc";
        Comment comment = new Comment(courseId, user, content, System.currentTimeMillis());

        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference("comment");
        commentsRef.push().setValue(comment);
    }

}
