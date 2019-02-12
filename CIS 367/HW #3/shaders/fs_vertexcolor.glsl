precision mediump float;
varying vec3 varColor;
void main() {
  gl_FragColor = vec4 (varColor, 1.0);
}