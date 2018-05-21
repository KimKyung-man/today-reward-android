package xyz.gracefulife.today;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.groupon.grox.Store;

import io.reactivex.disposables.CompositeDisposable;
import xyz.gracefulife.today.notice.FetchNoticesCommand;
import xyz.gracefulife.today.notice.NoticeState;
import xyz.gracefulife.today.signin.SignInCommand;
import xyz.gracefulife.today.signin.SignInState;

import static com.groupon.grox.RxStores.states;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getSimpleName();

  private final Store<NoticeState> store = new Store<>(NoticeState.empty());
  private final Store<SignInState> signInTestStore = new Store<>(SignInState.empty());
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", v -> new SignInCommand(Apps.get().getFirebaseAuth(), signInTestStore.getState()).actions()
            .subscribe(o -> Log.i(TAG, "onCreate: o = " + o))).show());

    compositeDisposable.add(states(store).observeOn(mainThread()).subscribe(this::updateUI, this::doLog));
    // perform action
    compositeDisposable.add(
        new FetchNoticesCommand(Apps.get().api().getNotice())
            .actions()
            .subscribe(store::dispatch, this::doLog)
    );
  }

  private void updateUI(NoticeState noticeState) {
    Log.i(TAG, "updateUI: " + noticeState.notices);
  }

  private void doLog(Throwable throwable) {
    Log.e(TAG, "doLog: throwable = ", throwable);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
