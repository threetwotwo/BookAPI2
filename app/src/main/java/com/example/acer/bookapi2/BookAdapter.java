package com.example.acer.bookapi2;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Acer on 12/4/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {


    public BookAdapter(Context context, @NonNull List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        RatingBar ratingBar = listItemView.findViewById(R.id.rb_list_rating);
        TextView ratingsCount = listItemView.findViewById(R.id.tv_list_ratings_count);
        TextView noRatingView = listItemView.findViewById(R.id.tv_list_no_rating);

        if (currentBook != null) {
            ratingBar.setRating((float) currentBook.getAverageRating());
        }
        ratingsCount.setText((int) currentBook.getRatingsCount() + " ratings");

        ImageView bookImage = listItemView.findViewById(R.id.iv_list_book_image);
        bookImage.setImageBitmap(currentBook.getBookImage());

        TextView titleView = listItemView.findViewById(R.id.tv_title);
        titleView.setText(currentBook.getTitle());

        TextView authorsView = listItemView.findViewById(R.id.tv_authors);
        authorsView.setText(currentBook.getAuthors());

        TextView descriptionView = listItemView.findViewById(R.id.tv_description);
        descriptionView.setText(currentBook.getCategories());

        TextView publishDateView = listItemView.findViewById(R.id.tv_publish_date);

        /** Get year from publish date */
        try {
            String dateString = currentBook.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            Date date = dateFormat.parse(dateString);
            String publishYear = dateFormat.format(date);
            publishDateView.setText(publishYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listItemView;
    }

    private int getRatingColor(double averageRating) {
        int ratingColorResourceId;
        int ratingFloor = (int) Math.floor(averageRating);
        switch (ratingFloor) {
            case 0:
            case 1:
                ratingColorResourceId = R.color.magnitude1;
                break;
            case 2:
                ratingColorResourceId = R.color.magnitude2;
                break;
            case 3:
                ratingColorResourceId = R.color.magnitude3;
                break;
            case 4:
                ratingColorResourceId = R.color.magnitude4;
                break;
            case 5:
                ratingColorResourceId = R.color.magnitude5;
                break;
            case 6:
                ratingColorResourceId = R.color.magnitude6;
                break;
            case 7:
                ratingColorResourceId = R.color.magnitude7;
                break;
            case 8:
                ratingColorResourceId = R.color.magnitude8;
                break;
            case 9:
                ratingColorResourceId = R.color.magnitude9;
                break;
            default:
                ratingColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), ratingColorResourceId);
    }
}
