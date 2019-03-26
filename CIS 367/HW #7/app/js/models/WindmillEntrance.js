import { Group } from 'three';
import Fence from './Fence';

const ANIMATION_DURATION = 6;

export default class WindmillEntrance extends Group {
  constructor() {
    super();

    this.elapsedTime = 0.0;

    // Front fencing
    const leftFence = new Fence(0.1, 0.75, 2, 0xa0722c);
    this.midFence = new Fence(0.1, 0.75, 2, 0xa0722c);
    const rightFence = new Fence(0.1, 0.75, 2, 0xa0722c);

    // Left fencing
    const leftOne = new Fence(0.1, 0.75, 2, 0xa0722c);
    const leftTwo = new Fence(0.1, 0.75, 2, 0xa0722c);
    const leftThree = new Fence(0.1, 0.75, 2, 0xa0722c);
    const leftFour = new Fence(0.1, 0.75, 2, 0xa0722c);

    // Back fencing
    const backLeftFence = new Fence(0.1, 0.75, 2, 0xa0722c);
    const backMidFence = new Fence(0.1, 0.75, 2, 0xa0722c);
    const backRightFence = new Fence(0.1, 0.75, 2, 0xa0722c);

    // Right fencing
    const rightOne = new Fence(0.1, 0.75, 2, 0xa0722c);
    const rightTwo = new Fence(0.1, 0.75, 2, 0xa0722c);
    const rightThree = new Fence(0.1, 0.75, 2, 0xa0722c);
    const rightFour = new Fence(0.1, 0.75, 2, 0xa0722c);

    leftFence.translateX(-2);
    rightFence.translateX(2);

    leftOne.translateX(-2);
    leftOne.rotateY(Math.PI / 2);

    leftTwo.translateX(-2);
    leftTwo.translateZ(-2);
    leftTwo.rotateY(Math.PI / 2);

    leftThree.translateX(-2);
    leftThree.translateZ(-4);
    leftThree.rotateY(Math.PI / 2);

    leftFour.translateX(-2);
    leftFour.translateZ(-6);
    leftFour.rotateY(Math.PI / 2);

    rightOne.translateX(4);
    rightOne.rotateY(Math.PI / 2);

    rightTwo.translateX(4);
    rightTwo.translateZ(-2);
    rightTwo.rotateY(Math.PI / 2);

    rightThree.translateX(4);
    rightThree.translateZ(-4);
    rightThree.rotateY(Math.PI / 2);

    rightFour.translateX(4);
    rightFour.translateZ(-6);
    rightFour.rotateY(Math.PI / 2);

    backLeftFence.translateX(-2);
    backLeftFence.translateZ(-8);
    backMidFence.translateZ(-8);
    backRightFence.translateX(2);
    backRightFence.translateZ(-8);

    this.add(leftFence);
    this.add(this.midFence);
    this.add(rightFence);

    this.add(leftOne);
    this.add(leftTwo);
    this.add(leftThree);
    this.add(leftFour);

    this.add(rightOne);
    this.add(rightTwo);
    this.add(rightThree);
    this.add(rightFour);

    this.add(backLeftFence);
    this.add(backMidFence);
    this.add(backRightFence);
  }

  animate(clock) {
    this.elapsedTime += clock.getDelta();
    if (this.elapsedTime >= ANIMATION_DURATION) {
      this.elapsedTime = 0.0;
    }
    const deltaZ = ((Math.PI * 2) / ANIMATION_DURATION) * this.elapsedTime;
    this.midFence.rotation.y = Math.pow(Math.sin(deltaZ), 2);
  }
}