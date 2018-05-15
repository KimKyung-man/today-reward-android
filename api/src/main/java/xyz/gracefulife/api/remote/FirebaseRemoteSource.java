package xyz.gracefulife.api.remote;

import com.annimon.stream.function.Supplier;

import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.NonNull;
import xyz.gracefulife.api.DataSource;

/**
 * TODO 더 좋은 방안을 생각해보기.
 * <p>
 * 파이어베이스의 경우 메인 모듈에 종속성이 있으므로, 페치 등의 함수만 가져와서,
 * 실행만 이쪽 데이터소스를 이용하는 것으로 한다.
 */
public class FirebaseRemoteSource implements DataSource<Notice> {
  private Supplier<Single<Notice>> singleItemSupplier;
  private Supplier<Observable<Notice>> multipleItemSupplier;

  private FirebaseRemoteSource() {
  }

  public FirebaseRemoteSource(
      @NonNull Supplier<Single<Notice>> singleItemSupplier,
      @NonNull Supplier<Observable<Notice>> multipleItemSupplier) {
    this.singleItemSupplier = singleItemSupplier;
    this.multipleItemSupplier = multipleItemSupplier;
  }

  @Override public Single<Notice> fetch() {
    return singleItemSupplier.get();
  }

  @Override public Observable<Notice> fetchAll() {
    return multipleItemSupplier.get();
  }
}
