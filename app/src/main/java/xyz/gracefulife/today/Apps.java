package xyz.gracefulife.today;

import android.app.Application;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Supplier;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import durdinapps.rxfirebase2.RxFirestore;
import io.reactivex.Single;
import lombok.Getter;
import xyz.gracefulife.api.Api;
import xyz.gracefulife.api.remote.CommonRemoteSource;
import xyz.gracefulife.api.remote.Faq;
import xyz.gracefulife.api.remote.Notice;

public class Apps extends Application {
  private static Apps instance;

  private Api api;
  private FirebaseFirestore firestore;
  @Getter private FirebaseAuth firebaseAuth;

  @Override public void onCreate() {
    super.onCreate();

    instance = this;
    firestore = FirebaseFirestore.getInstance();
    firebaseAuth = FirebaseAuth.getInstance();
  }

  public static Apps get() {
    return instance;
  }

  public final Api api() {
    return api == null ? api = createApi() : api;
  }

  @SuppressWarnings("unchecked") private Api createApi() {
//    List<DocumentSnapshot> snapshots = documentSnapshots.getDocuments();
//    return Stream.of(snapshots).flatMap(documentSnapshot ->
//        new Notice(documentSnapshot.getId(), documentSnapshot.getString("title"),
//            documentSnapshot.getString("contents"), LocalDateTime.now()))
    Supplier<Single<List<Notice>>> test = RxFirestore.getCollection(firestore.collection("notice"))
        .map(documentSnapshots -> Stream.of(documentSnapshots.getDocuments())
            .map(documentSnapshot -> new Notice(documentSnapshot.getId(), documentSnapshot.getString("title"),
                documentSnapshot.getString("contents"), LocalDateTime.now()))
            .collect(Collectors.toList()))::toSingle;

    return new Api(
        new CommonRemoteSource<>(
            id -> RxFirestore.getDocument(firestore.collection("notice").document(id)).map(doc -> doc.toObject(Notice.class)).toSingle(),
            test
        ),
        new CommonRemoteSource<>(
            id -> RxFirestore.getDocument(firestore.collection("faq").document(id)).map(doc -> doc.toObject(Faq.class)).toSingle(),
            () -> RxFirestore.getCollection(firestore.collection("faq"), Faq.class).toSingle()
        )
    );
  }
}
