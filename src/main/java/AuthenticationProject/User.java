package AuthenticationProject;

public class User {
    private String name;
    private String id;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id= String.valueOf(java.util.UUID.randomUUID());
    }
}
