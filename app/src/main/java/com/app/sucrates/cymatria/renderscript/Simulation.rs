#pragma version(1)
#pragma rs java_packages_name(com.app.sucrates.cymatria.renderscript)

#include "rs_graphics.rsh"

float4 bgColor; //xyzw 4-part float


static void drawBackground() {
    rsgClearBackground(bgColor.x, bgColor.y, bgColor.z, bgColor.w);
}

void init() {
    bgColor = (float4){0.0f, 1.0f, 1.0f, 1.0f};
    //rsdebug defined in rs_core.rsh, rsUptime in rs_time.rsh under sdk directory
    rsDebug("Init Call", rsUptimeMillis());
}

int root() {
    drawBackground();
    return 16; //return 16ms frame time
}