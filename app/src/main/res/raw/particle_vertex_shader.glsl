uniform mat4 u_Matrix;
uniform vec4 u_FFT = vec4(1.0, 0.5, 0.25, 0.5);
uniform float u_Time;

attribute vec3 a_Position;
attribute vec3 a_DirectionVector;
attribute vec3 a_Color;
attribute float a_ParticleStartTime;

varying vec3 v_Color;
varying float v_ElapsedTime;

void main(){
    v_Color = a_Color;
    v_ElapsedTime = u_Time - a_ParticleStartTime;


    vec3 currentPos = a_Position + (a_Theta * v_ElapsedTime);

    float shiftFactor = sin(n * pi * currentPos.x) * sin(m * pi * currentPos.y) +
                        sin(m * pi * currentPos.x) * sin(n * pi * currentPos.y);

        currentPos.x +=
    }
    currentPos.y = max(0.0, currentPos.y - gravityFactor);
    gl_Position = u_Matrix * vec4(currentPos, 1.0);
    gl_PointSize = 10.0;
}