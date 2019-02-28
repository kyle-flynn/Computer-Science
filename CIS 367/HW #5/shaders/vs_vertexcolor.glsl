attribute vec3 vertexPos;
attribute vec3 vertexCol;
varying vec3 varColor;
uniform mat4 modelView, projection, objectCoordFrame;

void main() {
  varColor = vertexCol;
  gl_Position = projection * modelView * objectCoordFrame * vec4 (vertexPos, 1);
}