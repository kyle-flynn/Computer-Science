import createContext from 'gl-context';
import createGeometry from 'gl-geometry';
import createShader from 'gl-shader-core';
import fsOne from './shaders/fs_onecolor.glsl';
import vsOne from './shaders/vs_onecolor.glsl';
import vsMulti from './shaders/vs_vertexcolor.glsl';
import fsMulti from './shaders/fs_vertexcolor.glsl';

let triangle, quad, circle;
const POINTS_ON_CIRCLE = 30;
let oneColorShader = null;
let multiColorShader = null;

function renderFunc() {
  gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
  if (oneColorShader) {
    triangle.bind(oneColorShader);
    // Updating uniform variables must be done AFTER the shader is bound
    oneColorShader.uniforms.pixelColor = [0.0, 0.7, 0.0]; // 70% green
    triangle.draw(gl.TRIANGLES);
    triangle.unbind();
  }
  if (multiColorShader) {
    quad.bind(multiColorShader);
    quad.draw(gl.TRIANGLE_STRIP);
    quad.unbind();
    circle.bind(multiColorShader);
    circle.draw(gl.TRIANGLE_FAN);
    circle.unbind();
  }
}

function generateCircleVertices(ctrx, ctry, radius, numPoints) {
  let arr = [ctrx, ctry, 0]; // At z = 0
  for (let k = 0; k < numPoints + 1; k++) {
    const angle = (k / numPoints) * 2 * Math.PI;
    const xVal = radius * Math.cos(angle);
    const yVal = radius * Math.sin(angle);
    arr.push(ctrx + xVal, ctry + yVal, 0.0); // At z=0
  }
  return arr;
}

// Setup canvas and its render function
const canvas = document.getElementById('mycanvas');
const gl = createContext(canvas, {}, renderFunc);
const width = gl.drawingBufferWidth;
const height = gl.drawingBufferHeight;

gl.viewport(0, 0, width, height); // Use the entire canvas for our viewport
gl.clearColor(0.0, 0.0, 0.0, 1); // Use black to clear the canvas

gl.enable(gl.DEPTH_TEST); // Use DEPTH buffer for hidden surface removal

// Prepare data for different shapes
triangle = createGeometry(gl).attr(
  'vertexPosition',
  [[-0.8, -0.6, -0.25], [0.7, -0.6, 0.25], [-0.5, 0.7, 0.0]],
  { size: 3 }
);

quad = createGeometry(gl)
  // vertexPos is a vec3 attribute (x,y,z)
  .attr(
    'vertexPos',
    [[-0.4, 0.5, 0.0], [0.0, 0.0, 0.0], [0.8, 0.6, 0.0], [0.7, 0.1, 0.0]],
    {
      size: 3
    }
  )
  // vertexCol is a vec3 attribute (r,g,b)
  .attr('vertexCol', [[0, 0, 1], [1, 1, 0], [0, 0, 1], [1, 1, 0]], { size: 3 });

const circleVertices = generateCircleVertices(
  -0.2, // center-x
  -0.5, // center-y
  0.4, // radius
  POINTS_ON_CIRCLE
);

let circleColors = [1, 0, 0]; // Center is RED
for (let k = 0; k < POINTS_ON_CIRCLE + 1; k++) {
  circleColors.push(1, 0.5, 0); // Perimeter color is YELLOW
}
circle = createGeometry(gl)
  .attr('vertexPos', circleVertices, { size: 3 })
  .attr('vertexCol', circleColors, { size: 3 });

oneColorShader = createShader(
  gl,
  vsOne,
  fsOne,
  [{ type: 'vec3', name: 'pixelColor' }], // uniforms
  [{ type: 'vec3', name: 'vertexPosition' }] // attributes
);

multiColorShader = createShader(
  gl,
  vsMulti,
  fsMulti,
  [], // this shader does not use uniform variables
  // but it defines two attribute variables
  [{ type: 'vec3', name: 'vertexPos' }, { type: 'vec3', name: 'vertexCol' }]
);
