package dev.idee.androidtest

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class CreateTimeCardFragmentTest {

    private lateinit var createTimeCardFragmentScenario: FragmentScenario<CreateTimeCardFragment>
    private lateinit var mockNavController: NavController

    @Before
    fun setup() {
        createTimeCardFragmentScenario = launchFragmentInContainer<CreateTimeCardFragment>(themeResId = R.style.AppTheme)

        MockitoAnnotations.initMocks(this)
        mockNavController = Mockito.mock(NavController::class.java)
        createTimeCardFragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

    }

    @Test
    fun checkStartViews(){

        onView(withId(R.id.projectNameInputLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.rateInputLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.dateLabelText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.startTimeLabel))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.endTimeLabel))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}