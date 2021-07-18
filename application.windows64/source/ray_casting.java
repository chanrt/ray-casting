import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ray_casting extends PApplet {

ArrayList<Wall> walls = new ArrayList<Wall>();
ArrayList<Ray> rays = new ArrayList<Ray>();
int num_walls = 3;
Ray ray;

float angle = 0;

public void setup() {
  
  generateWalls();
}

public void draw() {
  background(0);
  
  strokeWeight(10);
  stroke(0, 0, 256);
  for(Wall wall: walls) {
    wall.render();
  }
  
  rays.clear();
  for(float i = 0; i < 360; i += 0.25f) {
    rays.add(new Ray(i));
  }
  
  strokeWeight(3);
  stroke(128, 128, 0, 128);
  for(Ray ray: rays) {
    ray.update();
    ray.render();
  }
}

public void generateWalls() {
  walls.clear();
  
  walls.add(new Wall(0, 0, width, 0));
  walls.add(new Wall(width, 0, width, height));
  walls.add(new Wall(width, height, 0, height));
  walls.add(new Wall(0, height, 0, 0));
  
  for(int i = 0; i < num_walls; i++) {
    walls.add(new Wall());
  }
}

public void keyPressed() {
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
class Ray {
  PVector direction;
  float target_x, target_y;
  
  Ray(float angle) {
    direction = PVector.fromAngle(radians(angle));
  }
  
  public void update() {
    float min_distance = 9999999, distance;
    int min_index = -1;
    
    for(int i = 0; i < walls.size(); i++) {
      if(walls.get(i).intersects(mouseX, mouseY, mouseX + direction.x, mouseY + direction.y)) {
        distance = dist(mouseX, mouseY, walls.get(i).getX(), walls.get(i).getY());
        
        if(distance < min_distance) {
          min_distance = distance;
          min_index = i;
        }
      }
    }
    if(min_index != -1) {
      target_x = walls.get(min_index).getX();
      target_y = walls.get(min_index).getY();
    }
  }
  
  public void render() {
    line(mouseX, mouseY, target_x, target_y);
  }
}
class Wall {
  float x3, y3, x4, y4;
  float a, b, c, d, k1, k2, xdiff, ydiff, deno;
  float s, t, x, y;

  Wall() {
    x3 = random(0, width);
    y3 = random(0, height);
    x4 = random(0, width);
    y4 = random(0, height);
  }
  Wall(float x3, float y3, float x4, float y4) {
    this.x3 = x3;
    this.y3 = y3;
    this.x4 = x4;
    this.y4 = y4;
  }
  public boolean intersects(float x1, float y1, float x2, float y2) {
    
    a = x1 - x2;
    d = y3 - y4;
    b = x3 - x4;
    c = y1 - y2;
    k1 = x1 - x3;
    k2 = y1 - y3;
    xdiff = x2 - x1;
    ydiff = y2 - y1;
    
    deno = a * d - b * c;
   
    if (deno == 0) {
      return false;
    }

    s = (k1 * d - k2 * b) / deno;
    t = (-a * k2 - ydiff * k1) / deno;

    if (s > 0 && 0 < t && t < 1) {
      x = x1 + s * xdiff;
      y = y1 + s * ydiff;
      return true;
    } else {
      return false;
    }
  }
  
  public float getX() {
    return x;
  }
  
  public float getY() {
    return y;
  }

  public void render() {
    line(x3, y3, x4, y4);
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ray_casting" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
