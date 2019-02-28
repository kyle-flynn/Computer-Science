import GeometricObject from './GeometricObject';

export default class Cone extends GeometricObject {
  /* Create a cone with a given number of slices and height

     The cone base is a circle on the XY-plane centered at (0,0,0),
     the cone tip is on the Z-axis at (0,0,height)
   */
  constructor({ numberOfSlices = 20, radius = 1.0, height = 1.0 }) {
    super();
    /* Points 0, 1, 2, ..., N-1 are on the base circle */
    for (let k = 0; k < numberOfSlices; k++) {
      const angle = (k / numberOfSlices) * 2 * Math.PI;
      const xVal = radius * Math.cos(angle);
      const yVal = radius * Math.sin(angle);
      this.vertices.push([xVal, yVal, 0.0]); // At z=0
    }
    /* Point N is the base center */
    this.vertices.push([0, 0, 0]); // center at base
    /* Point N+1 is the cone tip */
    this.vertices.push([0, 0, height]); // tip of the cone

    for (let k = 0; k < numberOfSlices; k++) {
      /* cells for the circle base are
         [N,0,1], [N,1,2],  ..., [N,N-2,N-1], [N, N-1, 0]
       */
      this.triangles.push([numberOfSlices, k, (k + 1) % numberOfSlices]);
      /* cell for the cone top are
         [N+1,0,1], [N+1,1,2], ... [N+1,N-2,N-1], [N+1,N-1,0]
       */
      this.triangles.push([numberOfSlices + 1, k, (k + 1) % numberOfSlices]);
    }
  }
}
