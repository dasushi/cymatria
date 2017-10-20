precision mediump float;

varying vec3 v_Color;
varying float v_ElapsedTime;

void main() {
    float xDist = 0.5 - gl_PointCoord.x;
    float yDist = 0.5 - gl_PointCoord.y;
    float distanceFromCenter = sqrt(xDist * xDist + yDist * yDist);

    if(distanceFromCenter > 0.5) {
        discard;
    } else {
        gl_FragColor = vec4(v_Color / v_ElapsedTime, 1.0);
    }

}