import GLGeometry from 'gl-geometry';
import ObjectGroup from '../core/ObjectGroup';
import Rectangle, {RECTANGLE_VERTICES} from '../geometry/Rectangle';
import { mat4 } from 'gl-matrix';
export default class WindmillBase extends ObjectGroup {
  constructor({ glContext, positionAttribute, colorAttribute }) {
    super();

    let base = new Rectangle({
      width: 1.85,
      height: 0.5,
      length: 1.85
    });
    let baseColors = [];
    for (let k = 0; k < RECTANGLE_VERTICES; k++) {
      baseColors.push(0.4, 0.7, 0.4);
      baseColors.push(0.8, 0.9, 0.7);
    }
    const baseObj = new GLGeometry(glContext)
      .attr(positionAttribute, base.geometry())
      .attr(colorAttribute, baseColors);
    const baseCF = mat4.fromTranslation(mat4.create(), [0, -0.55, -0.125]);
    this.add({object: baseObj, frame: baseCF});
  }
}