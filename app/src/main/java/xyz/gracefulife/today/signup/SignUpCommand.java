package xyz.gracefulife.today.signup;

import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.groupon.grox.Action;
import com.groupon.grox.commands.rxjava2.Command;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 구현의 편리함을 위해 직접 Firebase Auth 와 통신한다.
 */
@AllArgsConstructor
public class SignUpCommand implements Command {
  private static final String TAG = SignUpCommand.class.getSimpleName();

  private final FirebaseAuth firebaseAuth;
  private final SignUpState oldState;

  @Override public Observable<? extends Action> actions() {
    return Observable.just(oldState.isValid)
        .map(valid -> {
          if (!valid) {
            throw new IllegalStateException("state not valid");
          }
          return true;
        })
        .flatMap($ -> RxFirebaseAuth.createUserWithEmailAndPassword(firebaseAuth, oldState.email, oldState.password)
            .defaultIfEmpty(new EmptyResult())
            .toObservable()
            .take(1))
        .onErrorReturn(throwable -> new EmptyResult())
        .flatMap((Function<AuthResult, ObservableSource<Action>>) authResult -> {
          if (authResult.getUser() != null) {
            return Observable.just(new SignUpAction());
          } else {
            return Observable.error(new IllegalStateException("Sign-up Failed"));
          }
        })
        .onErrorReturn(SignUpErrorAction::new);
  }

  @NoArgsConstructor
  public static class EmptyResult implements AuthResult {
    @Override public FirebaseUser getUser() {
      return null;
    }

    @Override public AdditionalUserInfo getAdditionalUserInfo() {
      return null;
    }
  }
}
