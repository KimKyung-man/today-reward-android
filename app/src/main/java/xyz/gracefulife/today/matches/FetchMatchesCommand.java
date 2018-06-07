package xyz.gracefulife.today.matches;

import com.groupon.grox.Action;
import com.groupon.grox.commands.rxjava2.Command;

import io.reactivex.Observable;
import lombok.AllArgsConstructor;
import xyz.gracefulife.api.DataSource;
import xyz.gracefulife.api.remote.Match;

import static io.reactivex.schedulers.Schedulers.io;

@AllArgsConstructor
public class FetchMatchesCommand implements Command {
  private final DataSource<Match, String> source;

  @Override public Observable<? extends Action> actions() {
    return fetch();
  }

  private Observable<? extends Action> fetch() {
    return source.fetchAll()
        .subscribeOn(io())
        .map(FetchMatchesAction::new)
        .cast(Action.class)
        .onErrorReturn(FetchMatchesErrorAction::new)
        .toObservable();
  }
}
