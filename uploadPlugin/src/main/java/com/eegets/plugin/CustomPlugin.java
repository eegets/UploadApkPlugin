package com.eegets.plugin;


import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.eegets.BuildParams;
import com.eegets.plugin.task.UploadTask;

import org.gradle.api.DomainObjectSet;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Created by wangkai on 2021/12/06 10:37
 * <p>
 * Desc TODO
 */
public class CustomPlugin implements Plugin<Project> {

    private String LOG_HEAD_MESSAGE = "TestGroovyPlugin + ";

    private BuildParams params;

    @Override
    public void apply(Project project) {
        System.out.println(LOG_HEAD_MESSAGE + "Task Plugin is beginning");

        params = project.getExtensions().create("buildParams", BuildParams.class, project.getObjects());

        project.afterEvaluate(project1 -> {
            AppExtension appExtension = (AppExtension) project1.getExtensions().findByName("android");

            System.out.println(LOG_HEAD_MESSAGE + appExtension);

            DomainObjectSet<ApplicationVariant> appVariants = ((AppExtension) project1.getExtensions().findByName("android")).getApplicationVariants();
            for (ApplicationVariant applicationVariant : appVariants) {
                if (applicationVariant.getBuildType() != null) {
                    System.out.println(LOG_HEAD_MESSAGE + "applicationVariant.getBuildType: " + applicationVariant.getBuildType() + "; name: " + applicationVariant.getName());
                    dependsOnTask(applicationVariant, project1);
                }
            }
        });
    }

    private void dependsOnTask(ApplicationVariant variant, Project project) {
        String variantName = variant.getName().substring(0, 1).toUpperCase() + variant.getName().substring(1);
        System.out.println(LOG_HEAD_MESSAGE + "variantName: " + variantName);
        if (variantName.equals("Release")) {  //在release环境下执行上传，或者是变体Release环境行执行上传
            UploadTask uploadTask = project.getTasks().create("BuildUpload" + variantName, UploadTask.class);
            uploadTask.init(variant, params);
        } else {
            System.out.println(LOG_HEAD_MESSAGE + "variantName: is not PreviewRelease");
        }
    }
}
