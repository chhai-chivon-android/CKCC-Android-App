package kh.edu.rupp.ckccapp.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.activity.CourseDetailActivity;
import kh.edu.rupp.ckccapp.model.App;
import kh.edu.rupp.ckccapp.model.Course;

/**
 * CKCCApp
 * Created by leapkh on 10/24/17.
 */

public class TrainingCoursesFragment extends Fragment implements ValueEventListener {

    private RecyclerView rclCourse;
    private CourseAdapter courseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);


        rclCourse = (RecyclerView)rootView.findViewById(R.id.rcl_news);
        rclCourse.setLayoutManager(new LinearLayoutManager(getActivity()));

        courseAdapter = new CourseAdapter();
        rclCourse.setAdapter(courseAdapter);

        // Load courses from Firebase db
        loadCoursesFromFirebase();

        return rootView;

    }

    private void loadCoursesFromFirebase(){

        DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference("course");
        coursesRef.addValueEventListener(this);

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("ckcc", "[Course Ref]onDataChange");
        Course[] courses = new Course[(int)dataSnapshot.getChildrenCount()];
        int index = 0;
        for(DataSnapshot subSnapshot:dataSnapshot.getChildren()){
            Course course = subSnapshot.getValue(Course.class);
            courses[index] = course;
            index++;
        }
        courseAdapter.setCourses(courses);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    // Course Adapter
    class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDate;
        NetworkImageView imgCourse;

        public CourseViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView)itemView.findViewById(R.id.txt_title);
            txtDate = (TextView)itemView.findViewById(R.id.txt_date);
            imgCourse = (NetworkImageView)itemView.findViewById(R.id.img_course);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Course course = courseAdapter.getCourses()[getAdapterPosition()];
                    Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                    intent.putExtra("id", course.getId());
                    intent.putExtra("title", course.getTitle());
                    intent.putExtra("image_url", course.getImageUrl());
                    intent.putExtra("start_date", course.getStartDate());
                    intent.putExtra("description", course.getDescription());
                    startActivity(intent);
                }
            });
        }
    }

    class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {

        private  Course[] courses;

        public CourseAdapter(){
            courses = new Course[0];
        }

        public void setCourses(Course[] courses) {
            this.courses = courses;
            notifyDataSetChanged();
        }

        public Course[] getCourses() {
            return courses;
        }

        @Override
        public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewholder_training_course, parent, false);
            CourseViewHolder courseViewHolder = new CourseViewHolder(view);
            return courseViewHolder;
        }

        @Override
        public void onBindViewHolder(CourseViewHolder holder, int position) {
            Course course = courses[position];
            holder.txtTitle.setText(course.getTitle());
            holder.txtDate.setText(course.getStartDate());

            // Display image using NetworkImageView
            ImageLoader imageLoader = App.getInstance(getActivity()).getImageLoader();
            holder.imgCourse.setDefaultImageResId(R.drawable.img_default_image);
            holder.imgCourse.setErrorImageResId(R.drawable.ic_error_image);
            holder.imgCourse.setImageUrl(course.getThumbnailUrl(), imageLoader);
        }

        @Override
        public int getItemCount() {
            return courses.length;
        }
    }

}
