package xyz.gracefulife.today;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;

import durdinapps.rxfirebase2.RxFirestore;
import xyz.gracefulife.api.Api;
import xyz.gracefulife.api.remote.FirebaseRemoteSource;
import xyz.gracefulife.api.remote.Notice;

public class Apps extends Application {
  private static Apps instance;

  private Api api;
  private FirebaseFirestore firestore;

  @Override public void onCreate() {
    super.onCreate();

    instance = this;
    firestore = FirebaseFirestore.getInstance();
  }

  public static Apps get() {
    return instance;
  }

  public final Api api() {
    return api == null ? api = createApi() : api;
  }

  private Api createApi() {
    return new Api(
        new FirebaseRemoteSource(
            id -> RxFirestore.getDocument(firestore.collection("notice").document(id)).map(doc -> doc.toObject(Notice.class)).toSingle(),
            () -> RxFirestore.getCollection(firestore.collection("notice"), Notice.class).toSingle()
        )
    );
  }
}
