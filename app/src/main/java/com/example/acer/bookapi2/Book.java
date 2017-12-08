package com.example.acer.bookapi2;

import android.graphics.Bitmap;

/**
 * An {@link Book} object contains information related to a single book.
 */

public class Book {

    /**
     * Average rating of the book
     */
    private double mAverageRating;

    /**
     * number of ratings of the book
     */
    private int mRatingsCount;

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

    private String mInfoUrl;

    private String mPreviewUrl;

    private String mCategories;

    private Bitmap mBookImage;


    /**
     * Constructs a new {@link Book} object
     *
     * @param rating      is the average rating of the book
     * @param title       is the title of the book
     * @param authors     is the authors of the book
     * @param description is the short description of the book
     * @param date        is the published date of the book
     */
    public Book(double rating, int ratingsCount, String title, String authors, String description, String date, String infoUrl, String previewUrl, String categories, Bitmap image) {
        mAverageRating = rating;
        mRatingsCount=ratingsCount;
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mDate = date;
        mInfoUrl = infoUrl;
        mPreviewUrl = previewUrl;
        mCategories = categories;
        mBookImage = image;
    }

    /**
     * Get the average rating of the book
     */
    public double getAverageRating() {
        return mAverageRating;
    }

    /**
     * Get the average rating of the book
     */
    public int getRatingsCount() {
        return mRatingsCount;
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
     * Get the info url of the book
     */
    public String getInfoUrl() {
        return mInfoUrl;
    }

    /**
     * Get the preview url of the book
     */
    public String getPreviewUrl() {
        return mPreviewUrl;
    }

    /**
     * Get the categories of the book
     */
    public String getCategories() {
        return mCategories;
    }

    /**
     * Get the image of the book
     */
    public Bitmap getBookImage() {
        return mBookImage;
    }

}
