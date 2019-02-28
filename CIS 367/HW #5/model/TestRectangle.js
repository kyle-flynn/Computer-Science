import GLGeometry from 'gl-geometry';
import ObjectGroup from '../core/ObjectGroup';
import Rectangle from '../geometry/Rectangle';
import { mat4, vec3 } from 'gl-matrix';
export default class TestRectangle extends ObjectGroup {
  constructor({ glContext, positionAttribute, colorAttribute }) {
    super();

    let testRectangle = new Rectangle({
      width: 0.5,
      height: 0.5,
      length: 0.5
    });
    let cylColors = [];
    for (let k = 0; k < 8; k++) cylColors.push(0.3, 0.3, 0.3);
    const cyl = new GLGeometry(glContext)
      .attr(positionAttribute, testRectangle.geometry())
      .attr(colorAttribute, cylColors);
    const cylCF = mat4.create();

    this.add({ object: cyl, frame: cylCF });
  }
}
