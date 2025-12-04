package com.revanth.swipe.feature.settings.faq

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.revanth.swipe.core.ui.components.SwipeScaffold
import com.revanth.swipe.core.ui.theme.SwipeTheme

@Composable
fun FaqScreen(
    onNavigateBack:()->Unit,
    modifier: Modifier = Modifier,
) {
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    SwipeScaffold(
        topBarTitle = "Faq",
        onNavigateBack = onNavigateBack,
        content = {
            Box(modifier = Modifier) {
                if (faqList.isNotEmpty()) {
                    FaqContent(
                        faqArrayList = faqList,
                        selectedFaqPosition = selectedIndex,
                        onAction = {
                            selectedIndex = it
                        },
                    )
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun FaqContent(
    faqArrayList: List<FAQ>,
    selectedFaqPosition: Int,
    onAction: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            itemsIndexed(items = faqArrayList) { index, faqItem ->
                FaqItemHolder(
                    index = index,
                    isSelected = selectedFaqPosition == index,
                    onItemSelected = { onAction(it) },
                    question = faqItem.question,
                    answer = faqItem.answer,
                )
            }
        }
    }
}

data class FAQ(
    val question: String? = null,
    val answer: String? = null,
)

val faqList = listOf(
    FAQ(
        question = "What is this app?",
        answer = "This app allows users to view products, search for items, add new products, and manage them efficiently using a clean and modern Android interface."
    ),
    FAQ(
        question = "How do I add a new product?",
        answer = "Open the Add Product screen, fill in details like name, price, product type, and tax, upload an optional image, and tap Submit to add it to the system."
    ),
    FAQ(
        question = "Can I use the app without internet?",
        answer = "Yes. You can add products offline, and they will be saved locally. When the internet becomes available, the app automatically syncs them with the server."
    ),
    FAQ(
        question = "Where are the products coming from?",
        answer = "Products are fetched from the public Swipe API, and displayed with images, prices, type, and tax information."
    ),
    FAQ(
        question = "How does search work?",
        answer = "You can search products by name in real-time using the search bar on the product listing screen."
    ),
    FAQ(
        question = "Can I change the theme of the app?",
        answer = "Yes. You can switch between Light, Dark, or System Default theme from the Settings screen."
    ),
    FAQ(
        question = "What information is available in App Info?",
        answer = "App Info contains details such as version, build number, developer credits, and necessary license/technology information."
    )
)

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun FaqScreenPreview() {
    SwipeTheme {
        FaqScreen(
            onNavigateBack = {}
        )
    }
}
