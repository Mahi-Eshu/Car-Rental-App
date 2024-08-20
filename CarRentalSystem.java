
import java.util.*;


class Car{
    
    private String carId;
    private String carBrand;
    private String carModel;
    private double carPricePerDay;
    private boolean isAvailable;

    //public constructor
    public Car(String carId, String carBrand, String carModel, double carPricePerDay, boolean isAvailable){
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carPricePerDay = carPricePerDay;
        this.isAvailable = true; //calling the constructor means a new car has come to the garage, so availability is set true.
    }

    public String getId(){
        return carId;
    }
    public String getBrand(){
        return carBrand;
    }
    public String getModel(){
        return carModel;
    }
    public double calculatePrice(int days){
        return carPricePerDay * days;
    }
    public boolean getAvailability(){
        return isAvailable;
    }
    public void rent(){
        isAvailable = false;
    }
    public void returnCar(){
        isAvailable = true;
    }
}
class Customer{

    private String name;
    private String phone;

    public Customer(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }
}
class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }
}

public class CarRentalSystem{

    private static List<Car> cars = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Rental> rentals = new ArrayList<>();

    public static void addCarToList(Car car){
        cars.add(car);
    }
    public static void addCustomerToList(Customer customer){
        customers.add(customer);
    }
    public static void rentCar(Car car, Customer customer, int days){
        if(car.getAvailability()){
            rentals.add(new Rental(car, customer, days));
            System.out.println(car.getBrand() + " " + car.getModel() + " booked by " + customer.getName()+".");
            car.rent(); 
        }
        else{
            System.out.println("Car booked by other.");
        }
    }
    public static void returnYourCar(Car car){
        car.returnCar(); //Car method
        Rental toRemove = null;
        for(Rental rental : rentals){
            if(rental.getCar() == car){
                toRemove = rental;
                break;
            }
        }
        if(toRemove != null){
            rentals.remove(toRemove);
            System.out.println(car.getBrand() + " " + car.getModel() + " returned successfully! ");
        }
        else{
            System.out.println(car.getBrand() + " " + car.getModel() + " was not rented at all. ");
        }
    }

    public static void main(String[] args){

        Car c1 = new Car("C001", "Mahindra", "Thar", 1000.00, true); addCarToList(c1);
        Car c2 = new Car("C002", "Mahindra", "Bolero", 900.00, true); addCarToList(c2);
        Car c3 = new Car("C003", "Tata", "Punch", 800.00, true); addCarToList(c3);
        Car c4 = new Car("C004", "Toyota", "Innova", 1500.00, true); addCarToList(c4);
        
        Scanner sc = new Scanner (System.in);
        boolean run = true;
        while(run){
            System.out.println("--------------------");
            System.out.println("WELCOME TO CARMANIA!");
            System.out.println("--------------------");
            System.out.println("1. ADD CAR\n" + "2. RENT CAR\n" + "3. RETURN CAR\n" + "4. EXIT APP\n");
            
            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice){

                case 1:
                    System.out.println("---CAR ADDING---");
                    System.out.print("1. CAR ID: "); String carId = sc.next();
                    sc.nextLine();
                    System.out.print("2. CAR BRAND: "); String carBrand1 = sc.next();
                    sc.nextLine();
                    System.out.print("3. CAR MODEL: "); String carModel1 = sc.next();
                    sc.nextLine();
                    System.out.print("4. CAR PRICE PER DAY: "); double carPricePerDay = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("5. AVAILABILITY: "); boolean isAvailable = sc.nextBoolean();
                    sc.nextLine();

                    Car carToAdd = new Car(carId, carBrand1, carModel1, carPricePerDay, isAvailable);
                    addCarToList(carToAdd);
                    System.out.println(carBrand1 + " " + carModel1 + " added to the Garage.");
                    break;


                case 2:
                    System.out.println("---CAR RENTING---");
                    System.out.print("1. CUSTOMER NAME: "); String name = sc.next();
                    sc.nextLine();
                    System.out.print("2. CUSTOMER PHONE: "); String phone = sc.next();
                    sc.nextLine();
                    Customer customer = new Customer(name, phone);
                    addCustomerToList(customer);

                    System.out.println("\nAVAILABLE CARS: ");
                    for(Car car : cars){
                        System.out.println(car.getId() + " " + car.getBrand() + " " + car.getModel());
                    }
                    System.out.println();
                    System.out.print("1. CAR ID: "); String carIdToRent = sc.next();
                    sc.nextLine();
                    System.out.print("2. NO. OF DAYS: "); int days = sc.nextInt();
                    sc.nextLine();

                    Car carToRent = null;
                    for(Car car : cars){
                        if(car.getId().equals(carIdToRent)){
                            carToRent = car;
                            break;
                        }
                    }
                    if(carToRent == null){
                        System.out.println("This car is not Available in the Garage.");
                    }
                    else{
                        rentCar(carToRent, customer, days);
                    }
                    break;
                case 3:
                    System.out.println("---CAR RETURNING---");
                    System.out.print("1. CUSTOMER NAME: "); String name1 = sc.next();
                    sc.nextLine();
                    System.out.print("2. CAR ID: "); String carIdToReturn = sc.next();
                    sc.nextLine();
                    
                    Car carToReturn = null;
                    Customer customerToRemove = null;
                    int totalDays = 0;
                    for(Rental rental : rentals){
                        if(rental.getCar().getId().equals(carIdToReturn) && rental.getCustomer().getName().equals(name1)){
                            carToReturn = rental.getCar();
                            customerToRemove = rental.getCustomer();
                            totalDays = rental.getDays();
                            break;
                        }
                    }
                    if(carToReturn != null){
                        customers.remove(customerToRemove);
                        returnYourCar(carToReturn);
                        System.out.println("Total Payment: ₹" + carToReturn.calculatePrice(totalDays));
                    }
                    break;
                case 4:
                    System.out.println("THANK YOU FOR USING CARMANIA!");
                    run = false;
                    break;
                default:
                    break;
            }
        }
    }
}