package com.example.navigation_blancocristian;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditMealActivity extends AppCompatActivity {

    private MealsDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal);

        dbHelper = new MealsDbHelper(this);

        ImageView ivBack = findViewById(R.id.ivBackEdit);
        EditText etTitle = findViewById(R.id.etEditMealTitle);
        EditText etCategory = findViewById(R.id.etEditCategory);
        EditText etDescription = findViewById(R.id.etEditMealDescription);
        Button btnUpdate = findViewById(R.id.btnUpdateMeal);

        ivBack.setOnClickListener(v -> finish());

        Meal meal = androidx.core.content.IntentCompat.getSerializableExtra(getIntent(), "meal", Meal.class);
        if (meal != null) {
            etTitle.setText(meal.getTitle());
            etCategory.setText(meal.getCategory());
            etDescription.setText(meal.getDescription());
        }

        btnUpdate.setOnClickListener(v -> {
            if (meal == null) return;

            String newTitle = etTitle.getText().toString().trim();
            String newCategory = etCategory.getText().toString().trim();
            String newDescription = etDescription.getText().toString().trim();

            if (newTitle.isEmpty() || newCategory.isEmpty() || newDescription.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
                return;
            }

            int rows = dbHelper.updateMeal(meal.getId(), newCategory, newTitle, newDescription);
            if (rows > 0) {
                Toast.makeText(this, getString(R.string.meal_modified_successfully), Toast.LENGTH_SHORT).show();
                finish();
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
