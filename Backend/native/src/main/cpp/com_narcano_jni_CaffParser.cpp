#include <iostream>
#include <fstream>
#include <vector>
#include "com_narcano_jni_CaffParser.h"
#include "CAFFparser.h"
#include "CIFF.h"

#define FRAME_CLASS "com/narcano/jni/Frame"
#define FRAME_CTOR "(ILcom/narcano/jni/CIFF;)V"

#define CIFF_CLASS "com/narcano/jni/CIFF"
#define CIFF_CTOR "(IILjava/lang/String;[Ljava/lang/String;[I)V"

#define CAFF_CLASS "com/narcano/jni/CAFF"
#define CAFF_CTOR "(Lcom/narcano/jni/SimpleDate;Ljava/lang/String;[Lcom/narcano/jni/Frame;)V"

#define SIMPLE_DATE_CLASS "com/narcano/jni/SimpleDate"
#define SIMPLE_DATE_CTOR "(IIIII)V"

std::pair<jclass, jmethodID> findCtor(JNIEnv *env, const char *className, const char *signature) {
    jclass clazz = env->FindClass(className);
    jmethodID ctor = env->GetMethodID(clazz, "<init>", signature);
    return {clazz, ctor};
}

jobject createSimpleDate(JNIEnv *env, SimpleDate simpleDate) {
    std::pair<jclass, jmethodID> classData = findCtor(env, SIMPLE_DATE_CLASS, SIMPLE_DATE_CTOR);
    return env->NewObject(classData.first, classData.second,
                          simpleDate.year, simpleDate.month, simpleDate.day, simpleDate.hour, simpleDate.minute);
}

jobject createFrame(JNIEnv *env, jobject integer, jobject ciff) {
    std::pair<jclass, jmethodID> classData = findCtor(env, FRAME_CLASS, FRAME_CTOR);
    return env->NewObject(classData.first, classData.second, integer, ciff);
}

jobjectArray createList(JNIEnv *env, jclass clazz, std::vector<jobject> objVec) {
    jobjectArray result = env->NewObjectArray(objVec.size(), clazz, NULL);

    for (int i = 0; i < objVec.size(); ++i) {
        env->SetObjectArrayElement(result, i, objVec[i]);
    }
    return result;
}

jobjectArray createStringList(JNIEnv *env, std::vector<std::string> stringVec) {
    jobjectArray result = env->NewObjectArray(stringVec.size(), env->FindClass("java/lang/String"), NULL);

    for (int i = 0; i < stringVec.size(); ++i) {
        env->SetObjectArrayElement(result, i, env->NewStringUTF(stringVec[i].c_str()));
    }
    return result;
}

jintArray createPixelList(JNIEnv *env, std::vector<pixel> pixels) {
    int len = pixels.size() * 4;
    jintArray flatPixelsRGBA = env->NewIntArray(len);
    std::vector<jint> temp;
    temp.reserve(len);
    for (int i = 0; i < pixels.size(); ++i) {
        pixel p = pixels[i];
        temp.push_back(p.R);
        temp.push_back(p.G);
        temp.push_back(p.B);
        temp.push_back(1);
    }
    env->SetIntArrayRegion(flatPixelsRGBA, (jsize)0, (jsize)len, &temp[0]);
    return flatPixelsRGBA;
}

jobject createCIFF(JNIEnv *env, CIFF ciff) {
    std::pair<jclass, jmethodID> classData = findCtor(env, CIFF_CLASS, CIFF_CTOR);
    jobjectArray jStringList = createStringList(env, ciff.tags);
    jintArray jPixelList = createPixelList(env, ciff.pixels);
    return env->NewObject(classData.first, classData.second, ciff.height, ciff.width, env->NewStringUTF(ciff.caption.c_str()), jStringList, jPixelList);
}

jobject createInteger(JNIEnv *env, int i) {
    std::pair<jclass, jmethodID> classData = findCtor(env, "java/lang/Integer", "(I)V");
    return env->NewObject(classData.first, classData.second, i);
}

jobject createAnimationList(JNIEnv *env, std::vector<std::pair<int, CIFF>> animations) {
    std::vector<jobject> jAnimations;
    jAnimations.reserve(animations.size());

    for (std::pair<int, CIFF> frame: animations) {
        jobject jInt = createInteger(env, frame.first);
        jobject jCiff = createCIFF(env, frame.second);
        jobject jAnimation = createFrame(env, jInt, jCiff);
        jAnimations.push_back(jAnimation);
    }

    std::pair<jclass, jmethodID> classData = findCtor(env, FRAME_CLASS, FRAME_CTOR);

    jobjectArray result = createList(env, classData.first, jAnimations);
    return result;
}

JNIEXPORT jobject JNICALL Java_com_narcano_jni_CaffParser_parse(JNIEnv *env, jobject jobj, jstring jfilename) {
    try {
        const char *filename = env->GetStringUTFChars(jfilename, NULL);
        std::ifstream file(filename, std::ios::binary);

        CAFF caff = parsers::parseCAFF(file);

        jclass caffClass = env->FindClass(CAFF_CLASS);
        jmethodID ctor = env->GetMethodID(caffClass, "<init>", CAFF_CTOR);
        jobject jSimpleDate = createSimpleDate(env, caff.creationDate);
        jstring jCreator = env->NewStringUTF(caff.creator.c_str());
        jobject jAnimations = createAnimationList(env, caff.content);
        jobject jCAFF = env->NewObject(caffClass, ctor, jSimpleDate, jCreator, jAnimations);

        return jCAFF;
    } catch (...) {
        return NULL;
    }
}