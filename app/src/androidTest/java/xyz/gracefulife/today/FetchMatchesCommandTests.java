package xyz.gracefulife.today;

import android.support.test.espresso.core.internal.deps.guava.collect.Lists;
import android.support.test.runner.AndroidJUnit4;

import com.groupon.grox.Action;
import com.groupon.grox.Store;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import xyz.gracefulife.api.DataSource;
import xyz.gracefulife.api.remote.Match;
import xyz.gracefulife.today.matches.FetchMatchesCommand;
import xyz.gracefulife.today.matches.MatchesState;

import static com.groupon.grox.RxStores.states;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class FetchMatchesCommandTests {
  @Test
  public void 경기_목록조회() {
    // MOCKING
    DataSource<Match, String> source = new TestDataSource();

    // GIVEN
    Store<MatchesState> store = new Store<>(MatchesState.empty());
    FetchMatchesCommand command = new FetchMatchesCommand(source);

    // WHEN
    final Action fetchMatchesAction = command.actions().blockingFirst();
    store.dispatch(fetchMatchesAction);

    // THEN
    assertEquals(states(store).blockingFirst().matches.size(), 1);
    System.out.println(states(store).blockingFirst());
  }

  class TestDataSource implements DataSource<Match, String> {
    final List<Match> matches = Collections.singletonList(
        new Match("1", "title", "content", Lists.newArrayList("삼성", "킹존"), "", ThreeTenUtils.LocalDateUtils.now(), ThreeTenUtils.LocalDateTimeUtils.now())
    );

    @Override public Single<Match> fetch(String s) {
      Match existNotice = matches.stream()
          .filter(match -> match.getId().equals(s))
          .findFirst()
          .orElse(null);

      return existNotice == null ? Single.error(new IllegalStateException("match not found")) : Single.just(existNotice);
    }

    @Override public Single<List<Match>> fetchAll() {
      return Single.just(matches);
    }
  }
}
