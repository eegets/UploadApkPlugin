package com.eegets.plugin.task;

import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariant;
import com.android.build.gradle.api.BaseVariantOutput;
import com.eegets.FeishuExt;
import com.eegets.plugin.utils.FeishuUtils;
import com.eegets.plugin.utils.PgyerUtils;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;

import java.io.File;
import java.util.Objects;

/**
 * Created by wangkai on 2021/12/06 14:57
 * <p>
 * Desc TODO
 */
public class UploadTask extends DefaultTask {
    private String LOG_UPLOAD_TASK = "TestUploadTask + ";

    private BaseVariant variant;
    private Project project;

    private FeishuUtils feishu;
    private PgyerUtils pgyer;

    public void init(ApplicationVariant variant, Project project) {
        this.variant = variant;
        this.project = project;
        feishu = new FeishuUtils();
        pgyer = new PgyerUtils();

        System.out.println(LOG_UPLOAD_TASK + "init");

//        setDescription("tools of upload to third platform");
        setGroup("publishToThirdPlatform");

        for (BaseVariantOutput output: variant.getOutputs()) {
            File apkDir = output.getOutputFile();
            if (apkDir == null || !apkDir.exists()) {
                System.out.println(LOG_UPLOAD_TASK + "apkDir: " + apkDir + "; apkDir is null or no exists");
//                throw new GradleException("apkDir OutputFile is not exist");
            } else {
                System.out.println(LOG_UPLOAD_TASK + apkDir.getAbsolutePath());
                File filePath = findApkFile(apkDir);

                //获取到文件，执行上传
                pgyer.uploadApkPgyer(filePath.getAbsoluteFile(), "TestApk.apk");

                pgyer.getMessage();

                feishu.requestFeishuImageKey(PgyerUtils.saveImagePath);

                feishu.requestToken();

                FeishuExt.INSTANCE.pushMessageToFeishuHook("", "");

            }
        }
    }

    private File findApkFile(File apkDir) {
        File apk = null;
        if (apkDir.getName().endsWith(".apk")) {
            apk = apkDir;
        } else {
            if (apkDir.listFiles() != null) {
                for (int i = Objects.requireNonNull(apkDir.listFiles()).length - 1; i >= 0; i--) {
                    File apkFile = Objects.requireNonNull(apkDir.listFiles())[i];
                    if (apkFile != null && apkFile.exists() && apkFile.getName().endsWith(".apk")) {
                        apk = apkFile;
                        break;
                    }
                }
            }
        }

        if (apk == null || !apk.exists()) {
            System.out.println(LOG_UPLOAD_TASK + "apkDir: " + apkDir + "; apkDir is null or no exists");
        } else {
            System.out.println(LOG_UPLOAD_TASK + "final upload apk path: " + apk.getAbsolutePath());
        }
        return apk;
    }

}
