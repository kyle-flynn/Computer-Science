import {Group} from 'three';
import Sphere from './Sphere';
import Rectangle from './Rectangle';

export default class WindmillSails extends Group {
  constructor() {
    super();

    const middle = new Sphere(0.5, 20, 0xe6e6e6);
    const sailOne = new Rectangle(0.1, 0.75, 3, 0x1a52ba);
    const sailTwo = new Rectangle(0.1, 0.75, 3, 0x1a52ba);
    const sailThree = new Rectangle(0.1, 0.75, 3, 0x1a52ba);

    sailOne.translateX(-1);
    sailOne.translateY(0.5);
    sailOne.rotateZ(Math.PI / 3);

    sailTwo.translateX(1);
    sailTwo.translateY(0.5);
    sailTwo.rotateZ(2 * Math.PI / 3);

    sailThree.translateY(-1.5);
    sailThree.rotateZ(3 * Math.PI / 3);

    this.add(middle);
    this.add(sailOne);
    this.add(sailTwo);
    this.add(sailThree);
  }

  animate() {
    this.rotation.z += 0.01;
  }
}