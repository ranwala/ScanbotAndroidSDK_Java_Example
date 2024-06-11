package com.example.scanbotexample;

import io.scanbot.sdk.ScanbotSDKInitializer;
import io.scanbot.sdk.process.ImageProcessor;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ScanbotSDKInitializer sdkLicenseInfo = new ScanbotSDKInitializer();
        sdkLicenseInfo.prepareOCRLanguagesBlobs(true);
        sdkLicenseInfo.license(this, "");
        sdkLicenseInfo.allowGpuAcceleration(false);
        sdkLicenseInfo.initialize(this);
    }
}
