ArrayList<Wall> walls = new ArrayList<Wall>();
ArrayList<Ray> rays = new ArrayList<Ray>();
int num_walls = 3;
Ray ray;

float angle = 0;

void setup() {
  fullScreen();
  
  generateWalls();
}

void draw() {
  background(0);
  
  strokeWeight(10);
  stroke(0, 0, 256);
  for(Wall wall: walls) {
    wall.render();
  }
  
  rays.clear();
  for(int i = 0; i < 360; i += 2) {
    rays.add(new Ray(i));
  }
  
  strokeWeight(1);
  stroke(256, 256, 0);
  for(Ray ray: rays) {
    ray.update();
    ray.render();
  }
}

void generateWalls() {
  walls.clear();
  
  walls.add(new Wall(0, 0, width, 0));
  walls.add(new Wall(width, 0, width, height));
  walls.add(new Wall(width, height, 0, height));
  walls.add(new Wall(0, height, 0, 0));
  
  for(int i = 0; i < num_walls; i++) {
    walls.add(new Wall());
  }
}

void keyPressed() {
  if(key == 'r' || key == 'R') {
    generateWalls();
  }
  if(key == 'w' || key == 'W') {
    walls.add(new Wall());
  }
  if(key == 's' || key == 'S') {
    if(walls.size() > 4) {
      walls.remove(4);
    }
  }
}
