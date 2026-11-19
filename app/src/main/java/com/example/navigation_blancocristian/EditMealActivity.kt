package com.example.navigation_blancocristian

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditMealActivity : AppCompatActivity() {

    private lateinit var dbHelper: MealsDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_meal)

        dbHelper = MealsDbHelper(this)

        val ivBack = findViewById<ImageView>(R.id.ivBackEdit)
        val etTitle = findViewById<EditText>(R.id.etEditMealTitle)
        val etCategory = findViewById<EditText>(R.id.etEditCategory)
        val etDescription = findViewById<EditText>(R.id.etEditMealDescription)
        val btnUpdate = findViewById<Button>(R.id.btnUpdateMeal)

        ivBack.setOnClickListener { finish() }

        @Suppress("DEPRECATION")
        val meal = intent.getSerializableExtra("meal") as? Meal
        if (meal != null) {
            etTitle.setText(meal.title)
            etCategory.setText(meal.category)
            etDescription.setText(meal.description)
        }

        btnUpdate.setOnClickListener {
            if (meal == null) return@setOnClickListener

            val newTitle = etTitle.text.toString().trim()
            val newCategory = etCategory.text.toString().trim()
            val newDescription = etDescription.text.toString().trim()

            if (newTitle.isEmpty() || newCategory.isEmpty() || newDescription.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rows = dbHelper.updateMeal(meal.id, newCategory, newTitle, newDescription)
            if (rows > 0) {
                Toast.makeText(this, getString(R.string.meal_modified_successfully), Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, getString(R.string.error_saving), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
