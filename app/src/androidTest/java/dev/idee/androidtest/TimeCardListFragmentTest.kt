package dev.idee.androidtest

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class TimeCardListFragmentTest {

    private lateinit var timeCardListFragmentScenario: FragmentScenario<TimeCardListFragment>
    private lateinit var mockNavController: NavController

    @Before
    fun setup() {
        timeCardListFragmentScenario = launchFragmentInContainer<TimeCardListFragment>(themeResId = R.style.AppTheme)

        MockitoAnnotations.initMocks(this)
        mockNavController = Mockito.mock(NavController::class.java)
        timeCardListFragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

    }

    @Test
    fun checkStartViews(){

        onView(withId(R.id.noDataTextView))
            .check(matches(isDisplayed()))

    }

    @Test
    fun clickingFABOpensCreateTimeCardFragment() {

        onView(withId(R.id.addNewTimeCardFab))
            .perform(click())

        verify(mockNavController)
            .navigate(
                TimeCardListFragmentDirections.actionTimeCardListFragmentToHomeFragment(
                    TimeCardModel(
                        "",
                        0.0,
                        "",
                        0.0,
                        "",
                        "",""
                    ), false
                )
            )


    }


}