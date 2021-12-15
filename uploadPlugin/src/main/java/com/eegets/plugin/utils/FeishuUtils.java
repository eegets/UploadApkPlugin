package com.eegets.plugin.utils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wangkai on 2021/12/10 14:28
 * <p>
 * Desc TODO
 */
public class FeishuUtils {

    private static final String LOG_UPLOAD_TASK = "FeishuUtils + ";

    /**
     * 飞书hook接口
     */
    private static final String feishuHookUrl = "https://open.feishu.cn/open-apis/bot/v2/hook/2c506f22-39e9-47c4-b9bc-4ef9bddd02e9";

    /**
     * 上传图片并获取生成的imageKey
     */
    private static final String uploadImageAndGetImageKeyUrl = "https://open.feishu.cn/open-apis/im/v1/images";

    /**
     * 获取token
     */
    private static final String requestToken = "https://open.feishu.cn/open-apis/auth/v3/app_access_token";

    /**
     * 授权凭证
     */
    private static final String tenantAccessToken = "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal";

    /**
     * 发送消息到飞书智能助手
     * https://open.feishu.cn/document/ukTMukTMukTM/ucTM5YjL3ETO24yNxkjN?lang=zh-CN#4996824a
     */
    public void pushMessageToFeishuHook(String apkPath, String  fileName) {
        System.out.println(LOG_UPLOAD_TASK + "pushMessageToFeishuHook apkPath: " + apkPath + "; fileName: " + fileName);

        RequestBody fileBody = new FormBody.Builder()
                .add("msg_type", "text")
                .add("content", "{\n" +
                        "        \"text\": \"This is the test message awesome222\"\n" +
                        "    }").build();

        Request request = new Request.Builder()
                .header("content-type", "application/x-www-form-urlencoded")
                .url(feishuHookUrl)
                .post(fileBody)
                .build();
        Response response = null;
        try {
            response = new OkHttpClient().newCall(request).execute();

            String responseMsg = response.body().string();

            System.out.println(LOG_UPLOAD_TASK + " pushMessageToFeishuHook upload success path: " + responseMsg + "; apkPath: " + apkPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第二步 上传图片
     * @param imagePath
     *
     * https://open.feishu.cn/document/uAjLw4CM/ukTMukTMukTM/reference/im-v1/image/create
     */
    public void requestFeishuImageKey(String imagePath) {

        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), imagePath);

        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"))
                .addFormDataPart("image_type", "message")
                .addFormDataPart("file", "imageRQ.jpg", fileBody)
                .build();

        Request request = new Request.Builder()
                .url(uploadImageAndGetImageKeyUrl)
                .addHeader("Content-Type", "multipart/form-data")
                .addHeader("Authorization", "Bearer t-da8512a29498b3bd9734835224f2c0d7dab5d8ad")
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = new OkHttpClient().newCall(request).execute();

            String responseMsg = response.body().string();

            System.out.println(LOG_UPLOAD_TASK + " requestFeishuImageKey upload success path: " + responseMsg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一步：获取证书Token
     *
     * https://open.feishu.cn/document/ukTMukTMukTM/uMTNz4yM1MjLzUzM
     *
     * `Authorization`可以通过`https://open.feishu.cn/document/ukTMukTMukTM/ukDNz4SO0MjL5QzM/auth-v3/auth/tenant_access_token_internal`来获取测试的`Authorization`
     */
    public void requestToken() {
        MultipartBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("app_id", "cli_a1399f919438500c")
                .addFormDataPart("app_secret", "1iOopI8meiKaOaOqCyU0ltpkSiQk1Olb")
                .build();

        Request request = new Request.Builder()
                .addHeader("Content-type", "application/json; charset=utf-8")
                .url(tenantAccessToken)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = new OkHttpClient().newCall(request).execute();

            String responseMsg = response.body().string();

            System.out.println(LOG_UPLOAD_TASK + " requestToken upload success path: " + responseMsg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
