package xyz.gracefulife.api;


import io.reactivex.Observable;
import io.reactivex.Single;

public interface DataSource<T> {
  Single<T> fetch();

  Observable<T> fetchAll();
}
