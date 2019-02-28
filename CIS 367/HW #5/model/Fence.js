import GLGeometry from 'gl-geometry';
import ObjectGroup from '../core/ObjectGroup';
import Polygonal from '../geometry/Polygonal';
import { mat4 } from 'gl-matrix';
export default class Fence extends ObjectGroup {
  constructor({ glContext, positionAttribute, colorAttribute }) {
    super();
    const N = 20;
    const mainCF = mat4.create();

    let leftPost = new Polygonal({
      topRadius: 0.05,
      bottomRadius: 0.05,
      height: 0.5,
      numberOfSlices: N
    });
    let fenceColors = [];
    for (let k = 0; k < 2 * N + 2; k++) fenceColors.push(0.9, 0.8, 0.7);
    const leftPostObj = new GLGeometry(glContext)
      .attr(positionAttribute, leftPost.geometry())
      .attr(colorAttribute, fenceColors);
    const leftPostCF = mat4.translate(mat4.create(), mainCF, [0, 0, 0]);
    this.add({ object: leftPostObj, frame: leftPostCF });

    let rightPost = new Polygonal({
      topRadius: 0.05,
      bottomRadius: 0.05,
      height: 0.5,
      numberOfSlices: N
    });
    const rightPostObj = new GLGeometry(glContext)
      .attr(positionAttribute, rightPost.geometry())
      .attr(colorAttribute, fenceColors);
    const rightPostCF = mat4.translate(mat4.create(), mainCF, [0, -0.5, 0]);

    this.add({ object: rightPostObj, frame: rightPostCF });
  }
}
