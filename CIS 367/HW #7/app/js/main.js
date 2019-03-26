import * as THREE from 'three';
// import orbit from 'three-orbit-controls';
// const OrbitControls = orbit(THREE);
import TrackballControls from 'three-trackballcontrols';
import Windmill from './models/Windmill';

export default class App {
  constructor() {
    const c = document.getElementById('mycanvas');
    // Enable antialias for smoother lines
    this.renderer = new THREE.WebGLRenderer({canvas: c, antialias: true});
    this.scene = new THREE.Scene();
    // Use perspective camera:
    //   Field of view: 75 degrees
    //   Screen aspect ration 4:3
    //   Near plane at z=0.5, far plane at z=500
    this.camera = new THREE.PerspectiveCamera(75, 4/3, 0.5, 500);
    // Place the camera at (0,0,100)
    this.camera.position.z = 100;

    this.clock = new THREE.Clock(true);

    // const orbiter = new OrbitControls(this.camera);
    // orbiter.enableZoom = false;
    // orbiter.update();
    this.tracker = new TrackballControls(this.camera);
    this.tracker.rotateSpeed = 2.0;
    // Allow zoom and pan
    this.tracker.noZoom = false;
    this.tracker.noPan = false;

    // Dodecahedron radius = 30
    const dodecgeom = new THREE.DodecahedronGeometry(30);
    const dodecmatr = new THREE.MeshBasicMaterial({color: 0x14ae6e});
    const dodecmesh = new THREE.Mesh(dodecgeom, dodecmatr);
    // this.scene.add(dodecmesh);

    this.windmill = new Windmill();
    this.scene.add(this.windmill);

    const lightOne = new THREE.DirectionalLight(0xFFFFFF, 1.0);
    lightOne.position.set(10, 40, 100);
    this.scene.add(lightOne);

    window.addEventListener('resize', () => this.resizeHandler());
    this.resizeHandler();
    requestAnimationFrame(() => this.render());
  }

  render() {
    this.renderer.render(this.scene, this.camera);
    this.tracker.update();
    this.windmill.animate(this.clock);
    // this.myWheel.matrix.multiply (this.rotZ1);
    // setup the render function to "autoloop"
    requestAnimationFrame(() => this.render());
  }

  resizeHandler() {
    const canvas = document.getElementById("mycanvas");
    let w = window.innerWidth - 16;
    let h = 0.75 * w;  /* maintain 4:3 ratio */
    if (canvas.offsetTop + h > window.innerHeight) {
      h = window.innerHeight - canvas.offsetTop - 16;
      w = 4/3 * h;
    }
    canvas.width = w;
    canvas.height = h;
    this.camera.updateProjectionMatrix();
    this.renderer.setSize(w, h);
    this.tracker.handleResize();
  }
}