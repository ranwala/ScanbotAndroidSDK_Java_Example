package com.example.scanbotexample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scanbotexample.databinding.ActivityMainBinding;

import java.util.List;

import io.scanbot.sdk.camera.CameraPreviewMode;
import io.scanbot.sdk.persistence.Page;
import io.scanbot.sdk.ui.view.barcode.BarcodeScannerActivity;
import io.scanbot.sdk.ui.view.barcode.configuration.BarcodeScannerConfiguration;
import io.scanbot.sdk.ui.view.barcode.dialog.BarcodeConfirmationDialogConfiguration;
import io.scanbot.sdk.ui.view.barcode.dialog.BarcodeDialogFormat;
import io.scanbot.sdk.ui.view.camera.DocumentScannerActivity;
import io.scanbot.sdk.ui.view.camera.configuration.DocumentScannerConfiguration;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private ActivityResultLauncher<DocumentScannerConfiguration> documentScannerResultLauncher;
    private ActivityResultLauncher<BarcodeScannerConfiguration> barcodeResultLauncher;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        documentScannerResultLauncher = registerForActivityResult(
                new DocumentScannerActivity.ResultContract(),
                resultEntity -> {

                    if (resultEntity.getResultOk()) {
                        PageRepository.addPages(resultEntity.getResult());

                        Intent intent = new Intent(this, PagePreviewActivity.class);
                        startActivity(intent);
                    }
                }
        );

        barcodeResultLauncher = registerForActivityResult(
                new BarcodeScannerActivity.ResultContract(),
                resultEntity -> {

                    if (resultEntity.getResultOk()) {
                        // do something
                    }
                }
        );

//        barcodeResultLauncher = io.scanbot.sdk.ui_v2.common.activity.ScanbotActivityResultKt.registerForActivityResultOk(
//                new BarcodeScannerActivity.ResultContract(),
//                resultEntity -> {
//
////                    if (((io.scanbot.sdk.ui_v2.barcode.BarcodeScannerActivity.Result)resultEntity).getResultOk()) {
////                        // do something
////                    }
//                }
//        );

        Button scanDocumentButton = findViewById(R.id.camera_default_ui);
        Button scanBarcodeButton = findViewById(R.id.qr_camera_default_ui);

        scanDocumentButton.setOnClickListener(v -> {
            DocumentScannerConfiguration documentScannerConfiguration = new DocumentScannerConfiguration();

            documentScannerConfiguration.setCameraPreviewMode(CameraPreviewMode.FIT_IN);
            documentScannerConfiguration.setIgnoreBadAspectRatio(true);
            documentScannerConfiguration.setBottomBarBackgroundColor(Color.BLACK);
            documentScannerConfiguration.setBottomBarButtonsColor(Color.GRAY);
            documentScannerConfiguration.setTopBarButtonsActiveColor(Color.WHITE);
            documentScannerConfiguration.setCameraBackgroundColor(Color.parseColor("#4b4f52"));
            documentScannerConfiguration.setUserGuidanceBackgroundColor(Color.BLACK);
            documentScannerConfiguration.setUserGuidanceTextColor(Color.WHITE);
            documentScannerConfiguration.setMultiPageEnabled(true);
            documentScannerConfiguration.setAutoSnappingSensitivity(0.75f);
            documentScannerConfiguration.setPageCounterButtonTitle("%d Page(s)");
            documentScannerConfiguration.setTextHintOK("Don't move.\nCapturing document...");

            documentScannerResultLauncher.launch(documentScannerConfiguration);
        });

        scanBarcodeButton.setOnClickListener(v -> {
            BarcodeScannerConfiguration barcodeScannerConfiguration = new BarcodeScannerConfiguration();
            barcodeScannerConfiguration.setConfirmationDialogConfiguration(new BarcodeConfirmationDialogConfiguration(
                    true,
                    "Barcode",
                    "Detected barcode",
                    "Confirm",
                    "Cancel",
                    Color.RED,
                    true,
                    Color.WHITE,
                    BarcodeDialogFormat.TYPE_AND_CODE

            ));
            // check more parameters
            barcodeResultLauncher.launch(barcodeScannerConfiguration);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}