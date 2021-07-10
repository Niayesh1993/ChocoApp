package com.example.mediasample.ui.main.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.example.mediasample.R
import junit.framework.TestCase
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import java.util.*


class MainActivityTest : TestCase() {

    public override fun setUp() {
        super.setUp()
    }

    public override fun tearDown() {}

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, true)

    //Test for Visibility
    @Test
    @Throws(InterruptedException::class)
    fun testVisibilityRecyclerView() {
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.recycler))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    //Test for Scrolling
    @Test
    @Throws(InterruptedException::class)
    fun testCaseForRecyclerScroll() {
        Thread.sleep(1000)
        val recyclerView =
            activityRule.activity.findViewById<RecyclerView>(R.id.recycler)
        val itemCount =
            Objects.requireNonNull(recyclerView.adapter!!).itemCount
        Espresso.onView(ViewMatchers.withId(R.id.recycler))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

    //Test for RecyclerView Click
    @Test
    @Throws(InterruptedException::class)
    fun testCaseForRecyclerClick() {
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.recycler))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
    }
    

}