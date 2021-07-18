class Ray {
  PVector direction;
  float target_x, target_y;
  
  Ray(float angle) {
    direction = PVector.fromAngle(radians(angle));
  }
  
  void update() {
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
  
  void render() {
    line(mouseX, mouseY, target_x, target_y);
  }
}
