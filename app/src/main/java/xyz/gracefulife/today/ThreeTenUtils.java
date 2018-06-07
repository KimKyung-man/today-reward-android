package xyz.gracefulife.today;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

public abstract class ThreeTenUtils {
  public static abstract class LocalDateTimeUtils {
    public static LocalDateTime now() {
      return LocalDateTime.now(ZoneId.of("Z"));
    }
  }

  public static abstract class LocalDateUtils {
    public static LocalDate now() {
      return LocalDate.now(ZoneId.of("Z"));
    }
  }
}
