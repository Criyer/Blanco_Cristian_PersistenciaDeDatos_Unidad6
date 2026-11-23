package com.example.navigation_blancocristian;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddMealsActivity extends AppCompatActivity {

    private MealsDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meals);

        dbHelper = new MealsDbHelper(this);

        ImageView ivBack = findViewById(R.id.ivBackAdd);
        EditText etTitle = findViewById(R.id.etMealTitle);
        EditText etCategory = findViewById(R.id.etCategory);
        EditText etDescription = findViewById(R.id.etMealDescription);
        Button btnSave = findViewById(R.id.btnSave);

        ivBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (title.isEmpty() || category.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
                return;
            }

            long id = dbHelper.insertMeal(category, title, description);
            if (id > 0) {
                Toast.makeText(this, getString(R.string.meal_added_successfully), Toast.LENGTH_SHORT).show();
                etTitle.getText().clear();
                etCategory.getText().clear();
                etDescription.getText().clear();
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
