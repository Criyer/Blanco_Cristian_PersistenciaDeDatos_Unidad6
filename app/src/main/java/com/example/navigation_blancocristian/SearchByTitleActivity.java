package com.example.navigation_blancocristian;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchByTitleActivity extends AppCompatActivity {

    private MealsDbHelper dbHelper;
    private MealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_title);

        dbHelper = new MealsDbHelper(this);

        ImageView ivBack = findViewById(R.id.ivBackSearch);
        EditText etQuery = findViewById(R.id.etSearchQuery);
        ListView listView = findViewById(R.id.listViewSearchResults);
        Button btnSearch = findViewById(R.id.btnSearchAction);
        Button btnSeeDetails = findViewById(R.id.btnSeeDetails);

        ivBack.setOnClickListener(v -> finish());

        adapter = new MealAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String query = etQuery.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
                return;
            }
            List<Meal> results = dbHelper.getMealsByTitle(query);
            if (results.isEmpty()) {
                Toast.makeText(this, getString(R.string.no_meals_found), Toast.LENGTH_SHORT).show();
            }
            adapter.updateData(results);
        });

        btnSeeDetails.setOnClickListener(v -> {
            Meal selected = adapter.getSelectedMeal();
            if (selected == null) {
                Toast.makeText(this, getString(R.string.select_meal_first), Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("meal", selected);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
