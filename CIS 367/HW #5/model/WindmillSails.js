import GLGeometry from 'gl-geometry';
import ObjectGroup from '../core/ObjectGroup';
import Sphere, {SPHERE_DIV} from '../geometry/Sphere';
import Rectangle, {RECTANGLE_VERTICES} from '../geometry/Rectangle';
import { mat4, glMatrix } from 'gl-matrix';
export default class WindmillSails extends ObjectGroup {
  constructor({ glContext, positionAttribute, colorAttribute }) {
    super();

    let sailFront = new Sphere({
      radius: 0.1
    });
    let sqailFrontColors = [];
    for (let k = 0; k < (SPHERE_DIV + 1) * (SPHERE_DIV + 1) + 1; k++) sqailFrontColors.push(0.9, 0.9, 0.9);
    const sailObj = new GLGeometry(glContext)
      .attr(positionAttribute, sailFront.geometry())
      .attr(colorAttribute, sqailFrontColors);
    const sailCF = mat4.create();

    this.add({ object: sailObj, frame: sailCF });

    let sailOne = new Rectangle({
      width: 0.05,
      height: 0.4,
      length: 0.2
    });
    const sailColors = [];
    for (let k = 0; k < RECTANGLE_VERTICES; k++) {
      sailColors.push(1, 1, 1);
      sailColors.push(0.3, 0.3, 0.3);
    }
    const sailOneObj = new GLGeometry(glContext)
      .attr(positionAttribute, sailOne.geometry())
      .attr(colorAttribute, sailColors);
    let sailOneCF = mat4.create();
    mat4.copy(sailOneCF, sailCF);
    mat4.fromRotation(sailOneCF, glMatrix.toRadian(-120), [1, 0, 0]);
    mat4.translate(sailOneCF, sailOneCF, [0.025, -0.1, 0.2]);
    this.add({ object: sailOneObj, frame: sailOneCF});

    let sailTwo = new Rectangle({
      width: 0.05,
      height: 0.4,
      length: 0.2
    });
    const sailTwoObj = new GLGeometry(glContext)
      .attr(positionAttribute, sailTwo.geometry())
      .attr(colorAttribute, sailColors);
    let sailTwoCF = mat4.create();
    mat4.copy(sailTwoCF, sailCF);
    mat4.fromRotation(sailTwoCF, glMatrix.toRadian(120), [1, 0, 0]);
    mat4.translate(sailTwoCF, sailTwoCF, [0.025, -0.1, 0.2]);
    this.add({ object: sailTwoObj, frame: sailTwoCF});

    let sailThree = new Rectangle({
      width: 0.05,
      height: 0.4,
      length: 0.2
    });
    const sailThreeObj = new GLGeometry(glContext)
      .attr(positionAttribute, sailThree.geometry())
      .attr(colorAttribute, sailColors);
    let sailThreeCF = mat4.create();
    mat4.copy(sailThreeCF, sailCF);
    mat4.fromRotation(sailThreeCF, glMatrix.toRadian(0), [1, 0, 0]);
    mat4.translate(sailThreeCF, sailThreeCF, [0.025, -0.1, 0.2]);
    this.add({ object: sailThreeObj, frame: sailThreeCF});
  }
}
