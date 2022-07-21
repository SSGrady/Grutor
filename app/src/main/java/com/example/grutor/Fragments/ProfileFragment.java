package com.example.grutor.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.grutor.Activites.LoginActivity;
import com.example.grutor.Modals.User;
import com.example.grutor.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

public class ProfileFragment extends Fragment {

    public Button btnlogOut;
    protected ParseUser currentUser;
    protected TextView tvProfileName, tvUserBestSubject, tvUserCurrentGrade, tvUserAddress;
    protected ImageView ivCurrUserProfile;
    protected String photoFileName = "";
    protected File photoFile;
    protected final int CAPTURE_IMAGE_REQUEST_CODE = 42;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        currentUser = (User) ParseUser.getCurrentUser();
        btnlogOut = view.findViewById(R.id.btnlogOut);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvUserBestSubject = view.findViewById(R.id.tvBestSubjectAnswer);
        tvUserCurrentGrade = view.findViewById(R.id.tvCurrentGradeAnswer);
        tvUserAddress = view.findViewById(R.id.tvUserAddressAnswer);
        ivCurrUserProfile = view.findViewById(R.id.ivCurrUserProfile);

        if (currentUser != null)  {
            photoFileName += currentUser.getUsername().toString() + ".jpeg";
            tvProfileName.setText(currentUser.get("name").toString());
            tvUserAddress.setText(currentUser.get("zipcode").toString());
            tvUserBestSubject.setText(currentUser.get("bestAt").toString());
            tvUserCurrentGrade.setText(currentUser.get("grade").toString());
            ivCurrUserProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchCamera();
                    ParseFile profilePhoto = currentUser.getParseFile("profilePhoto");
                }
            });
            if (currentUser.getParseFile("profilePhoto") != null) {
                Glide.with(getContext())
                        .load(currentUser.getParseFile("profilePhoto").getUrl())
                        .circleCrop() // create an effect of a round profile picture
                        .into(ivCurrUserProfile);
            }
        }
        btnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                currentUser = (User) ParseUser.getCurrentUser(); // now null
                goLoginActivity();
            }
        });
    }

    protected void launchCamera() {
        // Intent to take a picture and return control to the calling application
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // File reference to access future access
        photoFile = getPhotoFileUri(photoFileName);
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if (i.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(i, CAPTURE_IMAGE_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ProfileFragment");
        // Create the storage directory if it doesn't exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("ProfileFragment", "failed to create directory");
        }
        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                // ivProfilePhoto.setImageBitmap(takenImage);
                Glide.with(getContext()).load(takenImage).circleCrop().into(ivCurrUserProfile);
                currentUser.put("profilePhoto", new ParseFile(photoFile));
                currentUser.saveInBackground();
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}