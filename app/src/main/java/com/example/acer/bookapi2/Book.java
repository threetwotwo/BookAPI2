package com.example.acer.bookapi2;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * An {@link Book} object contains information related to a single book.
 */

public class Book{

    /**
     * Average rating of the book
     */
    private double mAverageRating;

    /**
     * Title of the book
     */
    private String mTitle;

    /**
     * Author(s) of the book
     */
    private String mAuthors;

    /**
     * Description of the book
     */
    private String mDescription;

    /**
     * Publication date of the book
     */
    private String mDate;

    private String mUrl;

    /**
     * Constructs a new {@link Book} object
     *
     * @param rating      is the average rating of the book
     * @param title       is the title of the book
     * @param authors     is the authors of the book
     * @param description is the short description of the book
     * @param date        is the published date of the book
     */
    public Book(double rating, String title, String authors, String description, String date, String url) {
        mAverageRating = rating;
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mDate = date;
        mUrl=url;
    }

    /**
     * Get the average rating of the book
     */
    public double getAverageRating() {
        return mAverageRating;
    }

    /**
     * Get the title of the book
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the authors of the book
     */
    public String getAuthors() {
        return mAuthors;
    }

    /**
     * Get the description of the book
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Get the date of the book
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Get the url of the book
     */
    public String getUrl() {
        return mUrl;
    }

}
