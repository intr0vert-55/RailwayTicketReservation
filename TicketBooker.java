import java.util.HashMap;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedHashMap;
class TicketBooker {

    // All the process happens here

    HashMap<Integer, Passenger> upperBerth;
    HashMap<Integer, Passenger> middleBerth;
    HashMap<Integer, Passenger> lowerBerth;

    // Maps for mapping the seatnumber along with the passenger

    LinkedHashMap<Integer, Passenger> rac;
    LinkedHashMap<Integer, Passenger> waitingList;

    // LinkedHashMaps for RAC and Waiting List. Here this works like a queue, first in first out by preserving the order of insertion

    Set<Integer> availableTickets;

    // Set is made for keeping track of the available tickets

    static int availUBerth;
    static int availMBerth;
    static int availLBerth;
    static int availRAC;
    static int availWL;

    // All static integers are for keeping track of the tickets in particular

    TicketBooker(){
        upperBerth = new HashMap<Integer, Passenger>();
        middleBerth = new HashMap<Integer, Passenger>();
        lowerBerth = new HashMap<Integer, Passenger>();
        rac = new LinkedHashMap<Integer, Passenger>();
        waitingList = new LinkedHashMap<Integer, Passenger>();
        availableTickets = new TreeSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
        21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40));
        // The set is given as treeset and is populated from 1 - 40 where 1 - 10 are UB, 11 - 20 are MB, 21 - 30 are LB 30 - 35 for RAC and 36 - 40 for WL
        // TreeSet is used to preserve the ascending order so that when someone cancels a ticket, that cancelled ticket will be given to the first person in RAC
        // and so on with the WL. But in RAC, since linkedhashmaps are used, it uses FIFO
        availUBerth = 10;
        availMBerth = 10;
        availLBerth = 10;
        availRAC = 5;
        availWL = 5;
    }

    public void bookTicket(Passenger p, char preference){

        // if(availWL < 1){
        //     System.out.println("No available tickets.....");
        //     return;
        // }

        // This commented code is implemented in the main class

        // The prefernce is checked in the main class itself. So now, if Ub isn't available it'll go on the order U->M->L and for L, it goes like L->M->U
        // If no berth is available, bookRAC() will be called and if RAC is full it will redirect it to bookWL()

        switch(preference){
            case 'U':
                if(availUBerth > 0){
                    System.out.println("UpperBerth available");
                    bookUpperBerth(p);
                }
                else if(availMBerth > 0){
                    System.out.println("UpperBerth isn't available :(");
                    bookMiddleBerth(p);
                }
                else if(availLBerth > 0){
                    System.out.println("UpperBerth isn't available :(");
                    bookLowerBerth(p);
                }
                else bookRAC(p);
                break;
            case 'M':
                if(availMBerth > 0){
                    System.out.println("MiddleBerth available");
                    bookMiddleBerth(p);
                }
                else if(availUBerth > 0){
                    System.out.println("MiddleBerth isn't available :(");
                    bookUpperBerth(p);
                }
                else if(availLBerth > 0){
                    System.out.println("MiddleBerth isn't available :(");
                    bookLowerBerth(p);
                }
                else bookRAC(p);
                break;
            case 'L':
                if(availLBerth > 0){
                    System.out.println("LowerBerth available");
                    bookLowerBerth(p);
                }
                else if(availMBerth > 0){
                    System.out.println("LowerBerth isn't available :(");
                    bookMiddleBerth(p);
                }
                else if(availUBerth > 0){
                    System.out.println("LowerBerth isn't available :(");
                    bookUpperBerth(p);
                }
                else bookRAC(p);
                break;
        }
    }

    // In here the first available seat is taken. It is to be noted that if avail*Berth = 0, it won't reach the following three functions
    // according to the "*" and in MBerth and LBerth there will be a condition to get the first M or L berth available
    // Now the seat being selected will be removed from the availableTickets set, the seat number is also varied for M, L, RAC and WL
    // The selected seat in the passenger class is updated and the seat is allotted by putting it in the hashMap

    public void bookUpperBerth(Passenger p){
        System.out.println("Booking Upper Berth.....");
        int selectedSeat = 0;
        for(int i : availableTickets){
            selectedSeat = i;
            break;
        }
        availableTickets.remove(selectedSeat);
        availUBerth--;
        p.seatNumber = "U" + selectedSeat;
        upperBerth.put(selectedSeat, p);
        System.out.println("Booking Successful. The seat number is : " + p.seatNumber);
    }

    public void bookMiddleBerth(Passenger p){
        System.out.println("Booking Middle Berth.....");
        int selectedSeat = 0;
        for(int i : availableTickets){
            if(i > 10){
                selectedSeat = i;
                break;
            }
        }
        availableTickets.remove(selectedSeat);
        selectedSeat -= 10;
        availMBerth--;
        p.seatNumber = "M" + selectedSeat;
        middleBerth.put(selectedSeat, p);
        System.out.println("Booking Successful. The seat number is : " + p.seatNumber);
    }

    public void bookLowerBerth(Passenger p){
        System.out.println("Booking Lower Berth.....");
        int selectedSeat = 0;
        for(int i : availableTickets){
            if(i > 20){
                selectedSeat = i;
                break;
            }
        }
        availableTickets.remove(selectedSeat);
        selectedSeat -= 20;
        availLBerth--;
        p.seatNumber = "L" + selectedSeat;
        lowerBerth.put(selectedSeat, p);
        System.out.println("Booking successful. The seat number is : " + p.seatNumber);
    }

    public void bookRAC(Passenger p){
        if(availRAC < 1){
            bookWL(p);
        } else{
            System.out.println("All berths are filled...\n Booking in RAC.....");
            int selectedSeat = 0;
            for(int i : availableTickets){
                if(i > 30){
                    selectedSeat = i;
                    break;
                }
            }
            availableTickets.remove(selectedSeat);
            selectedSeat -= 30;
            availRAC--;
            p.seatNumber = "R" + selectedSeat;
            rac.put(selectedSeat, p);
            System.out.println("Booking done in RAC. The id is : " + p.seatNumber);
        }
    }

    public void bookWL(Passenger p){
        System.out.println("All berths are filled and RAC tickets are full as well.... \nBooking on waiting list....");
        int selectedSeat = 0;
        for(int i : availableTickets){
            if(i > 35){
                selectedSeat = i;
                break;
            }
        }
        availableTickets.remove(selectedSeat);
        selectedSeat -= 35;
        availWL--;
        p.seatNumber = "W" + selectedSeat;
        waitingList.put(selectedSeat, p);
        System.out.println("Your booking is in waiting list. The id is : " + p.seatNumber);
    }

    public void cancelTicket(String seatNumber){
        char pref = seatNumber.charAt(0);
        int seat = 0;
        int index = 1;
        while(index < seatNumber.length()){
            seat = seat * 10 + ((int) seatNumber.charAt(index++) - 48);
        }
        int val = 0;
        switch(pref){
            case 'U':
                val = 0;
                break;
            case 'M':
                val = 10;
                break;
            case 'L':
                val = 20;
                break;
            case 'R':
                val = 30;
                break;
            case 'W':
                val = 35;
                break;
            default:
                System.out.println("Invalid seat number");
                return;
        }
        int checker = seat + val;

        if(availableTickets.contains(checker))  System.out.println("The seat is still available");

        else if(checker > 40)   System.out.println("Invalid Seat Number");

        else{
            switch(pref){
                case 'U':
                    System.out.println(upperBerth.get(seat).name + "'s ticket cancelled successfully");
                    upperBerth.remove(seat);
                    availableTickets.add(checker);
                    availUBerth++;
                    updateRACList();
                    break;
                case 'M':
                    System.out.println(middleBerth.get(seat).name + "'s ticket cancelled successfully");
                    middleBerth.remove(seat);
                    availableTickets.add(checker);
                    availMBerth++;
                    updateRACList();
                    break;
                case 'L':
                    System.out.println(lowerBerth.get(seat).name + "'s ticket cancelled successfully");
                    lowerBerth.remove(seat);
                    availableTickets.add(checker);
                    availLBerth++;
                    updateRACList();
                    break;
                case 'R':
                    System.out.println(rac.get(seat).name + "'s ticket cancelled successfully");
                    rac.remove(seat);
                    availableTickets.add(checker);
                    availRAC++;
                    updateWLList();
                    break;
                case 'W':
                    System.out.println(waitingList.get(seat).name + "'s ticket cancelled successfully");
                    waitingList.remove(seat);
                    availableTickets.add(checker);
                    availWL++;
                    break;
            }
        }
    }

    public void updateRACList(){
        int id = 0;
        for(int i : rac.keySet()){
            id = i;
            break;
        }
        if(id == 0) return;
        System.out.println("Updating RAC list....");
        char pref = rac.get(id).preference;
        Passenger p = rac.get(id);
        id += 30;
        availableTickets.add(id);
        availRAC++;
        updateWLList();
        System.out.println("Passenger "+ p.name + " is sent to the booking list.....");
        bookTicket(p, pref);
    }

    public void updateWLList(){
        int id = 0;
        for(int i : waitingList.keySet()){
            id = i;
            break;
        }
        if(id == 0) return;
        System.out.println("Updating Waiting List....");
        Passenger p = waitingList.get(id);
        waitingList.remove(id);
        id += 35;
        availableTickets.add(id);
        availWL++;
        System.out.println("Passenger " + p.name + " is sent to the RAC list");
        bookRAC(p);
    }

    public void bookedTickets(){
        System.out.println();
        if(!upperBerth.isEmpty()){
            System.out.println("UpperBerth passengers....");
            System.out.println();
            for(int key : upperBerth.keySet()){
                Passenger p = upperBerth.get(key);
                p.printDetails();
            }
            System.out.println();
        }
        if(!middleBerth.isEmpty()){
            System.out.println("MiddleBerth passengers....");
            System.out.println();
            for(int key : middleBerth.keySet()){
                Passenger p = middleBerth.get(key);
                p.printDetails();
            }
            System.out.println();
        }
        if(!lowerBerth.isEmpty()){
            System.out.println("LowerBerth passengers.....");
            System.out.println();
            for(int key : lowerBerth.keySet()){
                Passenger p = lowerBerth.get(key);
                p.printDetails();
            }
            System.out.println();
        }
        if(!rac.isEmpty()){
            System.out.println("RAC list.....");
            System.out.println();
            for(int key : rac.keySet()){
                Passenger p = rac.get(key);
                p.printDetails();
            }
            System.out.println();
        }
        if(!waitingList.isEmpty()){
            System.out.println("Waiting List....");
            System.out.println();
            for(int key : waitingList.keySet()){
                Passenger p = waitingList.get(key);
                p.printDetails();
            }
            System.out.println();
        }
        System.out.println("End of List");
    }

    public void populate(){
        String name = "Mukesh";
        int age = 21;
        char preference = 'U';
        for(int i = 0; i < 40; i++){
            System.out.println("Row : " + (i + 1));
            Passenger p = new Passenger(name, age, preference);
            bookTicket(p, preference);
        }
    }
}
