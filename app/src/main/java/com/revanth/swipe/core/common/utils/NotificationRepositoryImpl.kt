package com.revanth.swipe.core.common.utils

class NotificationRepositoryImpl(
    private val notificationHelper: NotificationHelper
) : NotificationRepository {

    override fun sendNotification(title: String, message: String) {
        notificationHelper.showNotification(title, message)
    }
}
