package kh.edu.rupp.ckccapp.activity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.model.App;
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

        if(App.getInstance(this).getArticles() == null){
            loadArticlesFromServer();
        }else{
            Article[] articles = App.getInstance(this).getArticles();
            articleAdapter.setArticles(articles);
        }
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
        StringRequest articlesRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Article[] articles = gson.fromJson(response, Article[].class);
                // Pass data to adapter for displaying
                articleAdapter.setArticles(articles);
                // Save data to Singleton for using later
                App.getInstance(NewsActivity.this).setArticles(articles);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewsActivity.this, "Error while loading articles from server", Toast.LENGTH_LONG).show();
                Log.d("ckcc", "Load article error: " + error.getMessage());
            }
        });
        /*
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
                        String imageUrl = articleJson.getString("_image_url");
                        Article article = new Article(title, 0, imageUrl);
                        articles[i] = article;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Pass data to adapter for displaying
                articleAdapter.setArticles(articles);
                // Save data to Singleton for using later
                App.getInstance(NewsActivity.this).setArticles(articles);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewsActivity.this, "Error while loading articles from server", Toast.LENGTH_LONG).show();
                Log.d("ckcc", "Load article error: " + error.getMessage());
            }
        });
        */
        requestQueue.add(articlesRequest);
    }




    // Article Adapter
    class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDate;
        NetworkImageView imgArticle;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView)itemView.findViewById(R.id.txt_title);
            txtDate = (TextView)itemView.findViewById(R.id.txt_date);
            imgArticle = (NetworkImageView)itemView.findViewById(R.id.img_article);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Article article = articleAdapter.getArticles()[position];
                    Intent intent = new Intent(NewsActivity.this, ArticleDetailActivity.class);
                    /*
                    intent.putExtra("title", article.getTitle());
                    intent.putExtra("image_url", article.getImageUrl());
                    */
                    Global.selectedArticle = article;
                    startActivity(intent);
                }
            });
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

        public Article[] getArticles() {
            return articles;
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

            // Display image using NetworkImageView
            ImageLoader imageLoader = App.getInstance(NewsActivity.this).getImageLoader();
            holder.imgArticle.setDefaultImageResId(R.drawable.img_default_image);
            holder.imgArticle.setErrorImageResId(R.drawable.ic_error_image);
            holder.imgArticle.setImageUrl(article.getImageUrl(), imageLoader);
        }

        @Override
        public int getItemCount() {
            return articles.length;
        }
    }


}
