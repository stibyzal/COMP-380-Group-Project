import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class CancelReservation {
    private String reservationNum;

    public CancelReservation(String reservationNum){
        setReservationNum(reservationNum);
    }

    //setter method
    public void setReservationNum(String reservationNum){
        this.reservationNum = reservationNum;
        processCancellation(reservationNum);
    }


    protected void processCancellation(String key){
        String inputFile = "reservation.txt";

        try {
            //Read all lines from the file in a list
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            //Fiter out lines that contains the key
            List<String> filteredLines = new ArrayList<>();
            for(String line : lines){
                if(!line.contains(key)){
                    filteredLines.add(line);
                }
            }
            Files.write(Paths.get(inputFile), filteredLines);
            processRefund(key);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //processing refund; deleting payment info
    protected void processRefund(String key){
        String inputFile = "payment.txt"; // Path to payment txt file

        try {
            //Read all lines from the file in a list
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            //Fiter out lines that contains the key
            List<String> filteredLines = new ArrayList<>();
            for(String line : lines){
                if(!line.contains(key)){
                    filteredLines.add(line);
                }
            }
            Files.write(Paths.get(inputFile), filteredLines);
        }catch(IOException e){
            e.printStackTrace();
        }

        // Deleting address
        String address = "address.txt"; // Path to address txt file
        try {
            //Read all lines from the file in a list
            List<String> lines = Files.readAllLines(Paths.get(address));

            //Fiter out lines that contains the key
            List<String> filteredLines = new ArrayList<>();
            for(String line : lines){
                if(!line.contains(key)){
                    filteredLines.add(line);
                }
            }
            Files.write(Paths.get(address), filteredLines);
        }catch(IOException e){
            e.printStackTrace();
        }


    }

}