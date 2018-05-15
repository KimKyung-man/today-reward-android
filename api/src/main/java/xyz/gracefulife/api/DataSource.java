package xyz.gracefulife.api;


import java.util.List;

import io.reactivex.Single;

public interface DataSource<T, ID> {
  Single<T> fetch(ID id);

  Single<List<T>> fetchAll();
}
