export default class GeometricObject {
  constructor() {
    this.vertices = []; /* array of (x,y,z) triplets */
    /* array of vertex number triplets, each triplet specifies which
    vertex needed to make a triangle */
    this.triangles = [];
  }

  geometry() {
    // Return an object with two properties
    return {
      positions: this.vertices,
      cells: this.triangles
    };
  }
}
