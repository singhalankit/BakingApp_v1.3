package com.example.ankit_pc.bakingappudacity;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest  {
    private MainActivity  mActivity ;
    boolean mScreenWidth;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setmActivity (){
        mActivity = mActivityTestRule.getActivity();
         mScreenWidth = isScreenSw600dp();

    }



    @Test
    public void mainActivityTest() {

        if (mScreenWidth) {

            Log.v("Entered into tablet", (String.valueOf(mScreenWidth)));

            ViewInteraction appCompatTextView = onView(
                    allOf(withId(R.id.recipe_TextView), withText("Nutella Pie"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.support.v7.widget.CardView")),
                                            1),
                                    0),
                            isDisplayed()));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            appCompatTextView.perform(click());

            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.include_recipe_step_list),
                            childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")),
                                    0)));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            recyclerView.perform(actionOnItemAtPosition(0, click()));

            ViewInteraction recyclerView2 = onView(
                    allOf(withId(R.id.include_recipe_step_list),
                            childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")),
                                    0)));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recyclerView2.perform(actionOnItemAtPosition(2, click()));

            ViewInteraction appCompatImageButton = onView(
                    allOf(withContentDescription("Navigate up"),
                            childAtPosition(
                                    allOf(withId(R.id.toolbar),
                                            childAtPosition(
                                                    withId(R.id.app_bar),
                                                    0)),
                                    1),
                            isDisplayed()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            appCompatImageButton.perform(click());
        }

        else {
            Log.v("Entered into phone",String.valueOf(mScreenWidth));

            ViewInteraction appCompatTextView = onView(
                    allOf(withId(R.id.recipe_TextView), withText("Nutella Pie"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.support.v7.widget.CardView")),
                                            1),
                                    0),
                            isDisplayed()));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            appCompatTextView.perform(click());

            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.include_recipe_step_list),
                            childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")),
                                    0)));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            recyclerView.perform(actionOnItemAtPosition(0, click()));


            ViewInteraction appCompatImageButton = onView(
                    allOf(withContentDescription("Navigate up"),
                            childAtPosition(
                                    allOf(withId(R.id.detail_toolbar),
                                            childAtPosition(
                                                    withId(R.id.app_bar),
                                                    0)),
                                    1),
                            isDisplayed()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            appCompatImageButton.perform(click());


            ViewInteraction appCompatImageButton2 = onView(
                    allOf(withContentDescription("Navigate up"),
                            childAtPosition(
                                    allOf(withId(R.id.toolbar),
                                            childAtPosition(
                                                    withId(R.id.app_bar),
                                                    0)),
                                    1),
                            isDisplayed()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            appCompatImageButton2.perform(click());




        }

    }



    public boolean isScreenSw600dp() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        //mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayMetrics = mActivity.getResources().getDisplayMetrics();
        float widthDp = displayMetrics.widthPixels / displayMetrics.density;
        float heightDp = displayMetrics.heightPixels / displayMetrics.density;
        float screenSw = Math.min(widthDp, heightDp);
        return screenSw >= 600;
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }




}
