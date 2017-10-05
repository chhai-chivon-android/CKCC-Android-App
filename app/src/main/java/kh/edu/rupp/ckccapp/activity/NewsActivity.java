package kh.edu.rupp.ckccapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.model.Article;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView rclNews;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tlb_main);
        setSupportActionBar(toolbar);
        setTitle("News");
        // Show back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rclNews = (RecyclerView)findViewById(R.id.rcl_news);
        rclNews.setLayoutManager(new LinearLayoutManager(this));

        articleAdapter = new ArticleAdapter();
        rclNews.setAdapter(articleAdapter);


        loadArticlesFromServer();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadArticlesFromServer(){
        //String url = "http://10.0.2.2/test/ckcc-api/news.php";
        String url = "http://test.js-cambodia.com/ckcc/news.json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest articlesRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("ckcc", "Load data success");
                Article[] articles = new Article[response.length()];
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject articleJson = response.getJSONObject(i);
                        int id = articleJson.getInt("_id");
                        String title = articleJson.getString("_title");
                        Article article = new Article(title, 0, "");
                        articles[i] = article;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                articleAdapter.setArticles(articles);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewsActivity.this, "Error while loading articles from server", Toast.LENGTH_LONG).show();
                Log.d("ckcc", "Load article error: " + error.getMessage());
            }
        });
        requestQueue.add(articlesRequest);
    }




    // Article Adapter
    class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDate;
        ImageView imgArticle;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView)itemView.findViewById(R.id.txt_title);
            txtDate = (TextView)itemView.findViewById(R.id.txt_date);
            imgArticle = (ImageView)itemView.findViewById(R.id.img_article);
        }
    }

    class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

        private  Article[] articles;

        public ArticleAdapter(){
            articles = new Article[0];
        }

        public void setArticles(Article[] articles) {
            this.articles = articles;
            notifyDataSetChanged();
        }

        @Override
        public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(NewsActivity.this).inflate(R.layout.viewholder_article, parent, false);
            ArticleViewHolder articleViewHolder = new ArticleViewHolder(view);
            return articleViewHolder;
        }

        @Override
        public void onBindViewHolder(ArticleViewHolder holder, int position) {
            Article article = articles[position];
            holder.txtTitle.setText(article.getTitle());
        }

        @Override
        public int getItemCount() {
            return articles.length;
        }
    }


}
