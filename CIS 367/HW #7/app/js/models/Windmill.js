import { Group } from 'three';
import Base from './WindmillBase';
import Sails from './WindmillSails';
import Entrance from './WindmillEntrance';


export default class Windmill extends Group {
  constructor() {
    super();
    this.base = new Base();
    this.sails = new Sails();
    this.entrance = new Entrance();

    this.sails.translateY(1.25);
    this.sails.translateZ(2.7);

    this.entrance.translateZ(4.5);
    this.entrance.translateX(-1);
    this.entrance.translateY(-3.15);

    this.add(this.base);
    this.add(this.sails);
    this.add(this.entrance);
  }

  animate(clock) {
    this.sails.animate(clock);
    this.entrance.animate(clock);
  }

}