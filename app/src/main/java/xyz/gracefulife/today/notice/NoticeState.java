package xyz.gracefulife.today.notice;

import com.annimon.stream.Optional;

import lombok.AllArgsConstructor;
import lombok.ToString;
import xyz.gracefulife.api.remote.Notice;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@ToString
@AllArgsConstructor
public class NoticeState {
  public static final Notice EMPTY = null;

  public final Notice notice;
  public final Optional<String> error;
  public final boolean isRefreshing;

  public static NoticeState empty() {
    return new NoticeState(EMPTY, Optional.empty(), false);
  }

  public static NoticeState refreshing() {
    return new NoticeState(EMPTY, Optional.empty(), true);
  }

  public static NoticeState success(Notice notice) {
    return new NoticeState(notice, Optional.empty(), true);
  }

  public static NoticeState error(String error) {
    return new NoticeState(EMPTY, Optional.of(error), false);
  }
}
