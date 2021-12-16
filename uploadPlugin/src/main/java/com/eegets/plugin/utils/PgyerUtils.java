package com.eegets.plugin.utils;

import com.eegets.GsonUtls;
import com.eegets.PgyerInfoBean;

import org.apache.http.util.TextUtils;

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

    public String uploadApkPgyer(File apkPath, String fileName, String shortName) {
        System.out.println(LOG_UPLOAD_TASK + " apkPath: " + apkPath + "; fileName: " + fileName + "; shortName: " + shortName);

        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data'"), apkPath);

        MultipartBody requestBody = addRequestBody("")
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

            FileUtils.saveToLocal("https://www.pgyer.com/" + shortName, saveImagePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMsg;
    }

    public PgyerInfoBean getMessage() {

        PgyerInfoBean pgyerInfoBean = null;

        MultipartBody requestBody = addRequestBody("")
                .build();

        Request request = new Request.Builder()
                .header("content-type", "application/x-www-form-urlencoded")
                .url(RESPONSE_GPYER)
                .post(requestBody)
                .build();
        try {
            Response response = new OkHttpClient().newCall(request).execute();

            String responseMsg = response.body().string();
            pgyerInfoBean = GsonUtls.INSTANCE.getGson().fromJson(responseMsg, PgyerInfoBean.class);

            System.out.println(LOG_UPLOAD_TASK + " upload success path: " + responseMsg + "; pgyerInfoBean: " + pgyerInfoBean);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pgyerInfoBean;
    }

    private MultipartBody.Builder addRequestBody(String description) {
        if (TextUtils.isEmpty(description)) {
            description = "更新内容正在开发中，敬请期待...";
        }
        return new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"))
                .addFormDataPart("_api_key", "0b9e7c7b9cf4ace8c41626f6371d2eca")
                .addFormDataPart("appKey", "9a5fcfca95c4b33d378bb746b713726e")
                .addFormDataPart("userKey", "7174de3cf30861bf6c11344996593317")
                .addFormDataPart("buildUpdateDescription", description);
    }
}
