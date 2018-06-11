package xyz.gracefulife.today.notices;

import android.util.Log;

import com.groupon.grox.Action;
import com.groupon.grox.commands.rxjava2.Command;

import io.reactivex.Observable;
import lombok.AllArgsConstructor;
import xyz.gracefulife.api.DataSource;
import xyz.gracefulife.api.remote.Notice;

import static io.reactivex.schedulers.Schedulers.io;

@AllArgsConstructor
public class FetchNoticesCommand implements Command {
  private static final String TAG = FetchNoticesCommand.class.getSimpleName();
  private final DataSource<Notice, String> source;

  @Override public Observable<? extends Action> actions() {
    return fetch();
  }

  private Observable<? extends Action> fetch() {
    return source.fetchAll()
        .doOnSuccess(notices -> Log.i(TAG, "fetch: notices = " + notices))
        .subscribeOn(io())
        .map(FetchNoticesAction::new)
        .cast(Action.class)
        .onErrorReturn(FetchNoticesErrorAction::new)
        .toObservable();
  }
}
