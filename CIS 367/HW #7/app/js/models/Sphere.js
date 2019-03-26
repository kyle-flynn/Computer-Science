import { Group, SphereGeometry, Mesh, MeshPhongMaterial } from 'three';

export default class Sphere extends Group {
  constructor(radius, segments, color) {
    super();

    const sphereGeomety = new SphereGeometry(radius, segments, segments);
    const sphereMaterial = new MeshPhongMaterial({ color: color ? color : 0xff});
    const sphereMesh = new Mesh(sphereGeomety, sphereMaterial);

    this.add(sphereMesh);
  }

}