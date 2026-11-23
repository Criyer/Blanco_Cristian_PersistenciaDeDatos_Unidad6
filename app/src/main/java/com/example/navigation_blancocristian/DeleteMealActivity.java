package com.example.navigation_blancocristian;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteMealActivity extends AppCompatActivity {

    private MealsDbHelper dbHelper;
    private MealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_meal);

        dbHelper = new MealsDbHelper(this);

        ImageView ivBack = findViewById(R.id.ivBackDelete);
        ListView listView = findViewById(R.id.listViewMeals);
        Button btnDelete = findViewById(R.id.btnDeleteAction);

        ivBack.setOnClickListener(v -> finish());

        adapter = new MealAdapter(this, dbHelper.getAllMeals());
        listView.setAdapter(adapter);

        btnDelete.setOnClickListener(v -> {
            Meal selected = adapter.getSelectedMeal();
            if (selected == null) {
                Toast.makeText(this, getString(R.string.select_meal_first), Toast.LENGTH_SHORT).show();
                return;
            }
            int rows = dbHelper.deleteMeal(selected.getId());
            if (rows > 0) {
                Toast.makeText(this, getString(R.string.meal_deleted_successfully), Toast.LENGTH_SHORT).show();
                adapter.updateData(dbHelper.getAllMeals());
            } else {
                Toast.makeText(this, getString(R.string.error_saving), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
