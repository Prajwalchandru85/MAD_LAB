package com.example.lab_8_3;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_8_3.db.MovieDatabaseHelper;
import com.example.lab_8_3.models.Movie;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}

public class MainActivity extends AppCompatActivity {
    private MovieDatabaseHelper dbHelper;
    private ListView movieList;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new MovieDatabaseHelper(this);
        movieList = findViewById(R.id.movieList);
        FloatingActionButton fab = findViewById(R.id.addMovieFab);

        fab.setOnClickListener(v -> showAddMovieDialog());
        loadMovies();

        movieList.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = movies.get(position);
            showMovieDetails(movie);
        });
    }

    private void loadMovies() {
        movies = dbHelper.getAllMovies();
        List<String> movieNames = movies.stream()
                .map(Movie::getName)
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, movieNames);
        movieList.setAdapter(adapter);
    }

    private void saveMovie(Movie movie) {
        dbHelper.insertMovie(movie);
        loadMovies();
    }

    private void showAddMovieDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_movie, null);

        TextInputEditText nameInput = view.findViewById(R.id.movieNameInput);
        TextInputEditText yearInput = view.findViewById(R.id.movieYearInput);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextInputEditText reviewInput = view.findViewById(R.id.movieReviewInput);

        builder.setView(view)
                .setTitle("Add Movie Review")
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = Objects.requireNonNull(nameInput.getText()).toString();
                    int year = Integer.parseInt(Objects.requireNonNull(yearInput.getText()).toString());
                    int rating = (int) ratingBar.getRating();
                    String review = Objects.requireNonNull(reviewInput.getText()).toString();

                    Movie movie = new Movie(name, year, rating, review);
                    saveMovie(movie);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showMovieDetails(Movie movie) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_movie_details, null);

        TextView nameText = view.findViewById(R.id.movieName);
        TextView yearText = view.findViewById(R.id.movieYear);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextView reviewText = view.findViewById(R.id.movieReview);

        nameText.setText(movie.getName());
        yearText.setText(String.valueOf(movie.getYear()));
        ratingBar.setRating(movie.getRating());
        reviewText.setText(movie.getReview());

        builder.setView(view)
                .setTitle("Movie Details")
                .setPositiveButton("Close", null)
                .show();
    }
}