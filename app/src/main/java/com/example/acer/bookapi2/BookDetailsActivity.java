package com.example.acer.bookapi2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = BookDetailsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        TextView title = findViewById(R.id.tv_details_title);
        title.setText(getIntent().getExtras().getString("title"));
        RatingBar ratingBar = findViewById(R.id.rb_details_rating);
        TextView ratingsCount = findViewById(R.id.tv_details_ratings_count);
        TextView noRating = findViewById(R.id.tv_details_no_rating);
        ratingsCount.setText(getIntent().getExtras().getInt("ratingsCount") + " ratings");
        ratingBar.setRating((float) getIntent().getExtras().getDouble("rating"));

        TextView authors = findViewById(R.id.tv_details_authors);
        authors.setText(getIntent().getExtras().getString("authors"));

        TextView date = findViewById(R.id.tv_details_date);
        date.setText(getYear(getIntent().getExtras().getString("date")));

        TextView categories = findViewById(R.id.tv_details_categories);
        categories.setText(getIntent().getExtras().getString("categories"));

        TextView description = findViewById(R.id.tv_details_description);
        description.setText(getIntent().getExtras().getString("description"));

        TextView preview = findViewById(R.id.tv_details_preview);
        // Convert the String URL into a URI object (to pass into the Intent constructor)
        final Uri previewUri = Uri.parse(getIntent().getExtras().getString("previewUrl")
                .replace("gbs_api", "kp_read_button"));

        TextView info = findViewById(R.id.tv_details_info);
        // Convert the String URL into a URI object (to pass into the Intent constructor)
        final Uri infoUri = Uri.parse(getIntent().getExtras().getString("infoUrl"));

        ImageView bookImage = findViewById(R.id.iv_details_book_image);
        bookImage.setImageBitmap((Bitmap) getIntent().getExtras().getParcelable("bookImage"));

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to view the earthquake URI
                Log.i(LOG_TAG, "TEST: The Uri passed is: " + previewUri);
                Intent previewIntent = new Intent(Intent.ACTION_VIEW, previewUri);
                startActivity(previewIntent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to view the earthquake URI
                Log.i(LOG_TAG, "TEST: The Uri passed is: " + infoUri);
                Intent previewIntent = new Intent(Intent.ACTION_VIEW, infoUri);
                startActivity(previewIntent);
            }
        });

    }

    public static String getYear(String date) {
        String publishYear = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            Date dateYear = dateFormat.parse(date);
            publishYear = dateFormat.format(dateYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return publishYear;
    }
}
