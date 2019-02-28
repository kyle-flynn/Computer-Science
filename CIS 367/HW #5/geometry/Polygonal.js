import GeometricObject from './GeometricObject';
export default class Polygonal extends GeometricObject {
  /* Create a cylinder-like geometry with a given number of slices, top radius,
bottom radius, and height

 The cylinder base is a circle on the XY-plane centered at (0,0,0),
 the cylinder top is a cicle parallel to the XY-planet centered at (0,0,height)
*/
  constructor({
    numberOfSlices = 20,
    topRadius = 0.75,
    bottomRadius = 1.0,
    height = 1.0
  }) {
    super();
    const S = numberOfSlices;
    // Generate vertices for the base (0 to S-1)
    for (let k = 0; k < S; k++) {
      const angle = (k / S) * 2 * Math.PI;
      const xVal = bottomRadius * Math.cos(angle);
      const yVal = bottomRadius * Math.sin(angle);
      this.vertices.push([xVal, yVal, 0.0]); // At z=0
    }
    // Generate vertices for the top (S to 2S-1)
    for (let k = 0; k < S; k++) {
      const angle = (k / S) * 2 * Math.PI;
      const xVal = topRadius * Math.cos(angle);
      const yVal = topRadius * Math.sin(angle);
      this.vertices.push([xVal, yVal, height]); // At z=0
    }
    // the center of the base circle is vertex 2S
    this.vertices.push([0, 0, 0]); // center of bottom circle
    // the center of the top circle is vertex 2S+1
    this.vertices.push([0, 0, height]); // center of top circle

    // setup the triangles for the bottom cover
    for (let k = 0; k < S; k++) {
      // be sure to maintain CCW order of each triangle
      // CCW triplets: [2S,1,0], [2S,2,1], ..., [2S, S-1, S-2], [2S, 0, S-1]
      this.triangles.push([2 * S, (k + 1) % S, k]);
    }
    // setup the triangles for the top cover
    for (let k = 0; k < S; k++) {
      // CCW triplets: [2S+1,S,S+1], [2S+1, S+1, S+2], ...
      this.triangles.push([2 * S + 1, k + S, ((k + 1) % S) + S]);
    }
    // setup the triangles for the cylinder sleeve/side wall
    for (let k = 0; k < S; k++) {
      this.triangles.push([k, S + ((k + 1) % S), S + k]);
      this.triangles.push([k, (k + 1) % S, S + ((k + 1) % S)]);
    }
  }
}
