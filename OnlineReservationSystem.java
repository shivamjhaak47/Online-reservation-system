import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class OnlineReservationSystem {

    static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    static class Reservation {
        private static Map<String, String> reservations = new HashMap<>();

        public static String makeReservation(String username, String trainNumber, String trainName,
                String classType, String dateOfJourney,
                String fromPlace, String toPlace) {
            String pnrNumber = UUID.randomUUID().toString();
            String reservationDetails = String.format(
                    "Username: %s, Train Number: %s, Train Name: %s, Class Type: %s, Date: %s, From: %s, To: %s",
                    username, trainNumber, trainName, classType, dateOfJourney, fromPlace, toPlace);
            reservations.put(pnrNumber, reservationDetails);
            return pnrNumber;
        }

        public static String getReservationDetails(String pnrNumber) {
            return reservations.get(pnrNumber);
        }

        public static void cancelReservation(String pnrNumber) {
            reservations.remove(pnrNumber);
        }
    }

    static class Cancellation {
        public static boolean cancelTicket(String pnrNumber) {
            String reservationDetails = Reservation.getReservationDetails(pnrNumber);
            if (reservationDetails != null) {
                Reservation.cancelReservation(pnrNumber);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");

        System.out.println("Login");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User loggedInUser = null;
        if ((username.equals(user1.getUsername()) && password.equals(user1.getPassword())) ||
                (username.equals(user2.getUsername()) && password.equals(user2.getPassword()))) {
            loggedInUser = new User(username, password);
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials");
            return;
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Make Reservation");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    makeReservation(scanner, loggedInUser);
                    break;
                case 2:
                    cancelReservation(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void makeReservation(Scanner scanner, User loggedInUser) {
        System.out.print("Train Number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Train Name: ");
        String trainName = scanner.nextLine();
        System.out.print("Class Type: ");
        String classType = scanner.nextLine();
        System.out.print("Date of Journey (YYYY-MM-DD): ");
        String dateOfJourney = scanner.nextLine();
        System.out.print("From (Place): ");
        String fromPlace = scanner.nextLine();
        System.out.print("To (Place): ");
        String toPlace = scanner.nextLine();

        String pnrNumber = Reservation.makeReservation(loggedInUser.getUsername(), trainNumber, trainName,
                classType, dateOfJourney, fromPlace, toPlace);
        System.out.println("Reservation successful! Your PNR number is " + pnrNumber);
    }

    private static void cancelReservation(Scanner scanner) {
        System.out.print("Enter PNR number: ");
        String pnrNumber = scanner.nextLine();
        boolean success = Cancellation.cancelTicket(pnrNumber);
        if (success) {
            System.out.println("Reservation cancelled successfully.");
        } else {
            System.out.println("Invalid PNR number. No reservation found.");
        }
    }
}
