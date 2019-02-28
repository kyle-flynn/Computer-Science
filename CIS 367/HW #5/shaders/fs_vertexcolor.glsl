precision mediump float;
varying vec3 varColor;
void main() {
  if (gl_FrontFacing)
    gl_FragColor = vec4 (varColor, 1.0);
  else
    gl_FragColor = vec4 (0.3,0.3,0.3, 1.0);
}