package xyz.gracefulife.today.signin;

import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.groupon.grox.Action;
import com.groupon.grox.commands.rxjava2.Command;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import lombok.AllArgsConstructor;

/**
 * 구현의 편리함을 위해 직접 Firebase Auth 와 통신한다.
 */
@AllArgsConstructor
public class SignInCommand implements Command {
  private static final String TAG = SignInCommand.class.getSimpleName();

  private final FirebaseAuth firebaseAuth;
  private final SignInState oldState;

  @Override public Observable<? extends Action> actions() {
    Log.i(TAG, "actions: email is " + oldState.email);
    return RxFirebaseAuth.signInWithEmailAndPassword(firebaseAuth, oldState.email, oldState.password)
        .toObservable()
        .take(1)
        .flatMap((Function<AuthResult, ObservableSource<Action>>) authResult -> {
          if (authResult != null && authResult.getUser() != null) {
            return Observable.just(new SignInAction());
          } else {
            throw new IllegalStateException();
          }
        })
        .onErrorReturn(SignInErrorAction::new);
  }
}
