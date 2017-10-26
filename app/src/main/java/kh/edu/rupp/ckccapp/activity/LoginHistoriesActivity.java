package kh.edu.rupp.ckccapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.model.DbManager;
import kh.edu.rupp.ckccapp.model.LoginHistory;

public class LoginHistoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_histories);

        RecyclerView rclHistory = (RecyclerView) findViewById(R.id.rcl_history);
        rclHistory.setLayoutManager(new LinearLayoutManager(this));
        rclHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        DbManager dbManager = new DbManager(this);
        ArrayList<LoginHistory> histories = dbManager.getHistories();

        rclHistory.setAdapter(new HistoryAdapter(histories));
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView txtUsername;
        TextView txtDate;
        TextView txtSuccess;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            txtUsername = (TextView) itemView.findViewById(R.id.txt_username);
            txtDate = (TextView) itemView.findViewById(R.id.txt_date);
            txtSuccess = (TextView) itemView.findViewById(R.id.txt_success);
        }
    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

        private ArrayList<LoginHistory> histories;

        public HistoryAdapter(ArrayList<LoginHistory> histories) {
            this.histories = histories;
        }

        @Override
        public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LoginHistoriesActivity.this).inflate(R.layout.viewholder_history, parent, false);
            return new HistoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HistoryViewHolder holder, int position) {
            LoginHistory history = histories.get(position);
            holder.txtUsername.setText(history.getUsername());
            holder.txtSuccess.setText(history.isSuccess() ? "success" : "fail");
        }

        @Override
        public int getItemCount() {
            return histories.size();
        }
    }

}
