
class Shape {
    public double area() {
        return 0;
    }
}

class Triangle extends Shape {
    double height;
    double base;

    @Override
    public double area() {
        return this.height * this.base / 2;
    }
}

class Circle extends Shape {
    double radius;

    @Override
    public double area() {
        return Math.PI * Math.pow(this.radius, 2);
    }
}

class Square extends Shape {
    double side;

    @Override
    public double area() {
        return Math.pow(this.side, 2);
    }
}

class Rectangle extends Shape {
    double width;
    double height;

    @Override
    public double area() {
        return this.width * this.height;
    }
}