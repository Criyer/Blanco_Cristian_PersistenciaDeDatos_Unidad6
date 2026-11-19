package com.example.navigation_blancocristian

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import android.widget.TextView

class MealAdapter(
    private val context: Context,
    private var meals: List<Meal>,
    private val onItemSelected: ((Meal) -> Unit)? = null
) : BaseAdapter() {

    private var selectedPosition = -1

    fun updateData(newMeals: List<Meal>) {
        meals = newMeals
        selectedPosition = -1
        notifyDataSetChanged()
    }

    fun getSelectedMeal(): Meal? {
        return if (selectedPosition in meals.indices) meals[selectedPosition] else null
    }

    override fun getCount(): Int = meals.size

    override fun getItem(position: Int): Meal = meals[position]

    override fun getItemId(position: Int): Long = meals[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false)
        val meal = getItem(position)

        val tvTitle = view.findViewById<TextView>(R.id.tvMealItemTitle)
        val tvCategory = view.findViewById<TextView>(R.id.tvMealItemCategory)
        val rbSelect = view.findViewById<RadioButton>(R.id.rbMealItemSelect)

        tvTitle.text = meal.title
        tvCategory.text = meal.category
        rbSelect.isChecked = (position == selectedPosition)

        val selectListener = View.OnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
            onItemSelected?.invoke(meal)
        }

        view.setOnClickListener(selectListener)
        rbSelect.setOnClickListener(selectListener)

        return view
    }
}
