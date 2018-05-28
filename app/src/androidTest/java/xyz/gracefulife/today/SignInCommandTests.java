package xyz.gracefulife.today;

import android.support.test.runner.AndroidJUnit4;

import com.groupon.grox.Store;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.TestObserver;
import xyz.gracefulife.today.signin.SignInCommand;
import xyz.gracefulife.today.signin.SignInState;

import static com.groupon.grox.RxStores.states;
import static xyz.gracefulife.today.signin.SignInState.EMPTY;


@RunWith(AndroidJUnit4.class)
public class SignInCommandTests {
  @Test
  public void 로그인() {
    // GIVEN
    Store<SignInState> store = new Store<>(SignInState.empty());
    SignInCommand command = new SignInCommand(Apps.get().getFirebaseAuth(), store.getState());
    TestObserver<SignInState> testSubscriber = new TestObserver<>();

    // WHEN
    states(store).subscribe(testSubscriber);
    command.actions().subscribe(store::dispatch);

    // THEN
    testSubscriber.assertSubscribed();
    testSubscriber.assertValueCount(2); // FIXME 얘는 첫 값도 방출하는데, fetch notices 는 그렇지 않다. 리서치 필요
    testSubscriber.assertValues(
        SignInState.empty(),
        SignInState.error(
            EMPTY, EMPTY, "user not found"
        ));
  }

}
