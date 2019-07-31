package dev.idee.androidtest

import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    private lateinit var instrumentationContext: Context
    private lateinit var loginFragmentScenario:FragmentScenario<LoginFragment>
    private lateinit var mockNavController:NavController

    @Before
    fun setup() {
        loginFragmentScenario = launchFragmentInContainer<LoginFragment>(themeResId = R.style.AppTheme)
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context

        MockitoAnnotations.initMocks(this)
        mockNavController = mock(NavController::class.java)
        loginFragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

    }

    @Test
    fun viewsShowAtStartup(){

        onView(withId(R.id.loginEmailAddressInputLayout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.loginPasswordInputLayout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))

    }

    @Test
    fun completeFieldsOpenNextScreen(){

        onView(withId(R.id.loginEmailAddress))
            .perform(typeText("hi@idee.dev"))

        onView(withId(R.id.loginPassword))
            .perform(typeText("Password4"))

        onView(withId(R.id.loginButton))
            .perform(click())

        verify(mockNavController).navigate(LoginFragmentDirections.actionLoginFragmentToTimeCardListFragment())


    }


}