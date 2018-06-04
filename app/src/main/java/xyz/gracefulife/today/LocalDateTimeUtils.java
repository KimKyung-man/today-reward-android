package xyz.gracefulife.today;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

public abstract class LocalDateTimeUtils {
  public static LocalDateTime now() {
    return LocalDateTime.now(ZoneId.of("Z"));
  }
}
