precision mediump float;

uniform vec3 myColor;

void main() {
    gl_FragColor = vec4 (myColor, 1.0);
}