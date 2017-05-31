#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_csd_activitybase_utils_AppUtil_getProductNo(JNIEnv *env, jclass type) {
    return env->NewStringUTF("233");
}