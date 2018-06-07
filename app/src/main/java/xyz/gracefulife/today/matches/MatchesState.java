package xyz.gracefulife.today.matches;

import com.annimon.stream.Optional;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.ToString;
import xyz.gracefulife.api.remote.Match;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@ToString
@AllArgsConstructor
public class MatchesState {
  public static final List<Match> EMPTY = null;

  public final List<Match> matches;
  public final Optional<String> error;
  public final boolean isRefreshing;

  public static MatchesState empty() {
    return new MatchesState(EMPTY, Optional.empty(), false);
  }

  public static MatchesState refreshing() {
    return new MatchesState(EMPTY, Optional.empty(), true);
  }

  public static MatchesState success(List<Match> matches) {
    return new MatchesState(matches, Optional.empty(), true);
  }

  public static MatchesState error(String error) {
    return new MatchesState(EMPTY, Optional.of(error), false);
  }
}
