package com.sphtech

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.sphtech.ui.main.MainActivity
import com.sphtech.ui.main.MainPresenter
import com.sphtech.utils.RecyclerViewMatcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)
    private val fetchingIdlingResource = FetchingIdlingResource()

    @Before
    fun setup() {
        activityRule.launchActivity(null)
        IdlingRegistry.getInstance().register(fetchingIdlingResource)
        activityRule.activity.registerIdling(fetchingIdlingResource)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.sphtech", appContext.packageName)
    }

    @Test
    fun testContentTop() {
        val callback = IdlingResource.ResourceCallback {
            onView(withRecyclerView(R.id.rc_list).atPosition(-1))
                .check(matches(hasDescendant(withText(MainPresenter.TOP))))
        }
        fetchingIdlingResource.registerIdleTransitionCallback(callback)
        // Clean up
        IdlingRegistry.getInstance().unregister(fetchingIdlingResource)

    }

    @Test
    fun testContentBottom() {
        val callback = IdlingResource.ResourceCallback {
            onView(withRecyclerView(R.id.rc_list).atPosition(0))
                .check(matches(hasDescendant(withText(MainPresenter.BOTTOM))))
        }
        fetchingIdlingResource.registerIdleTransitionCallback(callback)
        // Clean up
        IdlingRegistry.getInstance().unregister(fetchingIdlingResource)

    }

    // Convenience helper
    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }
}
