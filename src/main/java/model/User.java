package model;

public class User {
    private int    idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int    idRole;
    private String roleName; // joined field, not persisted

    public User() {}

    public User(int idUser, String firstName, String lastName,
                String email, String password, int idRole) {
        this.idUser     = idUser;
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.email      = email;
        this.password   = password;
        this.idRole     = idRole;
    }

    // Getters / setters
    public int    getIdUser()               { return idUser; }
    public void   setIdUser(int idUser)     { this.idUser = idUser; }

    public String getFirstName()                    { return firstName; }
    public void   setFirstName(String firstName)    { this.firstName = firstName; }

    public String getLastName()                     { return lastName; }
    public void   setLastName(String lastName)      { this.lastName = lastName; }

    public String getEmail()                { return email; }
    public void   setEmail(String email)    { this.email = email; }

    public String getPassword()                 { return password; }
    public void   setPassword(String password)  { this.password = password; }

    public int  getIdRole()             { return idRole; }
    public void setIdRole(int idRole)   { this.idRole = idRole; }

    public String getRoleName()                     { return roleName; }
    public void   setRoleName(String roleName)      { this.roleName = roleName; }
}