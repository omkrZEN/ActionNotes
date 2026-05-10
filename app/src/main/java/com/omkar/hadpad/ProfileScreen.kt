package com.omkar.hadpad

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.omkar.hadpad.auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel, onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val firebaseAuth = FirebaseAuth.getInstance()

    // after login the user state will not change here so below we are using the launched effect to refresh
    var user by remember { mutableStateOf(firebaseAuth.currentUser) }

    val state by authViewModel.uiState.collectAsStateWithLifecycle()

    // we are re fetching the user after state.refreshProfile key from the viewmodel state
    // and this value will be stored in the above user mutable state remember
    LaunchedEffect(state.refreshProfile) {
        user = firebaseAuth.currentUser
    } // Without this ui will not trigger recomposition, and it's causing the stale ui after login

    val displayName = user?.displayName?.takeIf { it.isNotBlank() } ?: "Guest User"
    val email = user?.email?.takeIf { it.isNotBlank() } ?: "Not signed in"
    val uid = user?.uid ?: "No UID"

    // to get the max resolution we are using "s512-c",,,
    val highResPhotoUrl = user?.photoUrl?.toString()?.replace("s96-c", "s512-c")

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Profile", style = MaterialTheme.typography.titleLarge
                )
            }, navigationIcon = {
                TextButton(onClick = onBackClick) {
                    Text(
                        text = "<", style = MaterialTheme.typography.titleLarge
                    )
                }
            })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            ProfileHeaderCard(
                displayName = displayName,
                email = email,
                uid = uid,
                photoUrl = highResPhotoUrl,
                isSignedIn = user != null,
                isLoginLoading = state.isLoading && !state.isSignedIn,
                onLoginClick = {
                    if (activity != null) {
                        authViewModel.onGoogleSignInClick(activity)
                    }
                },
                onSignOutClick = {
                    firebaseAuth.signOut()
                    onBackClick() // after sign out get back to homescreen

                })

            Spacer(modifier = Modifier.height(16.dp))

            ProfileSectionCard(
                title = "Preferences", subtitle = "Customize your experience."
            ) {
                SimpleInfoRow(
                    label = "Theme", value = "System default"
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                SimpleInfoRow(
                    label = "Notifications", value = "Coming soon"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ProfileSectionCard(
                title = "About",
                subtitle = "Version and app details."
            ) {
                SimpleInfoRow(
                    label = "Privacy",
                    value = "Google sign-in only"
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                SimpleInfoRow(
                    label = "Version", value = BuildConfig.VERSION_NAME
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "ActionNotes by Omkar Hadpad",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileHeaderCard(
    displayName: String,
    email: String,
    uid: String,
    photoUrl: String?,
    isSignedIn: Boolean,
    isLoginLoading: Boolean,
    onLoginClick: () -> Unit,
    onSignOutClick: () -> Unit
) {

    val clipboardManager = LocalClipboardManager.current


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!photoUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(photoUrl)
                            .crossfade(true).build(),
                        contentDescription = "Profile photo",
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = displayName.firstOrNull()?.uppercase() ?: "U",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = displayName,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {

                Surface(
                    onClick = { // copy the uid to clipboard after click
                        clipboardManager.setText(AnnotatedString(uid))
                    },
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f),
                    tonalElevation = 1.dp
                ) {

                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.rounded_id_card_24),
                            contentDescription = "ID icon",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(16.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "UID •  $uid  ",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold, letterSpacing = 0.8.sp
                            ),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            painter = painterResource(R.drawable.rounded_content_copy_24),
                            contentDescription = "Copy UID",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (isSignedIn) {
                Button(
                    onClick = onSignOutClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {

                    Text("Sign Out")
                }
            } else {
                Button(
                    onClick = onLoginClick,
                    enabled = !isLoginLoading,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    if (isLoginLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Connecting...")
                    } else {
                        Text("Continue with Google")
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileSectionCard(
    title: String,
    subtitle: String,
    content: @Composable () -> Unit // new concept - we can use compose as unit reusable card bg
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
private fun SimpleInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}