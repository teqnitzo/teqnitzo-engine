#version 330 core
in vec3 vColor;
in vec2 vTexCoord;
in vec3 vNormal;

out vec4 FragColor;

uniform sampler2D uTexture;
uniform vec3 uLightDir;
uniform vec3 uLightColor;
uniform float uAmbientStrength;

void main() {
    vec3 normal = normalize(vNormal);
    vec3 lightDir = normalize(-uLightDir);

    float diffuseStrength = max(dot(normal, lightDir), 0.0);
    vec3 diffuse = diffuseStrength * uLightColor;

    vec3 ambient = uAmbientStrength * uLightColor;

    vec4 texColor = texture(uTexture, vTexCoord);
    vec3 lighting = ambient + diffuse;

    FragColor = vec4(texColor.rgb * vColor * lighting, texColor.a);
}