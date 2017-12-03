#include <jni.h>
#include <string>
#include <fstream>

extern "C"
JNIEXPORT jlong JNICALL
Java_com_chan_fuckdex_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {

    std::string file_name = "/sdcard/classes.dex";
    std::ifstream in;
    in.open(file_name.c_str(), std::ios::binary);
    if (!in) {
        return -1;
    }
    in.seekg(0, std::ios::end);
    long len = in.tellg();
    if (len <= 0) {
        return -2;
    }

    char *buffer = new char[len];
    in.seekg(0, std::ios::beg);
    in.read(buffer, len);
    //还有些检查流状态，上一次操作状态函数
    in.close();

    return (jlong) buffer;
}
