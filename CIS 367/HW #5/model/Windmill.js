import ObjectGroup from '../core/ObjectGroup';
import { mat4, vec3 } from 'gl-matrix';
import WindmillBase from "./WindmillBase";
import WindmillSails from "./WindmillSails";
export default class Windmill extends ObjectGroup {
  constructor({ glContext, positionAttribute, colorAttribute }) {
    super();

    let base = new WindmillBase({
      glContext,
      positionAttribute,
      colorAttribute
    });
    let baseCF = mat4.create();

    this.add({ object: base, frame: baseCF });

    let sails = new WindmillSails({
      glContext,
      positionAttribute,
      colorAttribute
    });
    let sailsCF = mat4.fromTranslation(mat4.create(), [0.4, 0.375, 0.9]);

    this.add({ object: sails, frame: sailsCF });
  }
}
