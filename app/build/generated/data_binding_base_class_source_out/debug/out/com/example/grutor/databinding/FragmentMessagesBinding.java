// Generated by view binder compiler. Do not edit!
package com.example.grutor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.grutor.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMessagesBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final EditText etMessage;

  @NonNull
  public final ImageButton ibSend;

  @NonNull
  public final RecyclerView rvChat;

  private FragmentMessagesBinding(@NonNull FrameLayout rootView, @NonNull EditText etMessage,
      @NonNull ImageButton ibSend, @NonNull RecyclerView rvChat) {
    this.rootView = rootView;
    this.etMessage = etMessage;
    this.ibSend = ibSend;
    this.rvChat = rvChat;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMessagesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMessagesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_messages, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMessagesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.etMessage;
      EditText etMessage = ViewBindings.findChildViewById(rootView, id);
      if (etMessage == null) {
        break missingId;
      }

      id = R.id.ibSend;
      ImageButton ibSend = ViewBindings.findChildViewById(rootView, id);
      if (ibSend == null) {
        break missingId;
      }

      id = R.id.rvChat;
      RecyclerView rvChat = ViewBindings.findChildViewById(rootView, id);
      if (rvChat == null) {
        break missingId;
      }

      return new FragmentMessagesBinding((FrameLayout) rootView, etMessage, ibSend, rvChat);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
