package xyz.gracefulife.today.notice;

import com.groupon.grox.Action;
import com.groupon.grox.commands.rxjava2.Command;

import io.reactivex.Observable;
import lombok.AllArgsConstructor;
import xyz.gracefulife.api.DataSource;
import xyz.gracefulife.api.remote.Notice;

import static io.reactivex.schedulers.Schedulers.io;

@AllArgsConstructor
public class FetchNoticeCommand implements Command {
  private final DataSource<Notice, String> source;
  private final String id;

  @Override public Observable<? extends Action> actions() {
    return fetch(id);
  }

  private Observable<? extends Action> fetch(final String id) {
    return source.fetch(id)
        .subscribeOn(io())
        .map(FetchNoticeAction::new)
        .cast(Action.class)
        .onErrorReturn(FetchNoticeErrorAction::new)
        .toObservable();
  }
}
