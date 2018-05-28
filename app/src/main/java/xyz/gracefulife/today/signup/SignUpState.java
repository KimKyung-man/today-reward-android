package xyz.gracefulife.today.signup;

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
public class SignUpState {
  public static final String EMPTY = "";

  public final String email;
  public final String password;
  public final Optional<String> error;
  public final boolean isRefreshing;
  public final boolean isSuccess;

  public static SignUpState empty() {
    return new SignUpState(EMPTY, EMPTY, Optional.empty(), false, false);
  }

  public static SignUpState refreshing(String hasEmail) {
    return new SignUpState(hasEmail, EMPTY, Optional.empty(), true, false);
  }

  public static SignUpState onChangedText(String email, String password) {
    return new SignUpState(email, password, Optional.empty(), true, false);
  }

  public static SignUpState success(String successEmail) {
    return new SignUpState(successEmail, EMPTY, Optional.empty(), false, true);
  }

  public static SignUpState error(String hasEmail, String hasPassword, String error) {
    return new SignUpState(hasEmail, hasPassword, Optional.of(error), false, false);
  }
}
