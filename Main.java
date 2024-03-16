import java.util.InputMismatchException;
import java.util.Scanner;
class Main{
    public static void main(String [] args){
        TicketBooker booker = new TicketBooker();
        Scanner s = new Scanner(System.in);
        while(true){
            System.out.println("----Railway Ticket Booking System----");
            System.out.println("1. Book ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Booked Tickets list");
            System.out.println("4. Exit");
            System.out.println("5. Populate");
            System.out.print("Enter your choice : ");
            int choice = -1;
            try{
                choice = s.nextInt();
            } catch(InputMismatchException e){
                System.out.println("You were supposed to enter a number between 1 and 4. Now reopen the program");
                return;
            }
            switch(choice){
                case 1:
                    if(TicketBooker.availWL < 1){
                        System.out.println("No available tickets.....");
                        continue;
                    }
                    System.out.println("Enter the required details");
                    System.out.println("Enter your name : ");
                    s.nextLine();
                    String name = s.nextLine();
                    System.out.println("Enter your age : ");
                    int age = s.nextInt();
                    s.nextLine();
                    System.out.println("Enter your preference (Warning : If you don't enter properly as per below, you'll have to do the process all over again)");
                    System.out.println("U - Upper, M - Middle, L - Lower");
                    char pref = s.next().charAt(0);
                    if(pref > 96 && pref < 123){
                        pref -= 32;
                    }
                    if(pref != 85 && pref != 77 && pref != 76){
                        System.out.println("Invalid Preference. Re-enter the details");
                        continue;
                    } else{
                        Passenger p = new Passenger(name, age, pref);
                        booker.bookTicket(p, pref);
                    }
                    break;
                case 2:
                    System.out.println("Enter seat number along with the berth id (Eg. U12 - 12th seat in upper berth)");
                    s.nextLine();
                    String seat = s.nextLine();
                    booker.cancelTicket(seat);
                    break;
                case 3:
                    booker.bookedTickets();
                    break;
                case 4:
                    return;
                case 5:
                    booker.populate();
                default:
                    System.out.println("Invalid choice!!");
            }
        }
    }
}