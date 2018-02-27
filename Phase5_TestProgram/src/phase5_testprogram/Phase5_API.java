/////////////////////////////////////////////////////////////////////
//
// API for Phase 5
//
/////////////////////////////////////////////////////////////////////
package phase5_testprogram;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;




public class Phase5_API
{
	private Connection connect = null;

		
		// The Constructor for the database connection
	   public Phase5_API() 
		{
        
        // Select the database driver
        // If an error occurs, end the program execution
        try { Class.forName("com.mysql.jdbc.Driver");} 
		catch(Exception e) 
		{
            System.out.println("Error selecting database driver: "+ e.getMessage());
            System.out.println("Ending program execution");
            System.exit(-1);
        }
       
        // Connect to the database.
        try
		{

			// Using the local database on my computer
			// For testing purposes
            connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fieldtrip", "root","");    
        }
		catch(Exception e)
		{
            System.out.println("Error connecting to database: "+ e.getMessage());
            System.out.println("Ending program execution");
            System.exit(-1);
        }
        
    }
	
		
			// Query 0
			// NOT part of Phase 5
			// Strictly for testing purposes
			// Returns All Students
			public ArrayList<Student> allStudent()
			{
			ArrayList<Student> al = new ArrayList<Student>();
			
			try {
				// Create a statement object
				Statement s = connect.createStatement();
				
				// Execute the query
				ResultSet rs = s.executeQuery("SELECT  studentid, fname, lname, grade "+ "FROM Student");
				
				// Process the result set
				while(rs.next())
					{
					Student student = new Student();
					student.studentid = rs.getInt("studentid");
					student.fname = rs.getString("fname");
					student.lname = rs.getString("lname");
					student.grade = rs.getInt("grade");
					
					al.add(student);
				}
			
			}catch(Exception e) {
				System.out.println("Error in allStudent query:" + e.getMessage());
				System.out.println("Ending program execution");
				System.exit(-1);
			}

			return al;
		}
	
		// Query 1
		// Description:
		// This query will let the user know which of the field trips
		// planned will take place after school. This query is important because
		// it keeps track of trips that are done at times when school is not
		// in session.
		// Note: Using a string for time to keep the XX:XX:XX format
		// Note2: Using a Variable in place of static examples
		public ArrayList<Trip> Query1(String time)
			{
				ArrayList<Trip> al = new ArrayList<Trip>();
				
				try {
					// Create a statement object
					Statement s = connect.createStatement();
					
					// Execute the query
					ResultSet rs = s.executeQuery("SELECT tripdate, departuretime, returntime "+ "FROM Trip "+"WHERE  departuretime >= "+time+";");
					
					// Process the result set
					while(rs.next())
                                        {
						Trip trip = new Trip();
						trip.tripdate = rs.getString("tripdate");
						trip.departuretime = rs.getString("departuretime");
						trip.returntime = rs.getString("returntime");
						
						al.add(trip);
					}
				
				}catch(Exception e) {
					System.out.println("Error in Query1:" + e.getMessage());
					System.out.println("Ending program execution");
					System.exit(-1);
				}

				return al;
		}

		// Query 2
		// Description:
		// This query will show the field trips that is greater than a certain
		// cost. It would essentially show the user which of the field trips might
		// might be too expensive for the school's budget. It shows the
		// field trip's ID, address, description and cost of the place.
			public ArrayList<Trip> Query2(int cost)
				{
					ArrayList<Trip> al = new ArrayList<Trip>();
					
					try
					{
						// Create a statement object
						Statement s = connect.createStatement();
						
						// Execute the query
                                            ResultSet rs = s.executeQuery("SELECT T.FieldtripID, D.address, D.description ,D.cost "
                                                    +"FROM Trip T, Destination D, Proceeds P "
                                                    +"WHERE T.FieldtripID = P.FieldtripID "
                                                    +"AND P.destinationid = D.destinationid "
                                                    +"AND D.cost >="+cost+";");

						// Process the result set
						while(rs.next())
							{
							Trip trip = new Trip();
							trip.FieldtripID = rs.getInt("T.FieldtripID");
							trip.address = rs.getString("D.address");
							trip.description = rs.getString("D.description");
							
							al.add(trip);
						}
					
					}
					catch(Exception e)
					{
						System.out.println("Error in Query2:" + e.getMessage());
						System.out.println("Ending program execution");
						System.exit(-1);
					}

					return al;
				}
			
			
		// Query 3
		// Description:
		// This query will let the user know which of the field trips
		// are during school hours (During classes). It will show all of the
		// trip's information. This query is important because it lets the 
		// staff know that there is a field trip going on during class hours
		// which could be an explanation why there are students missing in class.		
public ArrayList<Trip> Query3(String time_leave, String time_return)
        {
                ArrayList<Trip> al = new ArrayList<Trip>();

                try {
                        // Create a statement object
                        Statement s = connect.createStatement();

                        // Execute the query
                        ResultSet rs = s.executeQuery("SELECT tripdate, departuretime, returntime "
                                + "FROM Trip "
                                +"WHERE departuretime AND returntime "
                                +"BETWEEN "+time_leave+" AND "+time_return+";");

                        // Process the result set
                        while(rs.next())
                                {
                                Trip trip = new Trip();
                                trip.tripdate = rs.getString("tripdate");
                                trip.departuretime = rs.getString("departuretime");
                                trip.returntime = rs.getString("returntime");

                                al.add(trip);
                        }

                }catch(Exception e) {
                        System.out.println("Error in Query3:" + e.getMessage());
                        System.out.println("Ending program execution");
                        System.exit(-1);
                }

                return al;
}
	
	
		// Query 4
		// Description:
		// This query will show the total cost (SUM) of all the trips that 
		// are planned alone. This query is meaningful to have because
		// it keeps the record of a general idea on how much the school will be
		// spending on all the trips planned.
public int Query4()
{
    int cost=0;

    try 
    {
        // Create a statement object
        Statement s = connect.createStatement();

        // Execute the query
        ResultSet rs = s.executeQuery("SELECT SUM(D.cost) AS total_cost "
                +"FROM Trip T, Destination D, Proceeds P "
                +"WHERE T.FieldtripID = P.FieldtripID "
                +"AND P.destinationid = D.destinationid;");

        //The Result will be a single integer
            while(rs.next())
            {    
                 cost = rs.getInt("total_cost");             
            }				


    }
    catch(Exception e) {
            System.out.println("Error in Query4:" + e.getMessage());
            System.out.println("Ending program execution");
            System.exit(-1);
    }

    return cost;
}
		


// Query 5
// Description:
// This query will display the grades and the amount of those 
// students within those grades that are going to a field trip.
// This is fundamental because it keeps track of how many students from each
// grade will be going to a field trip.
public ArrayList<Student> Query5()
{
        ArrayList<Student> al = new ArrayList<Student>();

        try
        {
            // Create a statement object
            Statement s = connect.createStatement();

            // Execute the query
            ResultSet rs = s.executeQuery("SELECT S.grade, COUNT(S.grade) as numStudent "
                    +"FROM Student S "+"GROUP BY S.grade "
                    +"HAVING COUNT(S.grade) ");

            // Process the result set
            while(rs.next())
                    {
                        Student student = new Student();
                        student.grade = rs.getInt("S.grade");
                        student.num = rs.getInt("numStudent");
                        al.add(student);
                    }

        }
        catch(Exception e)
        {
                System.out.println("Error in Query3:" + e.getMessage());
                System.out.println("Ending program execution");
                System.exit(-1);
        }

        return al;
}			
		
		// Query 6
		// Description:
		// This query will print out the students that will attend Field trip X,
		// but won't attend Field trip Y. This is important to have since it gives the
		// opportunity to know which students attended which field trips, but didn't attend
		// another field trip. It prints out student id, first name, last name and their grade level
public ArrayList<Student> Query6(int a, int b)
        {
                ArrayList<Student> al = new ArrayList<Student>();

                try {
                        // Create a statement object
                        Statement s = connect.createStatement();
                        String query =("SELECT DISTINCT S1.studentid, S1.fname, S1.lname, S1.grade "
                        +"FROM Student S1, Attends A1, Trip T1 "
                        +"WHERE S1.studentid = A1.studentid AND A1.FieldtripID = T1.FieldtripID "
                        +"AND T1.FieldtripID = 4  AND S1.studentid NOT IN"
                        +"(SELECT A2.studentid "
                        +"FROM Attends A2, Trip T2 "
                        +"WHERE A2.FieldtripID = T2.FieldtripID " 
                        +"AND T2.FieldtripID = 1); ");

                        // Execute the query
                        ResultSet rs = s.executeQuery(query);

                        //Issue using: 


                        // Process the result set
                        while(rs.next())
                                {
                                Student student = new Student();
                                student.studentid = rs.getInt("S1.studentid");
                                student.fname = rs.getString("S1.fname");
                                student.lname = rs.getString("S1.lname");
                                student.grade = rs.getInt("S1.grade");

                                al.add(student);
                        }

                }catch(Exception e) {
                        System.out.println("Error in Query6:" + e.getMessage());
                        System.out.println("Ending program execution");
                        System.exit(-1);
                }

                return al;
}			
	

}