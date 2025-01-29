/*
 * Contributed By Cxuri;
 * Integrity Checker for Flare
 * 
 * Description:
 * This class is responsible for performing integrity checks on the app's APK file.
 * It reads the APK file from `sourceDir`, computes its contents' SHA-256 hash, 
 * and verifies it against a provided SHA-256 checksum.
 * 
 * Remarks:
 * Anyone willing to improve this code is free to do so. Have a nice day :)
 */

import android.content.Context;
import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.security.MessageDigest;

public class IntegrityCheck {

    private final Context context;

    public IntegrityCheck(@NonNull Context context) {
        this.context = context;
    }

    /**
     * Generates the SHA-256 hash of a byte array.
     *
     * @param data The byte array to hash.
     * @return The SHA-256 hash as a hexadecimal string.
     */
    private String generateSHA256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data);

            // Convert byte array to hexadecimal string
            StringBuilder hexStr = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexStr.append('0');
                }
                hexStr.append(hex);
            }
            return hexStr.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating SHA-256 hash", e);
        }
    }

    /**
     * Reads the APK file as a byte array.
     *
     * @return The APK file contents as a byte array.
     * @throws IOException If an error occurs while reading the file.
     */
    private byte[] readAPKAsBytes() throws IOException {
        String appPath = context.getApplicationInfo().sourceDir; // Get the APK file path
        File apkFile = new File(appPath);

        try (FileInputStream fis = new FileInputStream(apkFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            // Read the APK file in chunks
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray(); // Return the entire file content as a byte array
        }
    }

    /**
     * Validates the integrity of the app by comparing the computed hash of the APK
     * file with a provided SHA-256 checksum.
     *
     * @param expectedSHA256 The expected SHA-256 checksum of the APK file.
     * @return True if the computed hash matches the expected hash, false otherwise.
     * @throws IOException If an error occurs while reading the APK file.
     */
    public boolean validateAppIntegrity(String expectedSHA256) throws IOException {
        byte[] apkBytes = readAPKAsBytes(); // Read APK contents as bytes
        String computedSHA256 = generateSHA256(apkBytes); // Compute the SHA-256 hash
        return computedSHA256.equalsIgnoreCase(expectedSHA256); // Compare with the expected hash
    }
}
