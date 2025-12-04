package com.revanth.swipe.feature.settings.help

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PhonePaused
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.revanth.swipe.core.ui.components.SwipeScaffold
import com.revanth.swipe.core.ui.theme.AppColors
import com.revanth.swipe.R
import com.revanth.swipe.core.ui.theme.SwipeTheme

@Composable
fun HelpScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToFAQ: () -> Unit,
) {
    val context = LocalContext.current
    SwipeScaffold(
        onNavigateBack = onBackClick,
        topBarTitle = "Help",
        modifier = modifier,
    ) {
        HelpScreenContent(
            onCallClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:9030808053")
                }
                context.startActivity(intent)
            },
            onMailClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:support@getswipe.in")
                    putExtra(Intent.EXTRA_SUBJECT, "Swipe App - Help & Support")
                }
                context.startActivity(intent)
            },
            navigateToFAQ = navigateToFAQ
        )
    }
}

@Composable
internal fun HelpScreenContent(
    onCallClick: () -> Unit,
    onMailClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToFAQ: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 16.dp,
            )
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        FAQCard(onClick = navigateToFAQ)
        PhoneSupportCard(onCallClick = onCallClick)
        EmailSupportCard(onMailClick = onMailClick)
    }
}


@Composable
private fun FAQCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    HelpCard(
        backgroundColor = AppColors.lightRed,
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
        ) {
            Text(
                text = "Many Doubts Are Solved In FAQ's!",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "No , I haven't checked them, take me there!",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
            )
        }
    }
}

@Composable
private fun PhoneSupportCard(
    modifier: Modifier = Modifier,
    onCallClick: () -> Unit,
) {
    SupportCard(
        backgroundColor = AppColors.peach,
        title = "Still Have Doubts?",
        message = "Facing an issue? Don’t worry at all!\nJust give our customer care a call.\nWe’re here to assist you, day and night,\nTo make sure everything’s working right!",
        action = "Call Us",
        iconRes = R.drawable.ic_help_mobile,
        iconContentDescription = "Phone Support",
        onActionClick = onCallClick,
        modifier = modifier,
    )
}

@Composable
private fun EmailSupportCard(
    modifier: Modifier = Modifier,
    onMailClick: () -> Unit,
) {
    SupportCard(
        backgroundColor = AppColors.lightPurple,
        title = "Mail Your Issues?",
        message = "Need support but prefer to write?\nSend us an email, day or night.\nOur team will respond with care and speed,\nEnsuring you get the help you need!",
        action = "Mail Us",
        iconRes = R.drawable.ic_help_mail,
        iconContentDescription = "Mail Support",
        onActionClick = onMailClick,
        modifier = modifier,
    )
}

@Composable
private fun HelpCard(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: ()->Unit,
    content: @Composable () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        content()
    }
}

@Composable
private fun SupportCard(
    backgroundColor: Color,
    title: String,
    message: String,
    action: String,
    @DrawableRes iconRes: Int,
    iconContentDescription: String,
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit,
) {
    HelpCard(
        backgroundColor = backgroundColor,
        modifier = modifier,
        onClick = onActionClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
        ) {
            SupportCardContent(
                title = title,
                message = message,
                action = action,
                onActionClick = onActionClick,
                modifier = Modifier.fillMaxWidth().zIndex(1f),
            )
            SupportCardIcon(
                iconRes = iconRes,
                contentDescription = iconContentDescription,
                modifier = Modifier.align(Alignment.BottomEnd).zIndex(0f),
            )
        }
    }
}

@Composable
private fun SupportCardContent(
    title: String,
    message: String,
    action: String,
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
        )
        HelpActionButton(
            text = action,
            onClick = onActionClick,
        )
    }
}


@Composable
private fun SupportCardIcon(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(iconRes),
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@Composable
private fun HelpActionButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.3f),
        ),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 12.dp,
        ),
        modifier = modifier,
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun HelpScreenPreview() {
    SwipeTheme {
        HelpScreen(
            onBackClick = {},
            navigateToFAQ = {}
        )
    }
}
