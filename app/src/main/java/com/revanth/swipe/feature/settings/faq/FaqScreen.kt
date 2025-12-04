package com.revanth.swipe.feature.settings.faq

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
import androidx.compose.ui.unit.dp
import com.revanth.swipe.core.ui.components.SwipeScaffold

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
        question = "What is Swipe?",
        answer = "Swipe is a fast and simple billing and inventory management app designed to help businesses manage sales, customers, and products efficiently."
    ),
    FAQ(
        question = "How do I add a new product?",
        answer = "Go to the Products section, tap the + icon, and enter the product details such as name, price, stock, and description."
    ),
    FAQ(
        question = "Can I generate invoices in Swipe?",
        answer = "Yes! You can easily create and share invoices with customers directly from the app using the Invoice section."
    ),
    FAQ(
        question = "Is my data safe on Swipe?",
        answer = "Yes. Swipe securely stores all your billing and inventory data with cloud backup and encryption to ensure maximum safety."
    )
)
