package com.revanth.swipe.core.common.utils

interface NotificationRepository {
    fun sendNotification(title: String, message: String)
}
