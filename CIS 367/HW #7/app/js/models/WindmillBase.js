import { Group } from 'three';
import Rectangle from './Rectangle';
import Cone from './Cone';

export default class WindmillBase extends Group {
  constructor() {
    super();

    const bottom = new Rectangle(5, 5, 7, 0x4d4d4d);
    const top = new Cone(4, 3, 20, 0xe6804d);
    const door = new Rectangle(0.05, 1.25, 2, 0x0a0a0a);
    const ground = new Rectangle(25, 25, 1, 0x66b466);

    // Place top cone on top of the windmill base.
    top.translateY(5);

    door.translateZ(2.5);
    door.translateY(-2.5);

    ground.translateY(-4);

    this.add(bottom);
    this.add(top);
    this.add(door);
    this.add(ground);
  }
}