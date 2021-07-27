#include "Main_C_C.h"
#include <stdio.h>
#include <windows.h>
#include <winuser.h>
  
JNIEXPORT jlong JNICALL Java_Main_C_C_change
  (JNIEnv *, jclass, jint width, jint height) {
    DEVMODE devmode;
    devmode.dmPelsWidth = width;
    devmode.dmPelsHeight = height;
    devmode.dmFields = DM_PELSWIDTH | DM_PELSHEIGHT;
    devmode.dmSize = sizeof(DEVMODE);
    long result = ChangeDisplaySettings(&devmode, 0);
	return result;
}