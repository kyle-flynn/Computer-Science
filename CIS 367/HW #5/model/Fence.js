import GLGeometry from 'gl-geometry';
import ObjectGroup from '../core/ObjectGroup';
import Polygonal from '../geometry/Polygonal';
import { mat4, glMatrix } from 'gl-matrix';
export default class Fence extends ObjectGroup {
  constructor({ glContext, positionAttribute, colorAttribute, length = 0.5 }) {
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

    const topPost = new Polygonal({
      topRadius: 0.05,
      bottomRadius: 0.05,
      height: length,
      numberOfSlices: N
    });
    const topPostObj = new GLGeometry(glContext)
      .attr(positionAttribute, topPost.geometry())
      .attr(colorAttribute, fenceColors);
    const topPostCF = mat4.fromRotation(mat4.create(), glMatrix.toRadian(-90), [1, 0, 0]);
    mat4.translate(topPostCF, topPostCF, [0, -0.4, -0.5]);
    this.add(({ object: topPostObj, frame: topPostCF }));

    const botPost = new Polygonal({
      topRadius: 0.05,
      bottomRadius: 0.05,
      height: length,
      numberOfSlices: N
    });
    const botPostObj = new GLGeometry(glContext)
      .attr(positionAttribute, botPost.geometry())
      .attr(colorAttribute, fenceColors);
    const botPostCF = mat4.fromRotation(mat4.create(), glMatrix.toRadian(-90), [1, 0, 0]);
    mat4.translate(botPostCF, botPostCF, [0, -0.15, -0.5]);
    this.add(({ object: botPostObj, frame: botPostCF }));
  }
}
