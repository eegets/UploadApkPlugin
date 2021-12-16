package com.eegets

data class PgyerInfoBean(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
            val buildBuildVersion: String,
            val buildCreated: String,
            val buildDescription: String,
            val buildFileKey: String,
            val buildFileName: String,
            val buildFileSize: String,
            val buildIcon: String,
            val buildIdentifier: String,
            val buildIsFirst: String,
            val buildIsLastest: String,
            val buildKey: String,
            val buildName: String,
            val buildQRCodeURL: String,
            val buildScreenshots: String,
            val buildShortcutUrl: String,
            val buildType: String,
            val buildUpdateDescription: String,
            val buildUpdated: String,
            val buildVersion: String,
            val buildVersionNo: String,
    )
}