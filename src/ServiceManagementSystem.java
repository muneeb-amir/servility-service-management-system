
import java.util.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Service class
class Service 
{   private String Sname;
    private String providerName;
    private String expertise;
    private String description;
    private double price;
    private String status;
    public void setStatus(String s)
    {
        status=s;
    } 
    String getstatus()
    {
        return status;
    }
    public void setSname(String Sname)
    {
        this.Sname=Sname;
    }
    public String getsname() 
    {
        return Sname;
    }   
    public Service(String providerName, String expertise, String description, double price,String status) 
    {
        this.providerName = providerName;
        this.expertise = expertise;
        this.description = description;
        this.price = price;
        this.status=status;
    }

    public String getExpertise() {
        return expertise;
    }

    public double getPrice() {
        return price;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getDetails() {
        return   description;
    }
    public String getDescription() {
     return description;
        
    }
}

// ServiceRequest class
class ServiceRequest {
    private String seekerName;
    private String expertise;
    private String description;
    private double budget;
    private String status;
     // ('pending', 'accepted', 'completed')
    private String pname;
    public String getpname()
    {
        return pname;
    }
   public void setpname(String pname)
    {
        this.pname=pname;
    }
     public ServiceRequest(String seekerName, String expertise, String description, double budget,String status)
    {
        this.seekerName = seekerName;
        this.expertise = expertise;
        this.description = description;
        this.budget = budget;
        this.status=status;
    }
   public void setStatus(String s)
    {
        this.status=s;
    }
    public String getStatus()
    {
        return status;    
    }
    public String getExpertise() 
    {
        return expertise;
    }

    public double getBudget() {
        return budget;
    }

    public String getSeekerName() {
        return seekerName;
    }

    public String getDetails() {
        return  description;
    }
}

// Abstract User class
abstract class User {
        protected String name;
        protected String email;
        protected String password;
    
        public User(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }
    
        // Getter and Setter for 'name'
        public String getName() {
            return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for 'email'
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for 'password'
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Abstract method 'login' to be implemented by subclasses
    public abstract void login();

    // Check credentials method to validate email and password
    public boolean checkCredentials(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}


class ServiceProvider extends User {
    private String expertise;
    private double balance;

    public void setBalance(double amount) {
        balance = amount;
    }

    public ServiceProvider(String name, String email, String password, String expertise) {
        super(name, email, password);
        this.expertise = expertise;
        this.balance = 0.0;
    }

    public String getExpertise() {
        return expertise;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    @Override
    public void login() {
        System.out.println("ServiceProvider logged in: " + name);
    }

    public void postService(List<Service> services, Scanner scanner) {
        System.out.print("Enter service description: ");
        String description = scanner.nextLine();
        System.out.print("Enter price for the service: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        

        Service service = new Service(name, expertise, description, price, "pending");
        services.add(service);
        System.out.println("Service posted successfully!");
    }

    public void viewAndAcceptRequests(List<ServiceRequest> requests, List<ServiceSeeker> seekers, Scanner scanner) {
        System.out.println("\nAvailable Service Requests:");
        List<ServiceRequest> filteredRequests = new ArrayList<>();
        
        for (ServiceRequest request : requests) {
            if (request.getExpertise().equalsIgnoreCase(expertise) && request.getStatus().equals("pending")) {
                filteredRequests.add(request);
            }
        }

        if (filteredRequests.isEmpty()) 
        {
            System.out.println("No matching requests available.");
            return;
        }

        for (int i = 0; i < filteredRequests.size(); i++) {
            if(filteredRequests.get(i).getStatus().equals("pending"))
            System.out.println((i + 1) + ". " + filteredRequests.get(i).getDetails());
        }
        System.out.println((filteredRequests.size() + 1) + ". None");

        System.out.print("\nSelect a request to accept (enter number): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= filteredRequests.size()) {

            ServiceRequest acceptedRequest = filteredRequests.get(choice - 1);
            ServiceSeeker seeker = findSeekerByName(seekers, acceptedRequest.getSeekerName());

            if (seeker != null && acceptedRequest.getStatus().equals("pending") )  //&&// seeker.getBalance() >= acceptedRequest.getBudget()) 
            {
                seeker.deductBalance(acceptedRequest.getBudget());
                this.addBalance(acceptedRequest.getBudget());
                
                acceptedRequest.setStatus("accepted");
                acceptedRequest.setpname(name);
                System.out.println("Request accepted and status changed to 'accepted': " +        acceptedRequest.getDetails() + " by Provider: " + name);
            } else {
                System.out.println("Seeker has insufficient balance.");
            }
        } else {
            System.out.println("No request selected.");
        }
    }

    private ServiceSeeker findSeekerByName(List<ServiceSeeker> seekers, String name) {
        for (ServiceSeeker seeker : seekers) {
            if (seeker.getName().equals(name)) {
                return seeker;
            }
        }
        return null;
    }
}

class ServiceSeeker extends User {
    private double balance;

    public ServiceSeeker(String name, String email, String password, double balance) {
        super(name, email, password);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }

    @Override
    public void login() {
        System.out.println("ServiceSeeker logged in: " + name);
    }

    public void postServiceRequest(List<ServiceRequest> requests, Scanner scanner) 
    {
        System.out.print("Enter required expertise (Driver, Chef, Househelper, Watchman): ");
        String expertise = scanner.nextLine();
        System.out.print("Enter a brief description: ");
        String description = scanner.nextLine();
        System.out.print("Enter your budget: ");
        double budget = scanner.nextDouble();
        scanner.nextLine();

        ServiceRequest request = new ServiceRequest(name, expertise, description, budget, "pending");
        requests.add(request);
        System.out.println("Service request posted successfully!");
    }

    public void viewAndSelectServices(List<Service> services, Scanner scanner) {
        System.out.println("\nAvailable Services:");

        if (services.isEmpty()) {
            System.out.println("No services available.");
            return;
        }

        for (int i = 0; i < services.size(); i++) {
          if(services.get(i).getstatus().equals("pending"))
            System.out.println((i + 1) + ". " + services.get(i).getDetails());
        }
        System.out.println((services.size() + 1) + ". None");

        System.out.print("\nSelect a service to hire (enter number): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= services.size()) {
            Service selectedService = services.get(choice - 1);

            if (selectedService.getPrice() <= balance && selectedService.getstatus().equals("pending") )//&& "available".equalsIgnoreCase(selectedService.getstatus())) 
            {
                balance -= selectedService.getPrice();
                selectedService.setSname(name);
                selectedService.setStatus("completed");
                System.out.println("Service hired successfully: " + selectedService.getDetails());
            } else {
                System.out.println("Insufficient balance or service unavailable.");
            }
        }
         else {
            System.out.println("No service selected.");
        }
    }
}


class DatabaseConnection 
{
    private static final String URL = "jdbc:mysql://localhost:3306/Servicemanagementsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "muneeb1234";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            // Check if the connection is null or closed and reinitialize if needed
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Database connection Failed!");
            e.printStackTrace();
        }
        return connection;
    }
}

// Main System Class
public class ServiceManagementSystem 
{
    private static List<ServiceSeeker> seekers = new ArrayList<>();
    private static List<ServiceProvider> providers = new ArrayList<>();
    private static List<Service> services = new ArrayList<>();
    private static List<ServiceRequest> requests = new ArrayList<>();

     // Getter and Setter for seekers
     public static List<ServiceSeeker> getSeekers() {
        return seekers;
    }

    public static void setSeekers(List<ServiceSeeker> seekers) {
        ServiceManagementSystem.seekers = seekers;
    }

    // Getter and Setter for providers
    public static List<ServiceProvider> getProviders() {
        return providers;
    }

    public static void setProviders(List<ServiceProvider> providers) {
        ServiceManagementSystem.providers = providers;
    }

    // Getter and Setter for services
    public static List<Service> getServices() {
        return services;
    }

    public static void setServices(List<Service> services) {
        ServiceManagementSystem.services = services;
    }

    // Getter and Setter for requests
    public static List<ServiceRequest> getRequests() {
        return requests;
    }

    public static void setRequests(List<ServiceRequest> requests) {
        ServiceManagementSystem.requests = requests;
    }

   
    public static void  manageprofileseeker(String username, String Name,String email,String pass)
    {
        for(int i=0;i<seekers.size();i++)
        {
            if(seekers.get(i).getName().equals(username))
            {
                seekers.get(i).setEmail(email);
                seekers.get(i).setName(Name);
                seekers.get(i).setPassword(pass);
                return;
            }
        }
    }    

       public static void main(String[] args)
    {
        loadDataFromDatabase();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nService Management System");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                signUp(scanner);
            } else if (choice == 2) {
                login(scanner);
            } else if (choice == 3) {
                break;
            }
        }
        scanner.close();
    }
    ////////////////////////////////////////////////////////////////
    /// 
    /// 
    /// 

    public static void Updated(String username, String name, String email, String pass) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Escape `password` using backticks for MySQL
            String providersQuery = "UPDATE ServiceSeekers SET name = ?, email = ?, `password` = ? WHERE name = ?";
    
            try (PreparedStatement stmt = connection.prepareStatement(providersQuery)) {
                // Set values for placeholders
                stmt.setString(1, name);     // New name
                stmt.setString(2, email);    // New email
                stmt.setString(3, pass);     // New password
                stmt.setString(4, username); // Old name to identify the record
    
                // Execute the update query
                int rowsUpdated = stmt.executeUpdate();
    
                if (rowsUpdated > 0) {
                    System.out.println("Service seeker updated successfully.");
                } else {
                    System.err.println("No service seeker found with the given name.");
                }
            } catch (SQLException e) {
                System.err.println("Error saving ServiceSeeker: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
      public static void saveDataToDatabase() 
    {
    try (Connection connection = DatabaseConnection.getConnection()) {
        if (connection == null) {
            System.out.println("Failed to establish a connection!");
            return;
        }

        System.out.println("Saving data to the database...");

        // Save Service Seekers
        String seekersQuery = "REPLACE INTO ServiceSeekers (name, email, password, balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(seekersQuery)) 
        {
            for (ServiceSeeker seeker : seekers) {
                stmt.setString(1, seeker.getName());
                stmt.setString(2, seeker.getEmail());
                stmt.setString(3, seeker.getPassword());
                stmt.setDouble(4, seeker.getBalance());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error saving Service Seekers: " + e.getMessage());
        }

        // Save Service Providers
        String providersQuery = "REPLACE INTO ServiceProviders (name, email, password, expertise, balance) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(providersQuery)) 
        {
            for (ServiceProvider provider : providers) {
                stmt.setString(1, provider.getName());
                stmt.setString(2, provider.getEmail());
                stmt.setString(3, provider.getPassword());
                stmt.setString(4, provider.getExpertise());
                stmt.setDouble(5, provider.getBalance());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error saving Service Providers: " + e.getMessage());
        }

        // Save Services
        String requestsQuery = "REPLACE INTO ServiceRequests (expertise, description, budget, status, seeker_id, providername) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(requestsQuery)) {
            for (ServiceRequest request : requests) {
                stmt.setString(1, request.getExpertise());
                stmt.setString(2, request.getDetails());
                stmt.setDouble(3, request.getBudget());
                stmt.setString(4, request.getStatus());
                stmt.setInt(5, getSeekerIdByName(connection, request.getSeekerName()));
                stmt.setString(6, request.getpname()); // Set the provider name if available
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error saving Service Requests: " + e.getMessage());
        }
        
        System.out.println("All service requests have been saved to the database successfully.");
        
        String servicesQuery = "REPLACE INTO Services (provider_id, description, price, sname, expertise, status) " +
        "VALUES (?, ?, ?, ?, ?, ?)";
try (PreparedStatement stmt = connection.prepareStatement(servicesQuery)) {
for (Service service : services) {
// Retrieve the provider_id based on the provider name
System.out.println(service.getProviderName());
int providerId = getProviderIdByName(connection, service.getProviderName());
System.out.println("providername: "+service.getProviderName() );
if (providerId == -1) {
System.err.println("Provider not found for name: " + service.getProviderName());
continue; // Skip this service and move to the next
}

// Set the values for the prepared statement
stmt.setInt(1, providerId); // Set provider_id dynamically
stmt.setString(2, service.getDetails()); // Set description
stmt.setDouble(3, service.getPrice()); // Set price
stmt.setString(4, service.getsname()); // Set sname (service name)
stmt.setString(5, service.getExpertise()); // Set expertise

// Set status - default to 'pending' if not provided
String status = service.getstatus() != null ? service.getstatus() : "pending";
stmt.setString(6, status); // Set the status dynamically
// Execute the query
stmt.executeUpdate();
}
} catch (SQLException e) {
System.err.println("Error saving Services: " + e.getMessage());
}


        

        System.out.println("All data has been saved to the database successfully.");
    } catch (SQLException e) {
        System.err.println("Error saving data to the database: " + e.getMessage());
        e.printStackTrace();
    }
}
private static int getProviderIdByName(Connection connection, String providerName) 
throws SQLException 
{
    String query = "SELECT provider_id FROM ServiceProviders WHERE name = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, providerName);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("provider_id");
            }
        }
    }

    // If no matching provider is found, return -1 and log the issue
    System.err.println("Provider with name '" + providerName + "' not found in ServiceProviders table.");
    return -1;
}

private static int getSeekerIdByName(Connection connection, String seekerName) throws SQLException {
    String query = "SELECT seeker_id FROM ServiceSeekers WHERE name = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, seekerName);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("seeker_id");
            }
        }
    }
    return -1; // Indicate that the seeker was not found
}

    
    
    ////////////////////////////////////////////////////////////////
    public static void loadDataFromDatabase() 
    {
     seekers.clear();
     providers.clear();
     requests.clear();
     services.clear();   
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                System.out.println("Failed to establish a connection!");
                return;
            }
    
            System.out.println("Connection established successfully.");
    
            // Load Service Seekers
            String seekersQuery = "SELECT name, email, password, balance FROM ServiceSeekers";
            try (PreparedStatement stmt = connection.prepareStatement(seekersQuery);
                 ResultSet rs = stmt.executeQuery()) {
                System.out.println("Loading Service Seekers...");
                while (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    double balance = rs.getDouble("balance");
    
                    ServiceSeeker seeker = new ServiceSeeker(name, email, password, balance);
                    seekers.add(seeker);
                }
            } catch (SQLException e) {
                System.err.println("Error loading seekers: " + e.getMessage());
            }
    
            // Load Service Providers
            String providersQuery = "SELECT name, email, password, expertise, balance FROM ServiceProviders";
            try (PreparedStatement stmt = connection.prepareStatement(providersQuery);
                 ResultSet rs = stmt.executeQuery()) {
                System.out.println("Loading Service Providers...");
                while (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String expertise = rs.getString("expertise");
                    double balance1 = rs.getDouble("balance");
    
                    ServiceProvider provider = new ServiceProvider(name, email, password, expertise);
                    provider.setBalance(balance1);
                    providers.add(provider);
                }
            } catch (SQLException e) {
                System.err.println("Error loading providers: " + e.getMessage());
            }
            // Load Services
            String servicesQuery = "SELECT s.description, s.expertise, s.price, s.sname, s.status, s.provider_id, sp.name AS provider_name " +
            "FROM Services s " +
            "JOIN ServiceProviders sp ON s.provider_id = sp.provider_id";

try (PreparedStatement stmt = connection.prepareStatement(servicesQuery);
ResultSet rs = stmt.executeQuery()) {
System.out.println("Loading Services...");
while (rs.next()) {
    String providerName = rs.getString("provider_name");  // Use the correct alias
    String description = rs.getString("description");
String status = rs.getString("status");  // Status
double price = rs.getDouble("price"); // Price
String exp = rs.getString("expertise"); // Expertise
String sname = rs.getString("sname"); // Service Name (sname)

// Create a Service object and add it to the list
Service service = new Service(providerName, exp, description, price, status);
service.setSname(sname);
services.add(service);
}
} catch (SQLException e) {
System.err.println("Error loading services: " + e.getMessage());
}

    //  private String seekerName; private String expertise; private String description; private double budget; private String status;
            // Load Service Requests
            String requestsQuery = "SELECT sr.expertise, sr.description, sr.budget, sr.status,sr.providername, ss.name AS seeker_name " +
                                   "FROM ServiceRequests sr " +
                                   "JOIN ServiceSeekers ss ON sr.seeker_id = ss.seeker_id";
            try (PreparedStatement stmt = connection.prepareStatement(requestsQuery);
                 ResultSet rs = stmt.executeQuery()) {
                System.out.println("Loading Service Requests...");
                while (rs.next()) {
                    String seekerName = rs.getString("seeker_name");
                    String expertise = rs.getString("expertise");
                    String description = rs.getString("description");
                    double budget = rs.getDouble("budget");
                    String status = rs.getString("status");
                    String pname=rs.getString("providername") ;
                    ServiceRequest request = new ServiceRequest(seekerName, expertise, description, budget, status);
                    request.setpname(pname);
                
                    requests.add(request);
                }
            } catch (SQLException e) {
                System.err.println("Error loading requests: " + e.getMessage());
            }
    
            System.out.println("Data loaded successfully from the database.");
        } catch (SQLException e) {
            System.out.println("Error loading data from the database.");
            e.printStackTrace();
        }
    }
      
    private static void signUp(Scanner scanner) {
        System.out.println("\nSign Up Menu");
        System.out.println("1. Sign up as ServiceSeeker");
        System.out.println("2. Sign up as ServiceProvider");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            signUpSeeker(scanner);
        } else if (choice == 2) {
            signUpProvider(scanner);
        }
    }

    private static void login(Scanner scanner) 
    {
        System.out.println("\nLogin Menu");
        System.out.println("1. ServiceSeeker Login");
        System.out.println("2. ServiceProvider Login");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            ServiceSeeker seeker = authenticateSeeker(email, password);
            if (seeker != null) {
                seeker.login();
                serviceSeekerMenu(seeker, scanner);
            } else {
                System.out.println("Invalid credentials.");
            }
        }
        else if (choice == 2) 
        {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            ServiceProvider provider = authenticateProvider(email, password);
            if (provider != null) {
                provider.login();
                serviceProviderMenu(provider, scanner);
            } else {
                System.out.println("Invalid credentials.");
            }
        }
    }

    private static void serviceSeekerMenu(ServiceSeeker seeker, Scanner scanner) {
        while (true) {
            System.out.println("\nServiceSeeker Menu");
            System.out.println("1. Post a Service Request");
            System.out.println("2. View and Select Services");
            System.out.println("3. Check Balance");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                seeker.postServiceRequest(requests, scanner);
            } else if (choice == 2) {
                seeker.viewAndSelectServices(services, scanner);
            } else if (choice == 3) {
                System.out.println("Current Balance: $" + seeker.getBalance());
            } else if (choice == 4) {
                saveDataToDatabase(); break;
            }
        }
    }

    private static void serviceProviderMenu(ServiceProvider provider, Scanner scanner) {
        while (true) {
            System.out.println("\nServiceProvider Menu");
            System.out.println("1. Post a Service");
            System.out.println("2. View and Accept Requests");
            System.out.println("3. View Earnings");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                provider.postService(services, scanner);
            } else if (choice == 2) {
                provider.viewAndAcceptRequests(requests, seekers, scanner);
            } else if (choice == 3) {
                System.out.println("Current Earnings: $" + provider.getBalance());
            } else if (choice == 4) {
                saveDataToDatabase();
                break;
            }
        }
    }
  
  
    private static void signUpSeeker(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your starting balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();
    
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                System.out.println("Failed to establish a connection!");
                return;
            }
    
            // Insert into ServiceSeekers table
            String insertSeekerQuery = "INSERT INTO ServiceSeekers (name, email, password, balance) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertSeekerQuery)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);
                stmt.setDouble(4, balance);
    
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("ServiceSeeker account created successfully in the database!");
                }
            } catch (SQLException e) {
                System.err.println("Error inserting ServiceSeeker into the database: " + e.getMessage());
            }
    
            // Add to the in-memory list
            seekers.add(new ServiceSeeker(name, email, password, balance));
        } catch (SQLException e) 
        {
            System.out.println("Error connecting to the database.");
            e.printStackTrace();
        }
    }
    

    private static void signUpProvider(Scanner scanner) 
    {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your area of expertise: ");
        String expertise = scanner.nextLine();
    
        // Add to local list
        providers.add(new ServiceProvider(name, email, password, expertise));
        System.out.println("ServiceProvider account created successfully!");
    
        // Insert into the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                System.out.println("Failed to connect to the database.");
                return;
            }
    
            // Insert into the ServiceProviders table
            String providerInsertQuery = "INSERT INTO ServiceProviders (name, email, password, expertise, balance) " +
                                         "VALUES (?, ?, ?, ?, 0.0)";
            try (PreparedStatement stmt = connection.prepareStatement(providerInsertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);
                stmt.setString(4, expertise);
                stmt.executeUpdate();
    
                // Retrieve the generated provider ID
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int providerId = rs.getInt(1);
                        System.out.println("ServiceProvider added to the database successfully! Provider ID: " + providerId);
                    } else {
                        System.out.println("Failed to retrieve the generated provider ID.");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error inserting ServiceProvider into the database: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database.");
            e.printStackTrace();
        }
    }
    
    
     static ServiceSeeker authenticateSeeker(String email, String password) 
    {
        for (ServiceSeeker seeker : seekers) 
        {
            if (seeker.checkCredentials(email, password))
            {
                return seeker;
            }
        }
        return null;
    }

     static ServiceProvider authenticateProvider(String email, String password) {
        for (ServiceProvider provider : providers) {
            if (provider.checkCredentials(email, password)) {
                return provider;
            }
        }
        return null;
    }
}