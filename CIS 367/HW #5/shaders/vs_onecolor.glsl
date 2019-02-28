attribute vec3 vertexPosition;

uniform mat4 modelView, projection;

void main() {
  // gl_PointSize = 6.0;
  gl_Position = projection * modelView * vec4 (vertexPosition, 1);
}