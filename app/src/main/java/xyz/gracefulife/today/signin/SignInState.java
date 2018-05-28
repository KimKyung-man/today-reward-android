package xyz.gracefulife.today.signin;

import com.annimon.stream.Optional;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class SignInState {
  public static final String EMPTY = "";

  public final String email;
  public final String password;
  public final Optional<String> error;
  public final boolean isRefreshing;
  public final boolean isSuccess;

  public static SignInState empty() {
    return new SignInState(EMPTY, EMPTY, Optional.empty(), false, false);
  }

  public static SignInState refreshing(String hasEmail) {
    return new SignInState(hasEmail, EMPTY, Optional.empty(), true, false);
  }

  public static SignInState success() {
    return new SignInState(EMPTY, EMPTY, Optional.empty(), false, true);
  }

  public static SignInState error(String hasEmail, String hasPassword, String error) {
    return new SignInState(hasEmail, hasPassword, Optional.of(error), false, false);
  }
}
