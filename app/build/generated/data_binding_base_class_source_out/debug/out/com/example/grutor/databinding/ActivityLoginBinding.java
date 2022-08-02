// Generated by view binder compiler. Do not edit!
package com.example.grutor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnRegister;

  @NonNull
  public final ImageView ivLoginDefault;

  @NonNull
  public final TextView tvGetStarted;

  @NonNull
  public final TextView tvLoginDefault;

  @NonNull
  public final TextView tvSignIn;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnRegister,
      @NonNull ImageView ivLoginDefault, @NonNull TextView tvGetStarted,
      @NonNull TextView tvLoginDefault, @NonNull TextView tvSignIn) {
    this.rootView = rootView;
    this.btnRegister = btnRegister;
    this.ivLoginDefault = ivLoginDefault;
    this.tvGetStarted = tvGetStarted;
    this.tvLoginDefault = tvLoginDefault;
    this.tvSignIn = tvSignIn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnRegister;
      Button btnRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnRegister == null) {
        break missingId;
      }

      id = R.id.ivLoginDefault;
      ImageView ivLoginDefault = ViewBindings.findChildViewById(rootView, id);
      if (ivLoginDefault == null) {
        break missingId;
      }

      id = R.id.tvGetStarted;
      TextView tvGetStarted = ViewBindings.findChildViewById(rootView, id);
      if (tvGetStarted == null) {
        break missingId;
      }

      id = R.id.tvLoginDefault;
      TextView tvLoginDefault = ViewBindings.findChildViewById(rootView, id);
      if (tvLoginDefault == null) {
        break missingId;
      }

      id = R.id.tvSignIn;
      TextView tvSignIn = ViewBindings.findChildViewById(rootView, id);
      if (tvSignIn == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, btnRegister, ivLoginDefault,
          tvGetStarted, tvLoginDefault, tvSignIn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}