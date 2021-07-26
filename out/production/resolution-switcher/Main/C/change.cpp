#include <stdio.h>
#include <windows.h>
#include <winuser.h>

long change(int width, int height) {
    DEVMODE devmode;
    devmode.dmPelsWidth = 1024;
    devmode.dmPelsHeight = 768;
    devmode.dmFields = DM_PELSWIDTH | DM_PELSHEIGHT;
    devmode.dmSize = sizeof(DEVMODE);

    return ChangeDisplaySettings(&devmode, 0);
}