//
// Created by kk on 2019/10/29.
//

#include <jni.h>
#include <string>

//{sdk-path}/ndk-bundle/sysroot/usr/include/android
#include <android/log.h>

//定义输出的char
const chat * LOG_TAG = "KK_LOG_TAG";

extern "C" JNIEXPORT jstring JNICALL

Java_com_kk_hub_kotlin_module_StartNavigationActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "\n************* Hello from C++ *************\n";

    //输出warn级别的日志信息
    __android_log_print(ANDROID_LOG_WARN, LOG_TGA, "\n************* print Hello in jni *************\n");

    return env->NewStringUTF(hello.c_str());
}