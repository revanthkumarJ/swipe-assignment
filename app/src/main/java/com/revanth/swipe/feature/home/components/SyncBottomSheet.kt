package com.revanth.swipe.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.revanth.swipe.R
import com.revanth.swipe.core.ui.components.SwipeBottomSheet
import com.revanth.swipe.core.ui.components.SwipeLottieAnimation
import com.revanth.swipe.feature.home.HomeAction
import com.revanth.swipe.feature.home.HomeUiState

@Composable
fun SyncBottomSheet(
    networkAvailable:Boolean,
    showSyncBottomSheet: Boolean,
    size: Int,
    onAction: (HomeAction) -> Unit
) {

    SwipeBottomSheet(
        showBottomSheet = showSyncBottomSheet,
        onDismiss = {
            onAction(HomeAction.DismissSyncBottomSheet)
        },
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if(size==0){
                    Text(
                        text = "No Items to Sync",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    SwipeLottieAnimation(
                        raw = R.raw.empty,
                        size = 200.dp
                    )
                }
                else{
                    if (!networkAvailable) {
                        Text(
                            text = "No Internet Connection",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Sync will start once we get active internet",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(
                            text = "$size files syncing pending",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        SwipeLottieAnimation(
                            raw = R.raw.no_internet,
                            size = 200.dp
                        )

                    } else {
                        Text(
                            text = "Syncing Files",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "$size files syncing currently",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        SwipeLottieAnimation(
                            raw = R.raw.sync,
                            size = 200.dp
                        )
                    }
                }
            }
        }
    }
}