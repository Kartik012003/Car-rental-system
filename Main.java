import java.util.*;
import java.util.stream.Collectors;

class Car {
private String carId;
private String brand;
private String model;
private double basePricePerDay;
private boolean isAvailable;

public Car(String carId, String brand, String model, double basePricePerDay) 
  {
   this.carId = carId;
   this.brand = brand;
   this.model = model;
   this.basePricePerDay = basePricePerDay;
   this.isAvailable = true;
  }

public String getCarId() 
 {
   return carId;
 }

public String getBrand()
 {
  return brand;
 }

public String getModel() 
 {

  return model;
 }

public double calculatePrice(int rentalDays)
 {
   return basePricePerDay * rentalDays;
 }

public boolean isAvailable()
 {
  return isAvailable;
 }

public void rent()
 {
  isAvailable = false;
 }

public void returnCar()
 {
  isAvailable = true;
 }

@Override
public String toString()
 {
   return carId + " " + brand + " "  + model + "   basePricePerDay  " ;
 }
}

class Customer {
private String customerId;
private String name;

public Customer(String customerId, String name) {

this.customerId = customerId;
this.name = name;
}

public String getCustomerId() {
return customerId;
}

public String getName() {
return name;
}
}

class Rental {
private Car car;
private Customer customer;
private int days;

public Rental(Car car, Customer customer, int days) {
this.car = car;
this.customer = customer;
this.days = days;
}

public Car getCar() {
return car;
}

public Customer getCustomer() {
return customer;
}

public int getDays() {
return days;
}
}

class CarRentalSystem {

private List<Car> cars = new ArrayList<>();
private List<Customer> customers = new ArrayList<>();
private List<Rental> rentals = new ArrayList<>();
private Scanner scanner = new Scanner(System.in);

public void addCar(Car car) {
cars.add(car);
}

public void addCustomer(Customer customer) {
customers.add(customer);
}

private Optional<Car> findCarById(String carId) {
return cars.stream().filter(car ->
car.getCarId().equalsIgnoreCase(carId)).findFirst();
}

private Optional<Rental> findRentalByCar(Car car) {
return rentals.stream().filter(rental ->
 rental.getCar().equals(car)).findFirst();
}

public void rentCar(String carId, String customerName, int
rentalDays) {
Optional<Car> optionalCar = findCarById(carId);

if (optionalCar.isPresent() &&
optionalCar.get().isAvailable()) {
Car car = optionalCar.get();
Customer customer = new Customer("CUS" +
(customers.size() + 1), customerName);
customers.add(customer);

car.rent();
rentals.add(new Rental(car, customer, rentalDays));

System.out.printf("Car rented successfully! Total Price: $%.2f%n ", car.calculatePrice(rentalDays));
} 
else {
System.out.println("Car is not available or invalid carID");
}
}

public void returnCar(String carId) {
Optional<Car> optionalCar = findCarById(carId);

if (optionalCar.isPresent() &&!optionalCar.get().isAvailable()) {
Car car = optionalCar.get();
Optional<Rental> rental = findRentalByCar(car);

rental.ifPresent(rentals::remove);
car.returnCar();
System.out.println("Car returned successfully");

}

else {
System.out.println("Invalid car ID or the car was not rented");
}
}

public void displayAvailableCars() {
List<Car> availableCars =
cars.stream().filter(Car::isAvailable).collect(Collectors.toList());
if (availableCars.isEmpty()) {
System.out.println("No cars available for rent");
}

else {
availableCars.forEach(System.out::println);
}
}

public void menu() {
while (true)
{
	
System.out.println("===== Car Rental System=====");
System.out.println("1. Rent a Car");
System.out.println("2. Return a Car");
System.out.println("3. Exit");
System.out.print("Enter your choice:\n");

int choice;
try {
choice = Integer.parseInt(scanner.nextLine());
} catch (NumberFormatException e) {
System.out.println("Invalid input. Please enter a number");
continue;

}

switch (choice) {
case 1 -> {
System.out.println("== Rent a Car ==");
System.out.print("Enter your name:\n" );
String customerName = scanner.nextLine();

System.out.println("Available Cars:\n\n\n");
displayAvailableCars();

System.out.print("\nEnter the car ID you want to rent: ");
String carId = scanner.nextLine();

System.out.print("\nEnter the number of days for rental:\n\n");
int rentalDays =
Integer.parseInt(scanner.nextLine());

rentCar(carId, customerName, rentalDays);
}
case 2 -> {
System.out.println("\n== Return a Car ==");
System.out.print("Enter the car ID you want to return\n\n");
String carId = scanner.nextLine();

returnCar(carId);
}
case 3 ->{
System.out.println("Thank you for using the Car Rental System!\n\n");

return;
}
default -> System.out.println("Invalid choice. Please try again");
}
}
}
}

public class Main {
public static void main(String[] args) {
CarRentalSystem rentalSystem = new CarRentalSystem();

rentalSystem.addCar(new Car("C001", "Toyota" ,"Camry", 60.0));
rentalSystem.addCar(new Car("C002","Honda","Accord", 70.0));
rentalSystem.addCar(new Car("C003", "Mahindra","Thar", 150.0));

rentalSystem.menu();
   }
}
