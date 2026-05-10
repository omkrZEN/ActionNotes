package com.omkar.hadpad.auth

import android.app.Activity
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import com.omkar.hadpad.R

class GoogleAuthRepository(
    context: Context,
    private val auth: FirebaseAuth
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun signIn(activity: Activity) {
        val webClientId = activity.getString(R.string.default_web_client_id)

        suspend fun requestIdToken(filterByAuthorizedAccounts: Boolean): String {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(webClientId)
                .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts)
                .setAutoSelectEnabled(filterByAuthorizedAccounts)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = activity
            )

            val credential = result.credential
            if (
                credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                return GoogleIdTokenCredential.createFrom(credential.data).idToken
            }

            throw IllegalStateException("Unsupported credential type")
        }

        val idToken = try {
            requestIdToken(filterByAuthorizedAccounts = true)
        } catch (_: NoCredentialException) {
            requestIdToken(filterByAuthorizedAccounts = false)
        }

        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential).await()
    }

    fun currentUser() = auth.currentUser
}