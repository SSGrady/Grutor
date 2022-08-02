// Generated by view binder compiler. Do not edit!
package com.example.grutor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.grutor.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignInBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnSignIn;

  @NonNull
  public final EditText etPasswordLogin;

  @NonNull
  public final EditText etUserLogin;

  @NonNull
  public final TextView tvSignIn;

  private ActivitySignInBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnSignIn,
      @NonNull EditText etPasswordLogin, @NonNull EditText etUserLogin,
      @NonNull TextView tvSignIn) {
    this.rootView = rootView;
    this.btnSignIn = btnSignIn;
    this.etPasswordLogin = etPasswordLogin;
    this.etUserLogin = etUserLogin;
    this.tvSignIn = tvSignIn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignInBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignInBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_sign_in, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignInBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnSignIn;
      Button btnSignIn = ViewBindings.findChildViewById(rootView, id);
      if (btnSignIn == null) {
        break missingId;
      }

      id = R.id.etPasswordLogin;
      EditText etPasswordLogin = ViewBindings.findChildViewById(rootView, id);
      if (etPasswordLogin == null) {
        break missingId;
      }

      id = R.id.etUserLogin;
      EditText etUserLogin = ViewBindings.findChildViewById(rootView, id);
      if (etUserLogin == null) {
        break missingId;
      }

      id = R.id.tvSignIn;
      TextView tvSignIn = ViewBindings.findChildViewById(rootView, id);
      if (tvSignIn == null) {
        break missingId;
      }

      return new ActivitySignInBinding((ConstraintLayout) rootView, btnSignIn, etPasswordLogin,
          etUserLogin, tvSignIn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
