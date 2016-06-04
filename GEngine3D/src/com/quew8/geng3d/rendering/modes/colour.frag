#version 330

varying vec4 vColour;

layout(location = 0) out vec4 colourOut;

void main(void) {
    colourOut = vColour;
}
