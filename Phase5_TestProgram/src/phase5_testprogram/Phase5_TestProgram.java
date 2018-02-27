package phase5_testprogram;
import java.util.Scanner;
import java.util.ArrayList;


public class Phase5_TestProgram
{
    public static Scanner scanner = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
  
     
    public static void main(String[] args)
    {
        
        int x;
        // Initialization Message
        System.out.println("Field Trip Testing Program Initialize...");
		
        // Create a Phase5_API class object
        Phase5_API FT_API = new Phase5_API();
        System.out.println("created DB api object");
     
        // User input for menu selection
        // Initialize to 1 so that while loop will execute.
        int menuChoice = 1;
        while(menuChoice != 0)  
		{
            menuChoice = showMenu();
            if(menuChoice == 1)
                runQuery1(FT_API);
            else if(menuChoice == 2)
                runQuery2(FT_API);
			else if(menuChoice == 3)
                runQuery3(FT_API);
			else if(menuChoice == 4)
                runQuery4(FT_API);
			else if(menuChoice == 5)
                runQuery5(FT_API);
			else if(menuChoice == 6)
                runQuery6(FT_API);
            
            
        }
    }
    public static int showMenu()
	 {
        int choice = -1;
        
        // Print the menu
        System.out.println("\n*----------------------------------------------------------------------------------------------*");
        System.out.println("Field Trip DB API Phase 5 Test Menu:");
        System.out.println("     1- To Test Query 1: Check Trips are after school hours");
        System.out.println("     2- To Test Query 2: Check which Trips exceed a certain cost");
        System.out.println("     3- To Test Query 3: Check the Date for Trips with an example departure time and return time of '7:30:00' and '16:30:00'.");
        System.out.println("     4- To Test Query 4: Check the total sum of ALL trips currently registered");
        System.out.println("     5- To Test Query 5: Check the number of Students for each grade");
        System.out.println("     6- To Test Query 6: Check which students haven't been in a certain field trip but not in another field trip. As an example the 4th field trip but not the 1st");
        System.out.println("     0- To quit");
        System.out.print("\nEnter your choice: ");
        
        // get user response
        choice = scanner.nextInt();
        
        // Print a blank line for formatting
        
        System.out.println();
        return choice;
    }
     public static void runQuery1(Phase5_API ft)
     {
        String time;
        //Time school ends in our example
        time = "'16:30:00'";

       // Call API function to for Query 1
       ArrayList<Trip> trip = ft.Query1(time);
        
        // Print information
        for(int i = 0; i < trip.size(); i++)
            {
                    System.out.println("tripdate = " + trip.get(i).tripdate);
                    System.out.println("departuretime = " + trip.get(i).departuretime);
                    System.out.println("returntime = " + trip.get(i).returntime);
                    System.out.println(" ");
            }
     }
     public static void runQuery2(Phase5_API ft)
     {
            // User Input Variable
            // Represents a Cost Limit
            int c;
        
        System.out.print("Enter a cost limit-> ");
        c = scanner.nextInt();
	 
        // Call API function to for Query 2
        ArrayList<Trip> trip = ft.Query2(c);
        
        // Print information
        for(int i = 0; i < trip.size(); i++)
			{
				System.out.println("FieldtripID = " + trip.get(i).FieldtripID);
				System.out.println("address = " + trip.get(i).address);
				System.out.println("description = " + trip.get(i).description);
				System.out.println(" ");
			}
     }
public static void runQuery3(Phase5_API ft)
 {
         // User Input Variables
         // Represents Time Leaving
         // And Returning back to 
         // School.
         String time_l="'7:30:00'";
         String time_r="'16:30:00'";

        // Asking for the input
        System.out.println("Time Leaving? ");
        //time_l = scanner.nextLine();
        System.out.println("Time Returning? ");
        //time_r = scanner.nextLine();

// Call API function to for Query 3
ArrayList<Trip> trip = ft.Query3(time_l, time_r);

    // Print information
    // Returns the Date, Departure Time,
    // and Return Time.
        for(int i = 0; i < trip.size(); i++)
         {
                 System.out.println("tripdate : " + trip.get(i).tripdate);
                 System.out.println("departuretime : " + trip.get(i).departuretime);
                 System.out.println("returntime : " + trip.get(i).returntime);
                 System.out.println(" ");
         }
    }
 public static void runQuery4(Phase5_API ft)
{
   // Call API function to for Query 4


   // Print information

   System.out.println("Total Cost : " + ft.Query4());
   System.out.println(" ");
}
public static void runQuery5(Phase5_API ft)
{
// Call API function to for Query 5
ArrayList<Student> student = ft.Query5();

// Print information
// Should be Student's Grades
for(int i = 0; i < student.size(); i++)
        {
                System.out.println("Student grade : " +student.get(i).grade+" # of Students: "+student.get(i).num);
        }
}
public static void runQuery6(Phase5_API ft)
{
    // Call API function to for Query 6
    ArrayList<Student> student = ft.Query6(1,2);

    // Print information
    for(int i = 0; i < student.size(); i++)
        {
                System.out.println("studentid : " + student.get(i).studentid);
                System.out.println("fname : " + student.get(i).fname);
                System.out.println("lname : " + student.get(i).lname);
                System.out.println("grade : " + student.get(i).grade);
                System.out.println(" ");
        }
 }
     
     

}
