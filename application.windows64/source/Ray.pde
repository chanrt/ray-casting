class Ray {
  float x1, y1, x2, y2;
  
  Ray(float angle) {
    PVector direction = PVector.fromAngle(radians(angle));
    x2 = direction.x;
    y2 = direction.y;
  }
  
  void update() {
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
  
  float calcDistance(float x1, float y1, float x2, float y2) {
    return pow(x2 - x1, 2) + pow(y2 - y1, 2);
  }
  
  void render() {
    line(mouseX, mouseY, x1, y1);
  }
}
