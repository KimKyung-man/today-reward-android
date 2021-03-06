package xyz.gracefulife.api.remote;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice implements Parcelable {
  private String id;
  private String title;
  private String contents;
  private LocalDateTime createdAt;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.title);
    dest.writeString(this.contents);
    dest.writeSerializable(this.createdAt);
  }

  protected Notice(Parcel in) {
    this.id = in.readString();
    this.title = in.readString();
    this.contents = in.readString();
    this.createdAt = (LocalDateTime) in.readSerializable();
  }

  public static final Creator<Notice> CREATOR = new Creator<Notice>() {
    @Override public Notice createFromParcel(Parcel source) {
      return new Notice(source);
    }

    @Override public Notice[] newArray(int size) {
      return new Notice[size];
    }
  };
}
