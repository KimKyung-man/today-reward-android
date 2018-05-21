package xyz.gracefulife.today;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class SignInCommandTests {
  private IdlingResource mActivityResource;

  @Rule
  public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

  @Before
  public void setUp() {
    if (mActivityResource != null) {
      Espresso.unregisterIdlingResources(mActivityResource);
    }

    // Register Activity as idling resource
    mActivityResource = new BaseActivityIdlingResource(mActivityTestRule.getActivity());
    Espresso.registerIdlingResources(mActivityResource);
  }

  @After
  public void tearDown() {
    if (mActivityResource != null) {
      Espresso.unregisterIdlingResources(mActivityResource);
    }
  }

  @Test
  public void 파이어베이스_로그인_테스트() {
    String email = "test@test.com";
    String password = "123456";

  }
}
