package com.eegets.plugin.utils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wangkai on 2021/12/10 14:29
 * <p>
 * Desc TODO
 */
public class PgyerUtils {

    private static final String LOG_UPLOAD_TASK = "PgyerUtils + ";

    /**
     * 上传App
     */
    private static final String UPLOAD_PGYER = "https://www.pgyer.com/apiv2/app/upload";

    /**
     * 获取App详细信息
     */
    private static final String RESPONSE_GPYER = "https://www.pgyer.com/apiv2/app/view";

    public static final String saveImagePath = "/Users/wangkai/FlutterProjects/TestGroovyPlugin/app/build/outputs/apk/release/ImageRQ.jpg";

    public String uploadApkPgyer(File apkPath, String fileName) {
        System.out.println(LOG_UPLOAD_TASK + " apkPath: " + apkPath + "; fileName: " + fileName);

        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data'"), apkPath);

        MultipartBody requestBody = addRequestBody()
                .addFormDataPart("file", fileName, fileBody)
                .build();

        Request request = new Request.Builder()
                .header("content-type", "application/x-www-form-urlencoded")
                .url(UPLOAD_PGYER)
                .post(requestBody)
                .build();
        String responseMsg = null;
        try {
            Response response = new OkHttpClient().newCall(request).execute();

            responseMsg = response.body().string();

            System.out.println(LOG_UPLOAD_TASK + " upload success path: " + responseMsg + "; apkPath: " + apkPath);

            FileUtils.saveToLocal("https://www.pgyer.com/N0tK", saveImagePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMsg;
    }

    public String getMessage() {

        MultipartBody requestBody = addRequestBody()
                .build();

        Request request = new Request.Builder()
                .header("content-type", "application/x-www-form-urlencoded")
                .url(RESPONSE_GPYER)
                .post(requestBody)
                .build();
        try {
            Response response = new OkHttpClient().newCall(request).execute();

            String responseMsg = response.body().string();

            System.out.println(LOG_UPLOAD_TASK + " upload success path: " + responseMsg);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private MultipartBody.Builder addRequestBody() {
        return new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"))
                .addFormDataPart("_api_key", "0b9e7c7b9cf4ace8c41626f6371d2eca")
                .addFormDataPart("appKey", "")
                .addFormDataPart("userKey", "7174de3cf30861bf6c11344996593317")
                .addFormDataPart("buildUpdateDescription", "这是版本更新描述");
    }
}
