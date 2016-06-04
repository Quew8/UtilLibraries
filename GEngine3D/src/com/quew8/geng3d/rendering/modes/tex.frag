#version 330

uniform sampler2D uTexture;

varying vec2 vTexCoords;

layout(location = 0) out vec4 colourOut;

void main(void) {
    colourOut = texture(uTexture, vTexCoords);
}
