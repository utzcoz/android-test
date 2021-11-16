/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.test.services.events.discovery;

import static androidx.test.internal.util.Checks.checkNotNull;

import android.os.Parcel;
import android.support.annotation.NonNull;
import androidx.test.services.events.ErrorInfo;
import androidx.test.services.events.TimeStamp;

/**
 * This event is sent when an error is encountered during test discovery. Multiple {@link
 * TestDiscoveryErrorEvent}s may be reported. This event does not mean discovery has finished.
 */
public class TestDiscoveryErrorEvent extends TestDiscoveryEvent {
  /* The error that occurred */
  @NonNull public final ErrorInfo error;
  /* The time when this error occurred */
  @NonNull public final TimeStamp timeStamp;

  /**
   * Constructor to create {@link TestDiscoveryErrorEvent}.
   *
   * @param error the error that occurred.
   * @param timeStamp the time when this error occurred.
   */
  public TestDiscoveryErrorEvent(@NonNull ErrorInfo error, @NonNull TimeStamp timeStamp) {
    this.error = checkNotNull(error, "error cannot be null");
    this.timeStamp = checkNotNull(timeStamp, "timeStamp cannot be null");
  }

  /**
   * Creates a {@link TestDiscoveryErrorEvent} from an {@link Parcel}.
   *
   * @param source {@link Parcel} to create the {@link TestDiscoveryErrorEvent} from.
   */
  TestDiscoveryErrorEvent(Parcel source) {
    error = new ErrorInfo(source);
    timeStamp = new TimeStamp(source);
  }

  @Override
  EventType instanceType() {
    return EventType.ERROR;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    super.writeToParcel(parcel, i);
    error.writeToParcel(parcel, i);
    timeStamp.writeToParcel(parcel, i);
  }
}
