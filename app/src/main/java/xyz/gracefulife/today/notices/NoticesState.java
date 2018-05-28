package xyz.gracefulife.today.notices;

import com.annimon.stream.Optional;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.ToString;
import xyz.gracefulife.api.remote.Notice;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@ToString
@AllArgsConstructor
public class NoticesState {
  public static final List<Notice> EMPTY = Collections.emptyList();

  /**
   * FIXME
   * remote 및 local 의 데이터형식이 상이해진다거나, 오프라인 저장을 따로 할 일이 있다면 local 패키지로 옮김
   * 그러나 어차피 firestore 가 오프라인 우선 저장방식을 사용하므로 local-remote 분리는 일어나지 않을 것이다.
   */
  public final List<Notice> notices;
  public final Optional<String> error;
  public final boolean isRefreshing;

  public static NoticesState empty() {
    return new NoticesState(EMPTY, Optional.empty(), false);
  }

  public static NoticesState refreshing() {
    return new NoticesState(EMPTY, Optional.empty(), true);
  }

  public static NoticesState success(List<Notice> notices) {
    return new NoticesState(notices, Optional.empty(), true);
  }

  public static NoticesState error(String error) {
    return new NoticesState(EMPTY, Optional.of(error), false);
  }
}
