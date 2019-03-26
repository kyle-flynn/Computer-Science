import { CylinderGeometry, Group, Mesh, MeshPhongMaterial } from "three";

export default class Cylinder extends Group {
  constructor(radius, height, segments, color) {
    super();

    const cylinderGeometry = new CylinderGeometry(radius, radius, height, segments, segments);
    const cylinderMaterial = new MeshPhongMaterial({ color: color ? color : 0xff});
    const cylinderMesh = new Mesh(cylinderGeometry, cylinderMaterial);

    this.add(cylinderMesh);
  }
}