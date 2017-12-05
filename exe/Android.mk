LOCAL_PATH := $(call my-dir)

TARGET_PIE := true
NDK_APP_PIE := true
APP_STL := stlport_static

include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
  drizzleDumper.cpp
LOCAL_C_INCLUDE := \
  drizzleDumper.h \
  definitions.h  

LOCAL_MODULE := drizzleDumper
LOCAL_MODULE_TAGS := optional

# Allow execution on android-16+
LOCAL_CFLAGS += -fPIE
LOCAL_LDFLAGS += -fPIE -pie

include $(BUILD_EXECUTABLE)

include $(call all-makefiles-under,$(LOCAL_PATH))
