package com.example.navigation_blancocristian;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ListMealsActivity extends AppCompatActivity {

    private MealsDbHelper dbHelper;
    private MealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_list_meals);

        dbHelper = new MealsDbHelper(this);

        ImageView ivBack = findViewById(R.id.ivBackList);
        ListView listView = findViewById(R.id.listViewAllMeals);
        Button btnSeeDetails = findViewById(R.id.btnListSeeDetails);

        ivBack.setOnClickListener(v -> finish());

        adapter = new MealAdapter(this, dbHelper.getAllMeals());
        listView.setAdapter(adapter);

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
    protected void onResume() {
        super.onResume();
        adapter.updateData(dbHelper.getAllMeals());
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
