package com.wily.moviesdbapp.ui.home

import android.view.View
import android.view.ViewParent
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.wily.moviesdbapp.R
import com.wily.moviesdbapp.utils.EspressoIdlingResource
import com.wily.moviesdbapp.utils.FormatedMethod
import com.wily.moviesdbapp.utils.MoviesDataDummy
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    private val dummyMovies = MoviesDataDummy.generateDummyMovies()
    private val dummyTvShows = MoviesDataDummy.generateDummyTvShows()

    private val SEARCH_TITLE = "Wonder Woman 1984"
    private val SEARCH_NAME = "Vikings"


    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)

        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovies.size
            )
        )
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.navigation_tvshows)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTvShows.size
            )
        )
    }

    @Test
    fun loadFavouritesMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(dummyMovies.size, click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).perform(nestedScrollTo(), click())
        onView(withId(R.id.img_btnfavourite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.navigation_favourites)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovies.size
            )
        )
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_firstair_date)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_firstairdate_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_lastair_date)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_lastairdate_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_creator)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_creator_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_type)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_type_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.tv_title)).perform(nestedScrollTo(), click())
        onView(withId(R.id.img_btnfavourite)).perform(click())
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun loadFavouritestvShows() {
        onView(withId(R.id.navigation_tvshows)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(dummyTvShows.size, click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).perform(nestedScrollTo(), click())
        onView(withId(R.id.img_btnfavourite)).perform(click())
        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.navigation_favourites)).perform(click())
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTvShows.size
            )
        )
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_daterelease)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_daterelease_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_director)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_director_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_budget)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_budget_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_revenue)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_revenue_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.tv_title)).perform(nestedScrollTo(), click())
        onView(withId(R.id.img_btnfavourite)).perform(click())
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun loadDetailMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_firstair_date)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_firstairdate_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_lastair_date)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_lastairdate_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_creator)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_creator_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_type)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_type_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))

    }

    @Test
    fun loadDetailTvShows() {
        onView(withId(R.id.navigation_tvshows)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_daterelease)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_daterelease_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_director)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_director_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_budget)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_budget_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_revenue)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_revenue_title)).check(matches(withEffectiveVisibility(Visibility.GONE)))

    }

    @Test
    fun loadSearchMoviesItemFound() {
        onView(withId(R.id.action_search)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(typeText(SEARCH_TITLE), pressImeActionButton())

        onView(withId(R.id.rv_search)).check(matches(isDisplayed()))
    }

    @Test
    fun loadSearchMoviesItemNotFound() {
        // Click on the search icon
        onView(withId(R.id.action_search)).perform(click())

        // Type the text in the search field and submit the query
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("No such item"), pressImeActionButton())

        // Check the empty view is displayed
        onView(withId(R.id.empty_view)).check(matches(isDisplayed()))
    }

    @Test
    fun loadSearchTvShowsItemFound() {
        onView(withId(R.id.navigation_tvshows)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.action_search)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(typeText(SEARCH_NAME), pressImeActionButton())

        onView(withId(R.id.rv_search)).check(matches(isDisplayed()))
    }

    @Test
    fun loadSearchTvShowsItemNotFound() {
        onView(withId(R.id.navigation_tvshows)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.action_search)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(typeText("No such item"), pressImeActionButton())

        onView(withId(R.id.empty_view)).check(matches(isDisplayed()))
    }

    @Test
    fun buttonShareMovies() {
        onView(withId(R.id.rv_movies))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab)).perform(click())
    }

    @Test
    fun buttonShareTvShows() {
        onView(withId(R.id.navigation_tvshows)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab)).perform(click())
    }

    // Scroll to Navigation View
    fun nestedScrollTo(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return Matchers.allOf(
                    isDescendantOfA(isAssignableFrom(NestedScrollView::class.java)),
                    withEffectiveVisibility(Visibility.VISIBLE)
                )
            }

            override fun getDescription(): String {
                return "View is not NestedScrollView"
            }

            override fun perform(uiController: UiController, view: View) {
                try {
                    val nestedScrollView = findFirstParentLayoutOfClass(
                        view,
                        NestedScrollView::class.java
                    ) as NestedScrollView?
                    if (nestedScrollView != null) {
                        nestedScrollView.scrollTo(0, view.top)
                    } else {
                        throw java.lang.Exception("Unable to find NestedScrollView parent.")
                    }
                } catch (e: java.lang.Exception) {
                    throw PerformException.Builder()
                        .withActionDescription(this.description)
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(e)
                        .build()
                }
                uiController.loopMainThreadUntilIdle()
            }
        }
    }

    private fun findFirstParentLayoutOfClass(
        view: View,
        parentClass: Class<out View>
    ): View? {
        var parent: ViewParent = FrameLayout(view.context)
        var incrementView: ViewParent? = null
        var i = 0
        while (parent.javaClass != parentClass) {
            parent = if (i == 0) {
                findParent(view)
            } else {
                findParent(incrementView)
            }
            incrementView = parent
            i++
        }
        return parent as View
    }

    private fun findParent(view: View): ViewParent {
        return view.parent
    }

    private fun findParent(view: ViewParent?): ViewParent {
        return view!!.parent
    }

}