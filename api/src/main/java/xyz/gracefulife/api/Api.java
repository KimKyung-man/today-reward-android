package xyz.gracefulife.api;

import android.support.annotation.NonNull;

import lombok.Getter;
import xyz.gracefulife.api.remote.FirebaseRemoteSource;

@Getter
public class Api {
  private final FirebaseRemoteSource firebase;

  public Api(@NonNull FirebaseRemoteSource firebaseRemoteSource) {
    this.firebase = firebaseRemoteSource;
  }
}
