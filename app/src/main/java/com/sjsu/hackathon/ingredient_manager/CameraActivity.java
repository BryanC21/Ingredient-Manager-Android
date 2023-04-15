package com.sjsu.hackathon.ingredient_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.google.common.util.concurrent.ListenableFuture;
import com.sjsu.hackathon.ingredient_manager.ui.home.HomeFragment;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity {
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
    private final int REQUEST_CODE_PERMISSIONS = 1001;
    private Executor executor = Executors.newSingleThreadExecutor();
    PreviewView mPreviewView;
    Button captureImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mPreviewView = findViewById(R.id.viewFinder);
        mPreviewView.setVisibility(View.VISIBLE);
        captureImage = findViewById(R.id.image_capture_button);
        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {

                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);

                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .build();

        ImageCapture.Builder builder = new ImageCapture.Builder();

        final ImageCapture imageCapture = builder
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .build();

        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageAnalysis, imageCapture);
        captureImage.setOnClickListener(v -> {
            ProgressBar spinnerContainer = findViewById(R.id.spinner);
            spinnerContainer.setVisibility(View.VISIBLE);
            mPreviewView.setVisibility(View.GONE);

            imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
                @SuppressLint("UnsafeExperimentalUsageError")
                @Override
                public void onCaptureSuccess(@NonNull ImageProxy image) {
                    System.out.println("captured");
                    final Bitmap bitmap = mPreviewView.getBitmap();
                    if (bitmap == null) {
                        System.out.println("bitmap not created");
                    } else {
                        executor.execute(() -> {
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            System.out.println(out.size());

                            AWSCredentials creds =
                                    new BasicAWSCredentials(getString(R.string.aws_access_key_id),
                                            getString(R.string.aws_secret_key));
                            AmazonRekognitionClient client = new AmazonRekognitionClient(creds);
                            client.setRegion(Region.getRegion("us-east-2"));
                            Image img = new Image();
                            img.withBytes(ByteBuffer.wrap(out.toByteArray()));
                            DetectLabelsRequest req = new DetectLabelsRequest().withImage(img);

                            DetectLabelsResult res = client.detectLabels(req);
                            try {
                                out.close();
                            } catch (Exception e) {
                                System.out.println("failed to close");
                            }

                            List<Label> labels = res.getLabels();
                            if (labels.size() != 0) {
                                submitText(CameraActivity.super.getCurrentFocus(), labels.get(0).getName());
                            }
                        });

                    }
                }

                @Override
                public void onError(@NonNull final ImageCaptureException exception) {
                    exception.printStackTrace();
                    spinnerContainer.setVisibility(View.GONE);
                }
            });
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void submitText(View view, String text) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("label", text);
        intent.putExtras(b);
        startActivity(intent);
    }
}
