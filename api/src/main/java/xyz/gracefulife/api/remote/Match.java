package xyz.gracefulife.api.remote;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match implements Parcelable {
  private String id;
  private String title;
  private String contents;
  private List<String> teams; // teams.
  private String winner; // winner
  private LocalDate matchAt;
  private LocalDateTime createdAt;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.title);
    dest.writeString(this.contents);
    dest.writeStringList(this.teams);
    dest.writeString(this.winner);
    dest.writeSerializable(this.matchAt);
    dest.writeSerializable(this.createdAt);
  }

  protected Match(Parcel in) {
    this.id = in.readString();
    this.title = in.readString();
    this.contents = in.readString();
    this.teams = in.createStringArrayList();
    this.winner = in.readString();
    this.matchAt = (LocalDate) in.readSerializable();
    this.createdAt = (LocalDateTime) in.readSerializable();
  }

  public static final Creator<Match> CREATOR = new Creator<Match>() {
    @Override public Match createFromParcel(Parcel source) {
      return new Match(source);
    }

    @Override public Match[] newArray(int size) {
      return new Match[size];
    }
  };
}
