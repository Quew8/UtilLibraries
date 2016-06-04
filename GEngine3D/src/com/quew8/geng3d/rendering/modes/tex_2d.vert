#version 330

layout(std140) uniform mat4 uProjectionMatrix;
layout(std140) uniform mat4 uModelMatrix;

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 texCoords;

varying vec2 vTexCoords;

void main(void) {
    vTexCoords = texCoords;
    gl_Position = uProjectionMatrix * uModelMatrix * vec4(position.xy, 0, 1);
}
