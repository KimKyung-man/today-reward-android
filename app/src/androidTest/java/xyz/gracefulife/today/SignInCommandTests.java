package xyz.gracefulife.today;

import android.support.test.runner.AndroidJUnit4;

import com.groupon.grox.Store;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import xyz.gracefulife.api.DataSource;
import xyz.gracefulife.api.remote.Notice;
import xyz.gracefulife.today.notice.FetchNoticeAction;
import xyz.gracefulife.today.notice.FetchNoticeCommand;
import xyz.gracefulife.today.notice.NoticeState;
import xyz.gracefulife.today.notices.FetchNoticesCommand;
import xyz.gracefulife.today.notices.NoticesState;

import static com.groupon.grox.RxStores.states;


@RunWith(AndroidJUnit4.class)
public class SignInCommandTests {
  @Test
  public void 공지_목록조회() {
    // MOCKING
    DataSource<Notice, String> source = new TestDataSource();

    // GIVEN
    Store<NoticesState> store = new Store<>(NoticesState.empty());
    FetchNoticesCommand command = new FetchNoticesCommand(source);
    TestObserver<NoticesState> testSubscriber = new TestObserver<>();

    // WHEN
    states(store).subscribe(testSubscriber);
    command.actions().subscribe(store::dispatch);

    // THEN
    testSubscriber.assertSubscribed();
    testSubscriber.assertValueCount(1);
  }

  @Test
  public void 공지_조회() {
    // MOCKING
    DataSource<Notice, String> source = new TestDataSource();

    // GIVEN
    Store<NoticeState> store = new Store<>(NoticeState.empty());
    FetchNoticeCommand command = new FetchNoticeCommand(source, "1");
    TestObserver<NoticeState> testSubscriber = new TestObserver<>();

    // WHEN
    states(store).subscribe(testSubscriber);
    command.actions().subscribe(store::dispatch);

    // THEN
    testSubscriber.assertSubscribed();
    testSubscriber.assertValueCount(1);

    store.dispatch(new FetchNoticeAction(
        new Notice("2", "title2", "content2", LocalDateTime.now(ZoneId.of("Z")))
    ));

    testSubscriber.assertValueCount(2);
  }

  class TestDataSource implements DataSource<Notice, String> {
    final List<Notice> notices = Collections.singletonList(
        new Notice("1", "title", "content", LocalDateTime.now(ZoneId.of("Z")))
    );

    @Override public Single<Notice> fetch(String s) {
      Notice existNotice = notices.stream()
          .filter(notice -> notice.getId().equals(s))
          .findFirst()
          .orElse(null);

      return existNotice == null ? Single.error(new IllegalStateException("notice not found")) : Single.just(existNotice);
    }

    @Override public Single<List<Notice>> fetchAll() {
      return Single.just(notices);
    }
  }
}
