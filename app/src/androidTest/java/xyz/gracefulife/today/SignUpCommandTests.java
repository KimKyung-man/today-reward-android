package xyz.gracefulife.today;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.groupon.grox.Action;
import com.groupon.grox.Store;
import com.slmyldz.random.Randoms;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.TestObserver;
import xyz.gracefulife.today.signup.SignUpAction;
import xyz.gracefulife.today.signup.SignUpCommand;
import xyz.gracefulife.today.signup.SignUpState;

import static com.groupon.grox.RxStores.states;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(AndroidJUnit4.class)
public class SignUpCommandTests {
  @Test
  public void 가입_실패() {
    // GIVEN
    Store<SignUpState> store = new Store<>(SignUpState.empty());
    SignUpCommand command = new SignUpCommand(Apps.get().getFirebaseAuth(), store.getState());
    TestObserver<SignUpState> testSubscriber = new TestObserver<>();

    states(store).subscribe(testSubscriber);
    command.actions().subscribe(store::dispatch);

    // WHEN 1
    String email = Randoms.email(Apps.get().getApplicationContext());
    String password = Randoms.alphaNumericString(10);
    store.dispatch(oldState ->
        SignUpState.onChangedState(email, password, "", "남"));

    final Action signUpFailedAction = new SignUpCommand(Apps.get().getFirebaseAuth(), store.getState()).actions().blockingFirst();
    store.dispatch(signUpFailedAction);

    // THEN 1
    testSubscriber.assertSubscribed();
    assertThat(store.getState().isValid, is(false));
    assertThat(store.getState().error.isPresent(), is(true));
    store.getState().error.ifPresent(s -> assertThat(s, is("Sign-up Failed")));

    // WHEN 2
    store.dispatch(oldState ->
        SignUpState.onChangedState(email, password, password, "남"));
    final Action signUpSuccessAction = new SignUpCommand(Apps.get().getFirebaseAuth(), store.getState()).actions().blockingFirst();
    store.dispatch(signUpSuccessAction);

    // THEN 2
    testSubscriber.assertSubscribed();
    assertThat(store.getState().isValid, is(true));
    assertThat(store.getState().isSuccess, is(true));
  }

  @Test
  public void 가입_성공() {
    // GIVEN
    Store<SignUpState> store = new Store<>(SignUpState.empty());
    SignUpCommand command = new SignUpCommand(Apps.get().getFirebaseAuth(), store.getState());
    TestObserver<SignUpState> testSubscriber = new TestObserver<>();

    states(store).subscribe(testSubscriber);
    command.actions().subscribe(store::dispatch);

    // WHEN
    String email = Randoms.email(Apps.get().getApplicationContext());
    String password = Randoms.alphaNumericString(10);
    store.dispatch(oldState ->
        SignUpState.onChangedState(email, password, password, "남"));
    store.dispatch(oldState -> SignUpState.success(email));

    // THEN
    testSubscriber.assertSubscribed();
    assertThat(store.getState().isValid, is(true));
    assertThat(store.getState().isSuccess, is(true));
  }

}
