package xyz.gracefulife.today;

import android.support.test.runner.AndroidJUnit4;

import com.groupon.grox.Store;
import com.slmyldz.random.Randoms;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.TestObserver;
import xyz.gracefulife.today.signup.SignUpCommand;
import xyz.gracefulife.today.signup.SignUpState;

import static com.groupon.grox.RxStores.states;


@RunWith(AndroidJUnit4.class)
public class SignUpCommandTests {
  @Test
  public void 가입() {
    // GIVEN
    Store<SignUpState> store = new Store<>(SignUpState.empty());
    SignUpCommand command = new SignUpCommand(Apps.get().getFirebaseAuth(), store.getState());
    TestObserver<SignUpState> testSubscriber = new TestObserver<>();

    // WHEN
    states(store).subscribe(testSubscriber);
    command.actions().subscribe(store::dispatch);
    store.dispatch(oldState ->
        SignUpState.onChangedText(Randoms.email(Apps.get().getApplicationContext()), Randoms.alphaNumericString(10)));

    SignUpCommand availableSignUpCommand = new SignUpCommand(Apps.get().getFirebaseAuth(), store.getState());
    availableSignUpCommand.actions().subscribe(store::dispatch);

    // THEN
    testSubscriber.assertSubscribed();
    testSubscriber.assertValueCount(3);
  }

}
