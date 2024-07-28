package javaOOP.HotelCalifornia;
import java.util.Scanner;

public class RoomChoice{

    private String[] roomTypes = {"Single room", "Twin room", "Executive room", "Suite"};
    private String room;
    private String choice;

    //Set method
    public void setRoom(String room){
        this.room = room;
    }
    //Get method
    public String getRoom(){
        return this.room;
    }
    public String[] getRoomTypes(){
        return this.roomTypes;
    }
    public String getChoice(){
        return this.choice;
    }

    //Other method
    //Method for getting the room from guest
    public void roomChoice(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the room type: ");
        for(int i = 0; i < this.roomTypes.length; i++){
            System.out.println(i + ") " + roomTypes[i]);
        }
        System.out.print("Enter the corresponding room type: ");
        int select = scanner.nextInt();
        this.choice = this.roomTypes[select];

    }

}
