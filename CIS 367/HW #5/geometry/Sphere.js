import GeometricObject from './GeometricObject';

export const SPHERE_DIV = 12;

export default class Sphere extends GeometricObject {
  /* Create a UV sphere with the given radius. */
  constructor({ radius = 1.0 }) {
    super();

    let i, ai, si, ci;
    let j, aj, sj, cj;
    let p1, p2;

    for (j = 0; j <= SPHERE_DIV; j++) {
      aj = j * Math.PI / SPHERE_DIV;
      sj = Math.sin(aj);
      cj = Math.cos(aj);
      for (i = 0; i <= SPHERE_DIV; i++) {
        ai = i * 2 * Math.PI / SPHERE_DIV;
        si = Math.sin(ai);
        ci = Math.cos(ai);
        this.vertices.push([si * sj * radius, cj * radius, ci * sj * radius]);
      }
    }

    for (j = 0; j < SPHERE_DIV; j++) {
      for (i = 0; i < SPHERE_DIV; i++) {
        p1 = j * (SPHERE_DIV + 1) + i;
        p2 = p1 + (SPHERE_DIV + 1);

        this.triangles.push(p1, p2, p1 + 1);
        this.triangles.push(p1 + 1, p2, p2 + 1);
      }
    }

  }
}