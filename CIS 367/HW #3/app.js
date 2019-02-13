import createContext from 'gl-context';
import createGeometry from 'gl-geometry';
import createShader from 'gl-shader-core';
import fsOne from './shaders/fs_onecolor.glsl';
import vsOne from './shaders/vs_onecolor.glsl';
import vsMulti from './shaders/vs_vertexcolor.glsl';
import fsMulti from './shaders/fs_vertexcolor.glsl';

const POINTS_ON_CIRCLE = 30;

let oneColorShader = null;
let multiColorShader = null;

let logoCircle, logoRect1, logoRect2, logoOutline;
let plantBottom, plantRect1, plantRect2, plantRect3, plantCircle, plantMouth, plantDivider, plantTooth1, plantTooth2;

function renderFunc() {
  gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
  if (oneColorShader) {
    logoRect1.bind(oneColorShader);
    oneColorShader.uniforms.pixelColor = [0.0, 0.0, 0.0];
    logoRect1.draw(gl.TRIANGLE_STRIP);
    logoRect1.unbind();
    logoRect2.bind(oneColorShader);
    logoRect2.draw(gl.TRIANGLE_STRIP);
    logoRect2.unbind();

    plantDivider.bind(oneColorShader);
    plantDivider.draw(gl.LINES);
    plantDivider.unbind();

    logoOutline.bind(oneColorShader);
    oneColorShader.uniforms.pixelColor = [1.0, 1.0, 1.0];
    logoOutline.draw(gl.LINE_STRIP);
    logoOutline.unbind();

    plantTooth1.bind(oneColorShader);
    plantTooth1.draw(gl.TRIANGLES);
    plantTooth1.unbind();

    plantTooth2.bind(oneColorShader);
    plantTooth2.draw(gl.TRIANGLES);
    plantTooth2.unbind();
  }
  if (multiColorShader) {
    // quad.bind(multiColorShader);
    // quad.draw(gl.TRIANGLE_STRIP);
    // quad.unbind();
    logoCircle.bind(multiColorShader);
    logoCircle.draw(gl.TRIANGLE_FAN);
    logoCircle.unbind();

    plantBottom.bind(multiColorShader);
    plantBottom.draw(gl.TRIANGLE_FAN);
    plantBottom.unbind();

    plantRect1.bind(multiColorShader);
    plantRect1.draw(gl.TRIANGLE_STRIP);
    plantRect1.unbind();

    plantRect2.bind(multiColorShader);
    plantRect2.draw(gl.TRIANGLE_STRIP);
    plantRect2.unbind();

    plantRect3.bind(multiColorShader);
    plantRect3.draw(gl.TRIANGLE_STRIP);
    plantRect3.unbind();

    // plantOutline.bind();
    // plantOutline.draw(gl.LINES);
    // plantOutline.unbind();

    plantCircle.bind(multiColorShader);
    plantCircle.draw(gl.TRIANGLE_FAN);
    plantCircle.unbind();

    plantMouth.bind(multiColorShader);
    plantMouth.draw(gl.TRIANGLE_FAN);
    plantMouth.unbind();
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

function generateCircleOutlineVertices(ctrx, ctry, radius, numPoints) {
  let arr = []; // At z = 0
  for (let k = 0; k < numPoints + 1; k++) {
    const angle = (k / numPoints) * 2 * Math.PI;
    const xVal = radius * Math.cos(angle);
    const yVal = radius * Math.sin(angle);
    arr.push(ctrx + xVal, ctry + yVal, 0.0); // At z=0
  }
  return arr;
}

function generateEllipseVertices(ctrx, ctry, radius, xmax, ymax, numPoints) {
  let arr = [ctrx, ctry, 0]; // At z = 0
  for (let k = 0; k < numPoints + 1; k++) {
    const angle = (k / numPoints) * 2 * Math.PI;
    const xVal = (radius * Math.cos(angle)) * xmax;
    const yVal = (radius * Math.sin(angle)) * ymax;
    arr.push(ctrx + xVal, ctry + yVal, 0.0); // At z=0
  }
  return arr;
}

function generateConeVertices(ctrx, ctry, radius, numPoints) {
  let arr = [ctrx, ctry, -0.1]; // At z = 0
  for (let k = 0; k < (numPoints / 2) + 1 ; k++) {
    const angle = (k / numPoints) * Math.PI;
    const xVal = radius * Math.cos(angle + (Math.PI / 4));
    const yVal = radius * Math.sin(angle + (Math.PI / 4));
    arr.push(ctrx + xVal, ctry + yVal, -0.1); // At z=0
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
// triangle = createGeometry(gl).attr(
//   'vertexPosition',
//   [[-0.8, -0.6, -0.25], [0.7, -0.6, 0.25], [-0.5, 0.7, 0.0]],
//   { size: 3 }
// );

// quad = createGeometry(gl)
//   // vertexPos is a vec3 attribute (x,y,z)
//   .attr(
//     'vertexPos',
//     [[-0.4, 0.5, 0.0], [0.0, 0.0, 0.0], [0.8, 0.6, 0.0], [0.7, 0.1, 0.0]],
//     {
//       size: 3
//     }
//   )
//   // vertexCol is a vec3 attribute (r,g,b)
//   .attr('vertexCol', [[0, 0, 1], [1, 1, 0], [0, 0, 1], [1, 1, 0]], { size: 3 });

const circleVertices = generateCircleVertices(
  0.5, // center-x
  0.25, // center-y
  0.5, // radius
  POINTS_ON_CIRCLE
);

let circleColors = [1, 0, 0]; // Center is RED
for (let k = 0; k < POINTS_ON_CIRCLE + 1; k++) {
  circleColors.push(0.3, 0, 0); // Perimeter color is a faded red
}

logoCircle = createGeometry(gl)
  .attr('vertexPos', circleVertices, { size: 3 })
  .attr('vertexCol', circleColors, { size: 3 });

const circleOutline = generateCircleOutlineVertices(
  0.5, // center-x
  0.25, // center-y
  0.49, // radius
  POINTS_ON_CIRCLE * 10
);

logoOutline = createGeometry(gl)
  .attr('vertexPos', circleOutline, { size: 3 });

logoRect1 = createGeometry(gl)
  .attr(
    'vertexPos',
    [
      [0.0, 0.1, 0.0], // TOP RIGHT
      [0.0, 0.0, 0.0], // BOTTOM RIGHT
      [1.0, 0.1, 0.0], // TOP LEFT
      [1.0, 0.0, 0.0] // BOTTOM LEFT
    ],
    {
      size: 3
    }
  );

logoRect2 = createGeometry(gl)
  .attr(
    'vertexPos',
    [
      [0.20, 0.75, 0.0], // TOP RIGHT
      [0.20, -0.25, 0.0], // BOTTOM RIGHT
      [0.40, 0.75, 0.0], // TOP LEFT
      [0.40, -0.25, 0.0] // BOTTOM LEFT
    ],
    {
      size: 3
    }
  );

const plantCircleVertices = generateEllipseVertices(
  -0.5, // center-x
  -0.85, // center-y
  0.4, // radius
  1.0,
  0.25,
  POINTS_ON_CIRCLE
);

let plantCircleColors = [0.9, 0.9, 0.9]; // Center is WHITE
for (let k = 0; k < POINTS_ON_CIRCLE + 1; k++) {
  plantCircleColors.push(0.75, 0.35, 0.1); // Kind of brownish on the outer edges
}

plantBottom = createGeometry(gl)
  .attr('vertexPos', plantCircleVertices, { size: 3 })
  .attr('vertexCol', plantCircleColors, { size: 3 });

plantRect1 = createGeometry(gl)
  .attr(
    'vertexPos',
    [
      [-0.75, -0.40, -0.1], // TOP RIGHT
      [-0.75, -0.85, -0.1], // BOTTOM RIGHT
      [-0.25, -0.40, -0.1], // TOP LEFT
      [-0.25, -0.85, -0.1] // BOTTOM LEFT
    ],
    {
      size: 3
    }
  )
  .attr(
    'vertexCol',
    [
      [0.0, 0.85, 0.0],
      [0.0, 0.85, 0.0],
      [0.0, 0.85, 0.0],
      [0.0, 0.85, 0.0]
    ],
    {
      size: 3
    }
  );

plantRect2 = createGeometry(gl)
  .attr(
    'vertexPos',
    [
      [-0.85, -0.20, 0.0], // TOP RIGHT
      [-0.85, -0.40, 0.0], // BOTTOM RIGHT
      [-0.15, -0.20, 0.0], // TOP LEFT
      [-0.15, -0.40, 0.0] // BOTTOM LEFT
    ],
    {
      size: 3
    }
  )
  .attr(
    'vertexCol',
    [
      [0.0, 0.85, 0.0],
      [0.0, 0.85, 0.0],
      [0.0, 0.85, 0.0],
      [0.0, 0.85, 0.0]
    ],
    {
      size: 3
    }
  );

plantRect3 = createGeometry(gl)
  .attr(
    'vertexPos',
    [
      [-0.45, 0.020, 0.0], // TOP RIGHT
      [-0.45, -0.20, 0.0], // BOTTOM RIGHT
      [-0.55, 0.020, 0.0], // TOP LEFT
      [-0.55, -0.20, 0.0] // BOTTOM LEFT
    ],
    {
      size: 3
    }
  )
  .attr(
    'vertexCol',
    [
      [0.0, 0.3, 0.0],
      [0.0, 0.3, 0.0],
      [0.0, 0.3, 0.0],
      [0.0, 0.3, 0.0]
    ],
    {
      size: 3
    }
  );

const plantHeadVertices = generateCircleVertices(
  -0.5, // center-x
  0.35, // center-y
  0.35, // radius
  POINTS_ON_CIRCLE
);

let plantHeadColors = [1, 0, 0]; // Center is RED
for (let k = 0; k < POINTS_ON_CIRCLE + 1; k++) {
  plantHeadColors.push(0.3, 0, 0); // Perimeter color is a faded red
}

plantCircle = createGeometry(gl)
  .attr('vertexPos', plantHeadVertices, { size: 3 })
  .attr('vertexCol', plantHeadColors, { size: 3 });

const plantMouthVertices = generateConeVertices(
  -0.5, // center-x
  0.35, // center-y
  0.35, // radius
  POINTS_ON_CIRCLE
);

let plantMouthColors = [0, 0, 0]; // Center is RED
for (let k = 0; k < POINTS_ON_CIRCLE + 1; k++) {
  plantMouthColors.push(0.0, 0.0, 0.0); // Perimeter color is a faded red
}

plantMouth = createGeometry(gl)
  .attr('vertexPos', plantMouthVertices, { size: 3 })
  .attr('vertexCol', plantMouthColors, { size: 3 });

plantDivider = createGeometry(gl)
  .attr(
    'vertexPos',
    [
      [-0.75, -0.40, -0.1], // TOP RIGHT
      [-0.75, -0.85, -0.1], // BOTTOM RIGHT
      [-0.25, -0.40, -0.1], // TOP LEFT
      [-0.25, -0.85, -0.1], // BOTTOM LEFT
      [-0.25, -0.85, -0.1],
      [-0.75, -0.85, -0.1],
    ],
    {
      size: 3
    }
  );

plantTooth1 = createGeometry(gl)
  .attr(
    'vertexPos',
    [
      [-0.2525, 0.6, -0.1],
      [-0.4, 0.45, -0.1],
      [-0.45, 0.625, -0.1]
    ],
    {
      size: 3
    }
  );

plantTooth2 = createGeometry(gl)
  .attr(
    'vertexPos',
    [
      // [-0.2525, 0.6, -0.1],
      // [-0.4, 0.45, -0.1],
      // [-0.45, 0.625, -0.1]
      [-0.55, 0.625, -0.1],
      [-0.6, 0.45, -0.1],
      [-0.75, 0.6, -0.1]
    ],
    {
      size: 3
    }
  );

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
