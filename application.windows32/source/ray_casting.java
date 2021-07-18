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
  float x1, y1, x2, y2;
  
  Ray(float angle) {
    PVector direction = PVector.fromAngle(radians(angle));
    x2 = direction.x;
    y2 = direction.y;
  }
  
  public void update() {
    float min_distance = 999999999, distance;
    int min_index = -1;
    
    for(int i = 0; i < walls.size(); i++) {
      if(walls.get(i).intersects(mouseX, mouseY, mouseX + x2, mouseY + y2)) {
        distance = calcDistance(mouseX, mouseY, walls.get(i).getX(), walls.get(i).getY());
        
        if(distance < min_distance) {
          min_distance = distance;
          min_index = i;
        }
      }
    }
    if(min_index != -1) {
      x1 = walls.get(min_index).getX();
      y1 = walls.get(min_index).getY();
    }
  }
  
  public float calcDistance(float x1, float y1, float x2, float y2) {
    return pow(x2 - x1, 2) + pow(y2 - y1, 2);
  }
  
  public void render() {
    line(mouseX, mouseY, x1, y1);
  }
}
class Wall {
  float x1, y1, x2, y2;
  float x3, y3, x4, y4;
  float a, b, c, d, k1, k2, deno;
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
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    
    // println(x1, y1, x2, y2, x3, y3, x4, y4);
    deno = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
   
    if (deno == 0) {
      return false;
    }

    s = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / deno;
    t = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / deno;

    if (s > 0 && 0 < t && t < 1) {
      x = x1 + s * (x2 - x1);
      y = y1 + s * (y2 - y1);
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
