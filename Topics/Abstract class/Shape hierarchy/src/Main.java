
abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}

class Triangle extends Shape {
    double sideA;
    double sideB;
    double sideC;

    Triangle(double sideA, double sideB, double sideC) {
        super();
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    double getPerimeter() {
        return this.sideA + this.sideB + this.sideC;
    }

    @Override
    double getArea() {
        double p = this.getPerimeter() / 2;
        return Math.sqrt(p * (p - this.sideA) * (p - this.sideB) * (p - this.sideC));
    }
}

class Rectangle extends Shape {
    double sideA;
    double sideB;

    Rectangle(double sideA, double sideB) {
        super();
        this.sideA = sideA;
        this.sideB = sideB;
    }

    @Override
    double getPerimeter() {
        return (double) 2 * (this.sideA + this.sideB);
    }

    @Override
    double getArea() {
        return this.sideA * this.sideB;
    }
}

class Circle extends Shape {
    double radius;

    Circle(double radius) {
        super();
        this.radius = radius;
    }

    @Override
    double getPerimeter() {
        return (double) 2 * Math.PI * this.radius;
    }

    @Override
    double getArea() {
        return Math.PI * Math.pow(this.radius, 2);
    }
}