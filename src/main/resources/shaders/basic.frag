#version 330 core
in vec3 vColor;
in vec2 vTexCoord;
in vec3 vNormal;

out vec4 FragColor;

uniform sampler2D uTexture;
uniform vec3 uLightDir;

void main() {
    vec3 normal = normalize(vNormal);
    vec3 lightDir = normalize(-uLightDir);

    float diffuse = max(dot(normal, lightDir), 0.2);

    vec4 texColor = texture(uTexture, vTexCoord);
    FragColor = texColor * vec4(vColor, 1.0) * diffuse;
}