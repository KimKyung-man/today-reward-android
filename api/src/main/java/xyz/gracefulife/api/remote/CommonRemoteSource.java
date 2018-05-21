package xyz.gracefulife.api.remote;

import com.annimon.stream.function.Function;
import com.annimon.stream.function.Supplier;

import java.util.List;

import io.reactivex.Single;
import lombok.AllArgsConstructor;
import xyz.gracefulife.api.DataSource;

@AllArgsConstructor
public class CommonRemoteSource<T, ID> implements DataSource<T, ID> {
  private final Function<ID, Single<T>> singleItemSupplier;
  private final Supplier<Single<List<T>>> multipleItemSupplier;

  @Override public Single<T> fetch(ID id) {
    return singleItemSupplier.apply(id);
  }

  @Override public Single<List<T>> fetchAll() {
    return multipleItemSupplier.get();
  }
}
