package com.example.navigation_blancocristian

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddMealsActivity : AppCompatActivity() {

    private lateinit var dbHelper: MealsDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meals)

        dbHelper = MealsDbHelper(this)

        val ivBack = findViewById<ImageView>(R.id.ivBackAdd)
        val etTitle = findViewById<EditText>(R.id.etMealTitle)
        val etCategory = findViewById<EditText>(R.id.etCategory)
        val etDescription = findViewById<EditText>(R.id.etMealDescription)
        val btnSave = findViewById<Button>(R.id.btnSave)

        ivBack.setOnClickListener { finish() }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val category = etCategory.text.toString().trim()
            val description = etDescription.text.toString().trim()

            if (title.isEmpty() || category.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = dbHelper.insertMeal(category, title, description)
            if (id > 0) {
                Toast.makeText(this, getString(R.string.meal_added_successfully), Toast.LENGTH_SHORT).show()
                etTitle.text.clear()
                etCategory.text.clear()
                etDescription.text.clear()
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
