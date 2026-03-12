#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aColor;
layout (location = 2) in vec2 aTexCoord;
layout (location = 3) in vec3 aNormal;

out vec3 vColor;
out vec2 vTexCoord;
out vec3 vNormal;

uniform mat4 uMVP;

void main() {
    vColor = aColor;
    vTexCoord = aTexCoord;
    vNormal = aNormal;
    gl_Position = uMVP * vec4(aPos, 1.0);
}