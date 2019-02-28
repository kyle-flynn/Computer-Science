import { mat4 } from 'gl-matrix';

export default class ObjectGroup {
  constructor() {
    this.children = [];
    this.tempMat = mat4.create();
  }

  // Add a new member to the group, each object must be associated
  // with a coordinate frame to arrange that object within the group
  add({ object, frame }) {
    this.children.push({ object, frame });
  }

  // We are using the Visitor pattern here. The actual behavior
  // of the rendering logic is supplied as a function (first arg)
  render(renderer, frame) {
    if (typeof frame === 'undefined') {
      throw 'Must supply coordinate frame to render the group';
    }
    for (let k = 0; k < this.children.length; k++) {
      const ch = this.children[k];
      // pre-multiply the requested coordindate with this child's CF
      mat4.multiply(this.tempMat, frame, ch.frame);
      // If the child is also a GroupObject
      // do a recursive call on that child
      // otherwise call the renderer function directly
      if (ch.object instanceof ObjectGroup)
        ch.object.render(renderer, this.tempMat);
      else renderer(ch.object, this.tempMat);
    }
  }
}
