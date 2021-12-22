package com.eegets

import groovy.json.JsonOutput
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Created by wangkai on 2021/12/13 17:11

 * Desc TODO
 */
object FeishuExt {
    private const val LOG_UPLOAD_TASK = "FeishuExt + "

    /**
     * 飞书hook接口
     */
    private const val feishuHookUrl = "https://open.feishu.cn/open-apis/bot/v2/hook/2c506f22-39e9-47c4-b9bc-4ef9bddd02e9"

    /**
     * 上传图片并获取生成的imageKey
     */
    private const val uploadImageAndGetImageKeyUrl = "https://open.feishu.cn/open-apis/im/v1/images"

    /**
     * 获取token
     */
    private const val requestToken = "https://open.feishu.cn/open-apis/auth/v3/app_access_token"

    /**
     * 授权凭证
     */
    private const val tenantAccessToken = "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal"

    /**
     * 第三步 发送消息到飞书智能助手
     * https://open.feishu.cn/document/ukTMukTMukTM/ucTM5YjL3ETO24yNxkjN?lang=zh-CN#4996824a
     */
    fun pushMessageToFeishuHook(feishuParams: BuildParams.Feishu, pgyerInfoBean: PgyerInfoBean) {

        val feishuMsgBean = JsonOutput.toJson(addRequestBean(pgyerInfoBean))

        println(LOG_UPLOAD_TASK + "pushMessageToFeishuHook pgyerInfoBean: $pgyerInfoBean; feishuMsgBean: $feishuMsgBean")

        val requestBody: RequestBody = feishuMsgBean.toRequestBody("application/json".toMediaTypeOrNull())

        val request: Request = Request.Builder()
                .header("content-type", "application/x-www-form-urlencoded")
                .url(feishuParams.hookUrl)
                .post(requestBody)
                .build()
        val response = OkHttpClient().newCall(request).execute()
        val responseMsg = response.body?.string()

        println("$LOG_UPLOAD_TASK pushMessageToFeishuHook upload success path: $responseMsg")
    }
}

private fun addRequestBean(pgyerInfoBean: PgyerInfoBean): FeishuMsgBean {

    val contentXList = mutableListOf<List<FeishuMsgBean.ContentX>>().apply {

        mutableListOf<FeishuMsgBean.ContentX>().apply {
            add(FeishuMsgBean.ContentX(tag = "text", text = "更新内容：\n${pgyerInfoBean.data.buildUpdateDescription}"))
        }.also {
            this.add(it)
        }

        mutableListOf<FeishuMsgBean.ContentX>().apply {
            add(FeishuMsgBean.ContentX(tag = "a", text = "\n点击下载安装包", href = "https://www.pgyer.com/${pgyerInfoBean.data.buildShortcutUrl}"))
        }.also {
            this.add(it)
        }
    }

    return FeishuMsgBean(msg_type = "post",
            content = FeishuMsgBean.Content(
                    post = FeishuMsgBean.Post(
                            zh_cn = FeishuMsgBean.ZhCn(
                                    content = contentXList,
                                    title = "最新开发测试包「${pgyerInfoBean.data.buildName}（V${pgyerInfoBean.data.buildVersion}）」"
                            )
                    )
            ))
}
