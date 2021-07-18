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
  boolean intersects(float x1, float y1, float x2, float y2) {
    
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
