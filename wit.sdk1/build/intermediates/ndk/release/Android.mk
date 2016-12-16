LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := witvad
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \

LOCAL_SRC_FILES := \
	/Users/brucexia/wit/wit.sdk1/src/main/jni/WITVadSimple.c \
	/Users/brucexia/wit/wit.sdk1/src/main/jni/WITVadWrapper.c \

LOCAL_C_INCLUDES += /Users/brucexia/wit/wit.sdk1/src/main/jni
LOCAL_C_INCLUDES += /Users/brucexia/wit/wit.sdk1/src/release/jni

include $(BUILD_SHARED_LIBRARY)
