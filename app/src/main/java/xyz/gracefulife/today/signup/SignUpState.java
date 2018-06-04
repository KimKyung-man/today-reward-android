package xyz.gracefulife.today.signup;

import android.text.TextUtils;

import com.annimon.stream.Optional;

import org.threeten.bp.LocalDateTime;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static xyz.gracefulife.today.LocalDateTimeUtils.now;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class SignUpState {
  private static final String TAG = SignUpState.class.getSimpleName();
  public static final String EMPTY = "";

  public final String email;
  public final String password;
  public final String passwordConfirm;
  public final String sex; // 남 or 여
  public final LocalDateTime birth;
  public final Optional<String> error;
  public final boolean isRefreshing;
  public final boolean isSuccess;
  public final boolean isValid;

  public static SignUpState empty() {
    return new SignUpState(EMPTY, EMPTY, EMPTY, EMPTY, now(), Optional.empty(), false, false, false);
  }

  public static SignUpState refreshing(String hasEmail) {
    return new SignUpState(hasEmail, EMPTY, EMPTY, EMPTY, now(), Optional.empty(), true, false, false);
  }

  public static SignUpState onChangedState(String email, String password, String passwordConfirm, String sex) {
    return new SignUpState(email, password, passwordConfirm, sex, now(), Optional.empty(), true, false, assertValid(email, password, passwordConfirm, sex));
  }

  public static SignUpState success(String successEmail) {
    return new SignUpState(successEmail, EMPTY, EMPTY, EMPTY, now(), Optional.empty(), false, true, true);
  }

  public static SignUpState error(String hasEmail, String hasPassword, String hasSex, String error) {
    return new SignUpState(hasEmail, hasPassword, EMPTY, hasSex, now(), Optional.of(error), false, false, false);
  }

  private static boolean assertValid(String email, String password, String passwordConfirm, String sex) {
    boolean empty = TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm) || TextUtils.isEmpty(sex);
    if (empty) {
      return false;
    }

    boolean isNotValidEmail = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    if (isNotValidEmail) {
      return false;
    }

    boolean isNotMatchedPassword = !Objects.equals(password, passwordConfirm);
    if (isNotMatchedPassword) {
      return false;
    }

    return true;
  }
}
