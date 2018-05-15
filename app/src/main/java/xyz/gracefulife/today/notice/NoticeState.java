package xyz.gracefulife.today.notice;

import com.annimon.stream.Optional;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import xyz.gracefulife.api.remote.Notice;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@AllArgsConstructor
public class NoticeState {
  public static final List<Notice> EMPTY = Collections.emptyList();

  /**
   * FIXME
   * remote 및 local 의 데이터형식이 상이해진다거나, 오프라인 저장을 따로 할 일이 있다면 local 패키지로 옮김
   * 그러나 어차피 firestore 가 오프라인 우선 저장방식을 사용하므로 local-remote 분리는 일어나지 않을 것이다.
   */
  public final List<Notice> notices;
  public final Optional<String> error;
  public final boolean isRefreshing;

  public static NoticeState empty() {
    return new NoticeState(EMPTY, Optional.empty(), false);
  }

  public static NoticeState refreshing() {
    return new NoticeState(EMPTY, Optional.empty(), true);
  }

  public static NoticeState success(List<Notice> notices) {
    return new NoticeState(notices, Optional.empty(), true);
  }

  public static NoticeState error(String error) {
    return new NoticeState(EMPTY, Optional.of(error), false);
  }
}
