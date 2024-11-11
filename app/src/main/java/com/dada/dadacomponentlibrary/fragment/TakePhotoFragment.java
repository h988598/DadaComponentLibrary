package com.dada.dadacomponentlibrary.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.dada.dadacomponentlibrary.PhotoConstant;
import com.dada.dadacomponentlibrary.R;
import com.dada.dadacomponentlibrary.bean.PhotoModel;
import com.dada.dadacomponentlibrary.databinding.FragmentTakePhotoBinding;
import com.dada.dadacomponentlibrary.utils.FileUtil;
import com.dada.dadacomponentlibrary.utils.SelectPicHelper;

import java.io.File;

public class TakePhotoFragment extends BaseNormalFragment {
    private FragmentTakePhotoBinding binding;
    private SelectPicHelper mPicHelper;
    private File mTempFile;
    private Uri fileUri;
    private ImageView mIvPhoto;
    private static final String PROJECT_PIC_PATH = "/yourprojectnamepic/image/";
    private static final String PIC_TYPE = ".jpg";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTakePhotoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        mIvPhoto = binding.ivPhoto;
        mIvPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showAddPhotoPopupWindow();
            }
        });
        binding.ivDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FileUtil.deleteFile(mTempFile);
                PhotoModel photoModel = new PhotoModel();
                photoModel.setUri(null);
                showPhoto(photoModel, mIvPhoto);
                binding.tvAddPhoto.setVisibility(View.VISIBLE);
                binding.ivDelete.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getRootViewResId() {
        return R.layout.fragment_take_photo;
    }

    private void showAddPhotoPopupWindow() {
        // 适用于 Android 10 及以上版本的文件路径
        if (Build.VERSION.SDK_INT >= 24) {
            mTempFile = new File(FileUtil.
                    checkDirPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + PROJECT_PIC_PATH), System.currentTimeMillis() + PIC_TYPE);
        } else {
            mTempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory()
                    .getPath() + PROJECT_PIC_PATH),
                    System.currentTimeMillis() + PIC_TYPE);
        }

        mPicHelper = new SelectPicHelper(getActivity(), mTempFile, mIvPhoto, this);
        mPicHelper.setClickSelectPicListener(new SelectPicHelper.ClickSelectPicListener() {
            @Override
            public void onClickSelectPic() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Launch the gallery to select an image
                pickImageLauncher.launch(intent);
            }
        });
        mPicHelper.setClickTakePhotoListener(new SelectPicHelper.ClickTakePhotoListener() {

            @Override
            public void onClickTakePhoto() {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= 24) {
                    fileUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".fileprovider", mTempFile);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    fileUri = Uri.fromFile(mTempFile);
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, PhotoConstant.REQUEST_TAKE_PHOTO);
            }
        });
        mPicHelper.selectPic();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoConstant.REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            // 确保照片已经保存
            if (mTempFile.exists()) {
                // 更新相册
                scanFile(mTempFile.getAbsolutePath());
                PhotoModel model = new PhotoModel();
                model.setUri(fileUri);
                model.setFilePath(mTempFile.getAbsolutePath());
                showPhoto(model, mIvPhoto);
            }

        }
    }

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        PhotoModel photoModel = new PhotoModel();
                        photoModel.setUri(selectedImageUri);
                        showPhoto(photoModel, mIvPhoto);
                    }
                }
            }
    );

    private void showPhoto(PhotoModel photoModel, ImageView imageView) {
        if (getActivity() != null) {
            Glide.with(getActivity()).load(photoModel.getUri()).into(imageView);
            binding.tvAddPhoto.setVisibility(View.GONE);
            binding.ivDelete.setVisibility(View.VISIBLE);
        }
    }

    private void scanFile(String path) {
        MediaScannerConnection.scanFile(getActivity(), new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }
}
