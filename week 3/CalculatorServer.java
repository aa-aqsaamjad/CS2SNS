
import java.net.*;
import java.io.*;
import java.util.*;

public class CalculatorServer {


    // Methods for each of the commands. We detect the command in the msg.
    // And get the result.
    public static int addString(String input) {
        List<String> numberList = Arrays.asList(input.split(";"));
        int addValue = 0;
        for(int i = 0; i < numberList.size(); i++) {
          addValue += Integer.parseInt(numberList.get(i));
        }
        return addValue;
    }

    public static int multiplyString(String input){
      List<String> numberList = Arrays.asList(input.split(";"));
      int mulValue = 1;
      for(int i = 0; i < numberList.size(); i++) {
        mulValue *= Integer.parseInt(numberList.get(i));
      }
      return mulValue;
    }

    public static void main(String[] args) throws IOException {

        int portNumber = 43221;

        try (
            ServerSocket serverSocket =
                new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while (( inputLine = in.readLine()) != null) {
                // Our text protocol is cmd!number;number e.g. ADD!5;6
                // We read the command first
                List<String> commands = Arrays.asList(inputLine.split("!"));
                String command = commands.get(0);
                switch (command) {
                  // Call the correct command based on message
                  case "ADD" : int res = addString(commands.get(1));
                               out.println("" + res);
                               break;
                  case "MUL" : int res2 = multiplyString(commands.get(1));
                               out.println("" + res2);
                               break;
                  default: out.println("Invalid Command");

                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
