import { Group } from 'three';
import Cylinder from './Cylinder';

export default class Fence extends Group {
  constructor(radius, height, length, color) {
    super();

    const fencePost1 = new Cylinder(radius, height, 20, color);
    const fencePost2 = new Cylinder(radius, height, 20, color);
    const fenceTop = new Cylinder(radius, length, 20, color);
    const fenceBot = new Cylinder(radius, length, 20, color);

    // Make the right fence post away from the first.
    fencePost2.translateX(length);

    // Make the top fence post rotated 90deg, and 1 radius away.
    fenceTop.rotateZ(Math.PI / 2);
    fenceTop.translateY(-1/2 * length);
    fenceTop.translateX(radius * 1.5);

    // Make the bot fence rotated 90deg, and 1 radius away, and 2 radius below top.
    fenceBot.rotateZ(Math.PI / 2);
    fenceBot.translateY(-1/2 * length);
    fenceBot.translateX(-radius * 1.5);

    this.add(fencePost1);
    this.add(fencePost2);
    this.add(fenceTop);
    this.add(fenceBot);
  }
}