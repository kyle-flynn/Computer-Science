import GLGeometry from 'gl-geometry';
import ObjectGroup from '../core/ObjectGroup';
import Polygonal from '../geometry/Polygonal';
import Cone from '../geometry/Cone';
import { mat4, vec3 } from 'gl-matrix';
export default class Arrow extends ObjectGroup {
  constructor({ glContext, positionAttribute, colorAttribute, color }) {
    super();
    const N = 20;

    // The arrow body is a cylinder (height 0.8)
    // The arrow head is a cone (height 0.2)
    /* setup the cylinder */
    let cylGeom = new Polygonal({
      topRadius: 0.05,
      bottomRadius: 0.05,
      height: 0.8,
      numberOfSlices: N
    });
    let cylColors = [];
    for (let k = 0; k < 2 * N + 2; k++) cylColors.push(color);
    // for (let k = 0; k < N + 1; k++) cylColors.push([0.8, 0, 0]);
    const cyl = new GLGeometry(glContext)
      .attr(positionAttribute, cylGeom.geometry())
      .attr(colorAttribute, cylColors);
    const cylCF = mat4.create();

    this.add({ object: cyl, frame: cylCF });

    /* setup the cone */
    let coneGeom = new Cone({ numberOfSlices: N, radius: 0.08, height: 0.2 });
    let coneColors = [];
    for (let k = 0; k < N + 1; k++) coneColors.push(color);
    coneColors.push([1, 1, 1]);
    const cone = new GLGeometry(glContext)
      .attr(positionAttribute, coneGeom.geometry())
      .attr(colorAttribute, coneColors);
    const coneCF = mat4.fromTranslation(mat4.create(), [0, 0, 0.8]);
    this.add({ object: cone, frame: coneCF });
  }
}
