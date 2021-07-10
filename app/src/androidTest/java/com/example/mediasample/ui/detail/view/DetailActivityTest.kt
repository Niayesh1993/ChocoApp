package com.example.mediasample.ui.detail.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.example.mediasample.R
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test


class DetailActivityTest : TestCase(){
    @get:Rule
    val activityRule = ActivityTestRule(DetailActivity::class.java, false, false)

    @Test
    fun onClickAddButton() {
        ActivityScenario.launch(DetailActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite_btn)).perform(ViewActions.click())
    }

    @Test
    fun onClicRemoveButton() {
        ActivityScenario.launch(DetailActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.remove_favorite_btn)).perform(ViewActions.click())
    }
}