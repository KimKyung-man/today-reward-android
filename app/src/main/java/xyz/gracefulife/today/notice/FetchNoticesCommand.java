package xyz.gracefulife.today.notice;

import com.groupon.grox.Action;
import com.groupon.grox.commands.rxjava2.Command;

import io.reactivex.Observable;

public class FetchNoticesCommand implements Command {

  @Override public Observable<? extends Action> actions() {
    return null;
  }
}
