package com.alfa.alfanieuws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alfa.alfanieuws.Helpers.DbBitmapUtility;

public class SingleArticleActivity extends AppCompatActivity {
    int post_id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_article);


        // This intent is called from NewsListAdapter.java Onclick method
        Intent i = getIntent();

        if(i != null) {
            DbBitmapUtility utility = new DbBitmapUtility();
            Bitmap bmp = utility.getImage(i.getExtras().getByteArray("news_image"));
            post_id = i.getExtras().getInt("news_id");
            ImageView news_image = findViewById(R.id.single_image);
            TextView news_title = findViewById(R.id.single_title);
            TextView news_text = findViewById(R.id.single_description);
//
//            LinearLayout article_layout = findViewById(R.id.article_layout);
//            article_layout.setMovementMethod(new ScrollingMovementMethod());

            news_image.setImageBitmap(bmp);
            news_title.setText(i.getExtras().getString("news_title"));
            news_text.setText(i.getExtras().getString("news_text"));
        }
    }

    public void backMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void load_comments(View view) {
        if (post_id != -1) {
            Intent intent = new Intent(this, CommentActivity.class);
            String id = Integer.toString(post_id);
            intent.putExtra("post_id", id);
            startActivity(intent);
        }
    }
}