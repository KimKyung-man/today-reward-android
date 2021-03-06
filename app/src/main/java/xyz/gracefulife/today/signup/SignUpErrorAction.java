/*
 * Copyright (c) 2017, Groupon, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.gracefulife.today.signup;

import com.groupon.grox.Action;

import lombok.Getter;
import lombok.ToString;

@ToString
public class SignUpErrorAction implements Action<SignUpState> {
  private static final String TAG = SignUpErrorAction.class.getSimpleName();

  @Getter private String msg;

  public SignUpErrorAction(Throwable error) {
    this.msg = error.getMessage();
  }

  @Override
  public SignUpState newState(SignUpState oldState) {
    return SignUpState.error(oldState.email, oldState.password, oldState.sex, msg);
  }
}
