package xyz.gracefulife.today;

import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;

public class BaseActivityIdlingResource implements IdlingResource {

  private AppCompatActivity mActivity;
  private ResourceCallback mCallback;

  public BaseActivityIdlingResource(AppCompatActivity activity) {
    mActivity = activity;
  }

  @Override
  public String getName() {
    return "BaseActivityIdlingResource:" + mActivity.getLocalClassName();
  }

  @Override
  public boolean isIdleNow() {
    if (mCallback != null) {
      mCallback.onTransitionToIdle();
    }

    return true;
  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback callback) {
    mCallback = callback;
  }
}
