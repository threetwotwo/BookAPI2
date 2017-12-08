package com.example.acer.bookapi2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Helper methods related to receiving and requesting data from Google's book API
 */

public class QueryUtils {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    /**
     * Main helper method that queries the book API data and return a list of {@link Book} objects
     */
    public static List<Book> fetchBookData(String requestUrl) {
        //Create url object
        URL url = createUrl(requestUrl);

        //Perform a HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making HTTP request");
        }


        //Return a list of book objects
        return extractFeatureFromJson(jsonResponse);
    }

    /**
     * Parses the JSON response and creates a list of book objects
     */
    private static List<Book> extractFeatureFromJson(String jsonResponse) {
        //Return early if JSON response is empty
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        double rating;
        int ratingsCount;
        String infoUrl;
        String previewUrl;
        String title;
        String author;
        String categories = null;
        String description;
        String date;
        Bitmap image = null;

        //Create a new empty array list that will hold book objects
        List<Book> books = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray booksArray = baseJsonResponse.getJSONArray("items");

            //For each book in the array, create a book object
            for (int i = 0; i < booksArray.length(); i++) {

                //Get a single book at position i
                JSONObject currentBook = booksArray.getJSONObject(i);
                //Extract the book details which is stored under the key volumeInfo
                JSONObject bookDetails = currentBook.getJSONObject("volumeInfo");

                /** Extract relevant values from keys */
                if (bookDetails.isNull("averageRating")) {
                    rating = 0;
                } else {
                    rating = bookDetails.getDouble("averageRating");
                }

                if (bookDetails.isNull("ratingsCount")) {
                    ratingsCount = 0;
                } else {
                    ratingsCount = bookDetails.getInt("ratingsCount");
                }

                if (bookDetails.isNull("title")) {
                    title = "";
                } else {
                    title = bookDetails.getString("title");
                }

                if (bookDetails.isNull("authors")) {
                    author = "";
                } else {

                    JSONArray authorsArray = bookDetails.getJSONArray("authors");
                    author = toStringArray(authorsArray);
                }

                if (bookDetails.isNull("description")) {
                    description = "";
                } else {
                    description = bookDetails.getString("description");
                }

                if (bookDetails.isNull("publishedDate")) {
                    date = "";
                } else {
                    date = bookDetails.getString("publishedDate");
                }

                if (bookDetails.isNull("infoLink")) {
                    infoUrl = "";
                } else {
                    infoUrl = bookDetails.getString("infoLink");
                }

                if (bookDetails.isNull("previewLink")) {
                    previewUrl = "";
                } else {
                    previewUrl = bookDetails.getString("previewLink");
                }

                if (bookDetails.isNull("categories")) {
                    author = "";
                } else {

                    JSONArray categoriesArray = bookDetails.getJSONArray("categories");
                    categories = toStringArray(categoriesArray);
                }

                if (bookDetails.getJSONObject("imageLinks").isNull("smallThumbnail")) {
                    image = null;
                } else {
                    try {
                        image = getImageFromUrl(bookDetails.getJSONObject("imageLinks").getString("smallThumbnail"));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                Book book = new Book(rating, ratingsCount, title, author, description, date, infoUrl, previewUrl, categories, image);

                books.add(book);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem with parsing JSON results", e);
        }
        Collections.sort(books, new Comparator<Book>() {
            @Override
            public int compare(Book book, Book t1) {
                return (int) (t1.getAverageRating() - book.getAverageRating());
            }
        });
        return books;
    }

    public static String toStringArray(JSONArray array) {
        if (array == null)
            return null;

        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                list.add(array.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list.toString()
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();
    }

    /**
     * Perform a HTTP request to the URL and receive a JSON response back
     */
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        //Return early if url is null
        if (url == null) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Read from the input stream if connection is successful(200 reponse code)
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving book results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static Bitmap getImageFromUrl(String imageUrl) throws MalformedURLException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        try {
            assert connection != null;
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Returns a new URL object from the given string url
     */
    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem with building URL");
        }
        return url;
    }
}
