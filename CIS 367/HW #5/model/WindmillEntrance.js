import ObjectGroup from '../core/ObjectGroup';
import { mat4, vec3 } from 'gl-matrix';
import Fence from './Fence';
export default class WindmillEntrance extends ObjectGroup {
  constructor({ glContext, positionAttribute, colorAttribute }) {
    super();

    const baseCF = mat4.create();

    let leftFence = new Fence({
      glContext,
      positionAttribute,
      colorAttribute
    });
    this.leftCF = mat4.create();
    mat4.copy(this.leftCF, baseCF);

    this.add({ object: leftFence, frame: this.leftCF });

    let rightFence = new Fence({
      glContext,
      positionAttribute,
      colorAttribute
    });
    this.rightCF = mat4.create();
    mat4.translate(this.rightCF, this.rightCF, [0.0, 1.25, 0.0]);

    this.add({ object: rightFence, frame: this.rightCF });

    // Extra variables for the tick method
    this.maxCount = 45;
    this.currentCount = 0;
    this.needsSwitch = false;
  }

  tick() {
    if (this.needsSwitch) {
      this.currentCount--;
      this.children[0].frame = mat4.translate(this.leftCF, this.leftCF, [0, -0.005, 0]);
      this.children[1].frame = mat4.translate(this.rightCF, this.rightCF, [0, 0.005, 0]);
    }

    if (!this.needsSwitch) {
      this.currentCount++;
      this.children[0].frame = mat4.translate(this.leftCF, this.leftCF, [0, 0.005, 0]);
      this.children[1].frame = mat4.translate(this.rightCF, this.rightCF, [0, -0.005, 0]);
    }

    if (this.currentCount >= this.maxCount) {
      this.needsSwitch = true;
    }

    if (this.currentCount < 0) {
      this.needsSwitch = false;
    }
  }
}
