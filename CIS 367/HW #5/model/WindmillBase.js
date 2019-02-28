import GLGeometry from 'gl-geometry';
import ObjectGroup from '../core/ObjectGroup';
import Sphere, {SPHERE_DIV} from '../geometry/Sphere';
import Rectangle, {RECTANGLE_VERTICES} from '../geometry/Rectangle';
import { mat4, vec3 } from 'gl-matrix';
import Cone from "../geometry/Cone";
export default class WindmillBase extends ObjectGroup {
  constructor({ glContext, positionAttribute, colorAttribute }) {
    super();

    // let testRectangle = new Sphere({
    //   radius: 0.5
    // });
    // let cylColors = [];
    // for (let k = 0; k < (SPHERE_DIV + 1) * (SPHERE_DIV + 1) + 1; k++) cylColors.push(0.3, 1.0, 0.3);
    // const cyl = new GLGeometry(glContext)
    //   .attr(positionAttribute, testRectangle.geometry())
    //   .attr(colorAttribute, cylColors);
    // const cylCF = mat4.create();
    //
    // this.add({ object: cyl, frame: cylCF });

    let base = new Rectangle({
      width: 0.75,
      height: 1.5,
      length: 0.75
    });
    let baseColors = [];
    for (let k = 0; k < RECTANGLE_VERTICES; k++) baseColors.push(0.3, 0.3, 0.3);
    const baseObj = new GLGeometry(glContext)
      .attr(positionAttribute, base.geometry())
      .attr(colorAttribute, baseColors);
    const baseCF = mat4.fromTranslation(mat4.create(), [0, 0, 0.75]);
    this.add({ object: baseObj, frame: baseCF });

    let baseTop = new Cone({
      numberOfSlices: 30,
      radius: 0.65,
      height: 0.5
    });
    let baseTopColors = [];
    for (let k = 0; k < 2 * 30 + 2; k++) baseTopColors.push(0.9, 0.5, 0.3);
    const baseTopObj = new GLGeometry(glContext)
      .attr(positionAttribute, baseTop.geometry())
      .attr(colorAttribute, baseTopColors);
    const baseTopCF = mat4.fromTranslation(mat4.create(), [0, 0.375, 1.5]);
    this.add({ object: baseTopObj, frame: baseTopCF});

    let baseDoor = new Rectangle({
      width: 0.01,
      height: 0.6,
      length: 0.25
    });
    let baseDoorColors = [];
    for (let k = 0; k < RECTANGLE_VERTICES; k++) baseDoorColors.push(0.0, 0.0, 0.0);
    const baseDoorObj = new GLGeometry(glContext)
      .attr(positionAttribute, baseDoor.geometry())
      .attr(colorAttribute, baseDoorColors);
    const baseDoorCF = mat4.fromTranslation(mat4.create(), [0.375, 0.25, 0.3]);
    this.add({ object: baseDoorObj, frame: baseDoorCF });
  }
}
