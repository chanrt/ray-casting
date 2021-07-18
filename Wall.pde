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
  boolean intersects(float x1, float y1, float x2, float y2) {
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
  
  float getX() {
    return x;
  }
  
  float getY() {
    return y;
  }

  void render() {
    line(x3, y3, x4, y4);
  }
}
