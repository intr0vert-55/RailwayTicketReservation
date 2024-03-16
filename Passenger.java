class Passenger {
    static int id = 1;
    String name;
    int age;
    int passengerId;
    char preference;
    String seatNumber;
    Passenger(String name, int age, char preference){
        this.name = name;
        this.age = age;
        this.preference = preference;
        this.passengerId = id;
        id++;
    }
    public void printDetails(){
        System.out.println("ID : " + this.passengerId);
        System.out.println("Name : " + this.name);
        System.out.println("Age : " + this.age);
        System.out.println("SeatNumber : " + this.seatNumber);
        System.out.println();
    }
}
