// Generated by view binder compiler. Do not edit!
package com.example.grutor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.grutor.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProfileBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final Button btnlogOut;

  @NonNull
  public final ImageView ivBestSubject;

  @NonNull
  public final ImageView ivCurrUserProfile;

  @NonNull
  public final ImageView ivGradeProfile;

  @NonNull
  public final ImageView ivUserAddress;

  @NonNull
  public final TextView tvBestSubjectAnswer;

  @NonNull
  public final TextView tvBestSubjectProfilePrompt;

  @NonNull
  public final TextView tvCurrentGradeAnswer;

  @NonNull
  public final TextView tvCurrentGradePrompt;

  @NonNull
  public final TextView tvProfileName;

  @NonNull
  public final TextView tvUserAddressAnswer;

  @NonNull
  public final TextView tvUserAddressPrompt;

  @NonNull
  public final View view;

  private FragmentProfileBinding(@NonNull FrameLayout rootView, @NonNull Button btnlogOut,
      @NonNull ImageView ivBestSubject, @NonNull ImageView ivCurrUserProfile,
      @NonNull ImageView ivGradeProfile, @NonNull ImageView ivUserAddress,
      @NonNull TextView tvBestSubjectAnswer, @NonNull TextView tvBestSubjectProfilePrompt,
      @NonNull TextView tvCurrentGradeAnswer, @NonNull TextView tvCurrentGradePrompt,
      @NonNull TextView tvProfileName, @NonNull TextView tvUserAddressAnswer,
      @NonNull TextView tvUserAddressPrompt, @NonNull View view) {
    this.rootView = rootView;
    this.btnlogOut = btnlogOut;
    this.ivBestSubject = ivBestSubject;
    this.ivCurrUserProfile = ivCurrUserProfile;
    this.ivGradeProfile = ivGradeProfile;
    this.ivUserAddress = ivUserAddress;
    this.tvBestSubjectAnswer = tvBestSubjectAnswer;
    this.tvBestSubjectProfilePrompt = tvBestSubjectProfilePrompt;
    this.tvCurrentGradeAnswer = tvCurrentGradeAnswer;
    this.tvCurrentGradePrompt = tvCurrentGradePrompt;
    this.tvProfileName = tvProfileName;
    this.tvUserAddressAnswer = tvUserAddressAnswer;
    this.tvUserAddressPrompt = tvUserAddressPrompt;
    this.view = view;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnlogOut;
      Button btnlogOut = ViewBindings.findChildViewById(rootView, id);
      if (btnlogOut == null) {
        break missingId;
      }

      id = R.id.ivBestSubject;
      ImageView ivBestSubject = ViewBindings.findChildViewById(rootView, id);
      if (ivBestSubject == null) {
        break missingId;
      }

      id = R.id.ivCurrUserProfile;
      ImageView ivCurrUserProfile = ViewBindings.findChildViewById(rootView, id);
      if (ivCurrUserProfile == null) {
        break missingId;
      }

      id = R.id.ivGradeProfile;
      ImageView ivGradeProfile = ViewBindings.findChildViewById(rootView, id);
      if (ivGradeProfile == null) {
        break missingId;
      }

      id = R.id.ivUserAddress;
      ImageView ivUserAddress = ViewBindings.findChildViewById(rootView, id);
      if (ivUserAddress == null) {
        break missingId;
      }

      id = R.id.tvBestSubjectAnswer;
      TextView tvBestSubjectAnswer = ViewBindings.findChildViewById(rootView, id);
      if (tvBestSubjectAnswer == null) {
        break missingId;
      }

      id = R.id.tvBestSubjectProfilePrompt;
      TextView tvBestSubjectProfilePrompt = ViewBindings.findChildViewById(rootView, id);
      if (tvBestSubjectProfilePrompt == null) {
        break missingId;
      }

      id = R.id.tvCurrentGradeAnswer;
      TextView tvCurrentGradeAnswer = ViewBindings.findChildViewById(rootView, id);
      if (tvCurrentGradeAnswer == null) {
        break missingId;
      }

      id = R.id.tvCurrentGradePrompt;
      TextView tvCurrentGradePrompt = ViewBindings.findChildViewById(rootView, id);
      if (tvCurrentGradePrompt == null) {
        break missingId;
      }

      id = R.id.tvProfileName;
      TextView tvProfileName = ViewBindings.findChildViewById(rootView, id);
      if (tvProfileName == null) {
        break missingId;
      }

      id = R.id.tvUserAddressAnswer;
      TextView tvUserAddressAnswer = ViewBindings.findChildViewById(rootView, id);
      if (tvUserAddressAnswer == null) {
        break missingId;
      }

      id = R.id.tvUserAddressPrompt;
      TextView tvUserAddressPrompt = ViewBindings.findChildViewById(rootView, id);
      if (tvUserAddressPrompt == null) {
        break missingId;
      }

      id = R.id.view;
      View view = ViewBindings.findChildViewById(rootView, id);
      if (view == null) {
        break missingId;
      }

      return new FragmentProfileBinding((FrameLayout) rootView, btnlogOut, ivBestSubject,
          ivCurrUserProfile, ivGradeProfile, ivUserAddress, tvBestSubjectAnswer,
          tvBestSubjectProfilePrompt, tvCurrentGradeAnswer, tvCurrentGradePrompt, tvProfileName,
          tvUserAddressAnswer, tvUserAddressPrompt, view);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}