package xyz.gracefulife.today;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.groupon.grox.Store;

import durdinapps.rxfirebase2.DocumentSnapshotMapper;
import durdinapps.rxfirebase2.RxFirestore;
import io.reactivex.disposables.CompositeDisposable;
import xyz.gracefulife.api.remote.Notice;
import xyz.gracefulife.today.notices.FetchNoticesCommand;
import xyz.gracefulife.today.notices.NoticesState;
import xyz.gracefulife.today.signin.SignInCommand;
import xyz.gracefulife.today.signin.SignInState;

import static com.groupon.grox.RxStores.states;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getSimpleName();

  private final Store<NoticesState> store = new Store<>(NoticesState.empty());
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

    RxFirestore.getCollection(FirebaseFirestore.getInstance().collection("notice"))
        .subscribe(notices -> Log.i(TAG, "onCreate: notices = " + notices.getDocuments()));
    FirebaseFirestore.getInstance().collection("notice")
        .get()
        .addOnCompleteListener(task -> {
          if (task.isSuccessful()) {
            for (DocumentSnapshot document : task.getResult()) {
              Log.d(TAG, document.getId() + " => " + document.getData());
            }
          } else {
            Log.d(TAG, "Error getting documents: ", task.getException());
          }
        });
  }

  private void updateUI(NoticesState noticesState) {
    Log.i(TAG, "updateUI: " + noticesState.notices);
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
