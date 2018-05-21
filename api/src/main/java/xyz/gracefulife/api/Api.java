package xyz.gracefulife.api;

import android.support.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.gracefulife.api.remote.Faq;
import xyz.gracefulife.api.remote.Notice;

@Getter
@AllArgsConstructor
public class Api {
  @NonNull private final DataSource<Notice, String> notice;
  @NonNull private final DataSource<Faq, String> faq;
}
