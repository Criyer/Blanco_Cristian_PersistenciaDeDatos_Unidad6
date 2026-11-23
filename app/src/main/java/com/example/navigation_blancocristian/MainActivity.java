package com.example.navigation_blancocristian;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddMeals = findViewById(R.id.btnAddMeals);
        Button btnDeleteMeals = findViewById(R.id.btnDeleteMeals);
        Button btnListAllMeals = findViewById(R.id.btnListAllMeals);
        Button btnListMealsByTitle = findViewById(R.id.btnListMealsByTitle);
        Button btnModifyMeal = findViewById(R.id.btnModifyMeal);

        btnAddMeals.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddMealsActivity.class))
        );

        btnDeleteMeals.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DeleteMealActivity.class))
        );

        btnListAllMeals.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ListMealsActivity.class))
        );

        btnListMealsByTitle.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SearchByTitleActivity.class))
        );

        btnModifyMeal.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ModifyMealListActivity.class))
        );
    }
}
