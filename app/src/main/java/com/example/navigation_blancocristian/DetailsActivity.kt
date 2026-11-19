package com.example.navigation_blancocristian

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val ivBack = findViewById<ImageView>(R.id.ivBackDetails)
        val tvTitle = findViewById<TextView>(R.id.tvDetailTitle)
        val tvCategory = findViewById<TextView>(R.id.tvDetailCategory)
        val tvDescription = findViewById<TextView>(R.id.tvDetailDescription)

        ivBack.setOnClickListener { finish() }

        @Suppress("DEPRECATION")
        val meal = intent.getSerializableExtra("meal") as? Meal
        if (meal != null) {
            tvTitle.text = meal.title
            tvCategory.text = meal.category
            tvDescription.text = meal.description
        }
    }
}
