package xyz.gracefulife.today;

import android.support.test.runner.AndroidJUnit4;

import com.groupon.grox.Action;
import com.groupon.grox.Store;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.TestObserver;
import xyz.gracefulife.today.signin.SignInCommand;
import xyz.gracefulife.today.signin.SignInState;
import xyz.gracefulife.today.signup.SignUpCommand;
import xyz.gracefulife.today.signup.SignUpState;

import static com.groupon.grox.RxStores.states;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static xyz.gracefulife.today.signin.SignInState.EMPTY;


@RunWith(AndroidJUnit4.class)
public class SignInCommandTests {
  @Test
  public void 로그인() {
    // GIVEN
    Store<SignInState> store = new Store<>(SignInState.empty());
    TestObserver<SignInState> testSubscriber = new TestObserver<>();

    states(store).subscribe(testSubscriber);

    // WHEN
    final Action signInOnErrorActions = new SignInCommand(Apps.get().getFirebaseAuth(), store.getState()).actions().blockingFirst();
    store.dispatch(signInOnErrorActions);

    // THEN
    assertThat(store.getState().error.isPresent(), is(true));

    // WHEN 2
    store.dispatch(oldState ->
        SignInState.onChangedState("test333@naver.com", "123456"));

    final Action signInSuccessAction = new SignInCommand(Apps.get().getFirebaseAuth(), store.getState()).actions().blockingFirst();
    store.dispatch(signInSuccessAction);

    // THEN 2
    testSubscriber.assertSubscribed();
    assertThat(store.getState().isSuccess, is(true));
  }
}
