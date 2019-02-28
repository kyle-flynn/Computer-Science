precision mediump float;
uniform vec3 pixelColor;

void main() {
  gl_FragColor = vec4 (pixelColor, 1.0);
}