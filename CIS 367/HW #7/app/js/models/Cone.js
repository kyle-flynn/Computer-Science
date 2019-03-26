import { ConeGeometry, Group, Mesh, MeshPhongMaterial } from "three";

export default class Cone extends Group {
  constructor(radius, height, segments, color) {
    super();

    const coneGeometry = new ConeGeometry(radius, height, segments, segments);
    const coneMaterial = new MeshPhongMaterial({ color: color ? color : 0xff});
    const coneMesh = new Mesh(coneGeometry, coneMaterial);

    this.add(coneMesh);
  }
}