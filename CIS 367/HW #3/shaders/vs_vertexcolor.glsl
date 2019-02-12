attribute vec3 vertexPos;
attribute vec3 vertexCol;
varying vec3 varColor;

void main() {
  varColor = vertexCol;
  gl_Position = vec4 (vertexPos, 1);
}