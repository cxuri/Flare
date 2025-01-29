package com.example.flare;

import android.content.Context;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** FlarePlugin */
public class FlarePlugin implements FlutterPlugin, MethodCallHandler {
    private MethodChannel channel;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flare");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "validateAppIntegrity":
                // Get the application context
                Context context = flutterPluginBinding.getApplicationContext();
                
                // Instantiate IntegrityCheck with context
                IntegrityCheck integrityChecker = new IntegrityCheck(context);
                
                // Get the SHA256 checksum from the method call arguments
                String sha256 = call.argument("sha");
                
                try {
                    // Validate app integrity by comparing the hashes
                    boolean isValid = integrityChecker.validateAppIntegrity(sha256);
                    result.success(isValid);
                } catch (IOException e) {
                    result.error("IO_ERROR", "Error reading APK file", null);
                }
                break;

            default:
                result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
