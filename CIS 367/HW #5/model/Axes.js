import ObjectGroup from '../core/ObjectGroup';
import Arrow from '../model/Arrow';
import { mat4, glMatrix } from 'gl-matrix';

export default class Axes extends ObjectGroup {
  constructor({ glContext, positionAttribute, colorAttribute }) {
    super();

    // Setup Y-Axis
    let xAxis = new Arrow({
      glContext,
      positionAttribute,
      colorAttribute,
      color: [1, 0.3, 0]
    });
    let xFrame = mat4.fromRotation(mat4.create(), glMatrix.toRadian(90), [
      0,
      1,
      0
    ]);
    this.add({ object: xAxis, frame: xFrame });

    // Setup Y-Axis
    let yAxis = new Arrow({
      glContext,
      positionAttribute,
      colorAttribute,
      color: [0, 0.6, 0]
    });
    let yFrame = mat4.fromRotation(mat4.create(), glMatrix.toRadian(-90), [
      1,
      0,
      0
    ]);
    this.add({ object: yAxis, frame: yFrame });

    // Setup Z-axis
    let zAxis = new Arrow({
      glContext,
      positionAttribute,
      colorAttribute,
      color: [0, 0.5, 1]
    });
    this.add({ object: zAxis, frame: mat4.create() });
  }
}
