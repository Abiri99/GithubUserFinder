package com.example.githubuserfinder.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.example.githubuserfinder.BuildConfig
import com.example.githubuserfinder.app.theme.lightThemeColors
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.security.ProviderInstaller

private const val ERROR_DIALOG_REQUEST_CODE = 1

// Dependencies are created manually in the MainActivity and injected to the lower layers
class MainActivity : ComponentActivity(), ProviderInstaller.ProviderInstallListener {

    /**
     * This is a flag indicating whether the [ProviderInstaller] must re-install the provider or not
     * */
    private var retryProviderInstall: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SecurityUtil.isDeviceRooted()) {
            // Instead of closing the app, a proper message must be shown to the user
            finish()
        }

        if (!BuildConfig.DEBUG && SecurityUtil.isProbablyRunningOnEmulator) {
            // Instead of closing the app, a proper message must be shown to the user
            finish()
        }

        setContent {
            MaterialTheme(
                colors = lightThemeColors,
            ) {
                GithubUserFinderApp()
            }
        }
    }

    /**
     * This method is only called if the provider is successfully updated
     * (or is already up-to-date).
     */
    override fun onProviderInstalled() {
        // Provider is up-to-date, app can make secure network calls.
        // User can safely move on
    }

    /**
     * This method is called if updating fails; the error code indicates
     * whether the error is recoverable.
     */
    override fun onProviderInstallFailed(errorCode: Int, recoveryIntent: Intent?) {
        GoogleApiAvailability.getInstance().apply {
            if (isUserResolvableError(errorCode)) {
                // Recoverable error. Show a dialog prompting the user to
                // install/update/enable Google Play services.
                showErrorDialogFragment(this@MainActivity, errorCode, ERROR_DIALOG_REQUEST_CODE) {
                    // The user chose not to take the recovery action
                    onProviderInstallerNotAvailable()
                }
            } else {
                onProviderInstallerNotAvailable()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        // This API must be replaced with [registerActivityResult]
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ERROR_DIALOG_REQUEST_CODE) {
            // Adding a fragment via GoogleApiAvailability.showErrorDialogFragment
            // before the instance state is restored throws an error. So instead,
            // set a flag here, which will cause the fragment to delay until
            // onPostResume.
            retryProviderInstall = true
        }
    }

    /**
     * On resume, check to see if we flagged that
     * we need to reinstall the provider.
     */
    override fun onPostResume() {
        super.onPostResume()
        if (retryProviderInstall) {
            ProviderInstaller.installIfNeededAsync(this, this)
        }
        retryProviderInstall = false
    }

    private fun onProviderInstallerNotAvailable() {
        // This is reached if the provider cannot be updated for some reason.
        // App should consider all HTTP communication to be vulnerable, and take
        // appropriate action.

        // Instead of closing the app, a proper message must be shown to the user
        finish()
    }
}
