package com.example.githubuserfinder.app

import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object SecurityUtil {

    fun isDeviceRooted(): Boolean {
        return checkRootMethod1() && checkRootMethod2() && checkRootMethod3()
    }

    val isProbablyRunningOnEmulator: Boolean by lazy {
        // Android SDK emulator
        return@lazy (
            (
                Build.FINGERPRINT.startsWith("google/sdk_gphone_") &&
                    Build.FINGERPRINT.endsWith(":user/release-keys") &&
                    Build.MANUFACTURER == "Google" && Build.PRODUCT.startsWith("sdk_gphone_") && Build.BRAND == "google" &&
                    Build.MODEL.startsWith("sdk_gphone_")
                ) ||
                //
                Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86") ||
                // bluestacks
                "QC_Reference_Phone" == Build.BOARD && !"Xiaomi".equals(
                Build.MANUFACTURER,
                ignoreCase = true
            ) || // bluestacks
                Build.MANUFACTURER.contains("Genymotion") ||
                Build.HOST.startsWith("Build") || // MSI App Player
                Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
                Build.PRODUCT == "google_sdk" ||
                // another Android SDK emulator check
                SystemProperties.getProp("ro.kernel.qemu") == "1"
            )
    }

    private fun checkRootMethod1(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRootMethod2(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su",
            "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
            "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"
        )
        for (path in paths) {
            if (File(path).exists()) return true
        }
        return false
    }

    private fun checkRootMethod3(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val `in` = BufferedReader(InputStreamReader(process.inputStream))
            `in`.readLine() != null
        } catch (t: Throwable) {
            false
        } finally {
            process?.destroy()
        }
    }
}
