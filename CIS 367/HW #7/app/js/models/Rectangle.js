import {Group, BoxGeometry, Mesh, MeshPhongMaterial} from "three";

export default class Rectangle extends Group {
  constructor(length, width, height, color) {
    super();

    const cubeGeometry = new BoxGeometry(width, height, length);
    const cubeMaterial = new MeshPhongMaterial({ color: color ? color : 0xff});
    const cubeMesh = new Mesh(cubeGeometry, cubeMaterial);

    this.add(cubeMesh);
  }

}