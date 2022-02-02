
class Employee {

    String name;
    int salary;
    String address;

    public Employee() {
        this.name = "unknown";
        this.address = "unknown";
        this.salary = 0;
    }

    public  Employee(String name, int salary) {
        this();
        this.name = name;
        this.salary = salary;
    }

    public Employee(String name, int salary, String address) {
        this.name = name;
        this.salary = salary;
        this.address = address;
    }
}