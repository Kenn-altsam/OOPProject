package project.models;
import java.io.Serializable;
import java.util.Objects;
import project.enums.UserRole;
import project.interfaces.Authenticatable;

public abstract class CreateUser implements Authenticatable, Comparable<CreateUser>, Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    protected UserRole role;
    protected boolean active = true;

    public CreateUser(String id, String username, String password, String name, String email, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public boolean login(String password) { return active && Objects.equals(this.password, password); }
    public void logout() { }
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
    public boolean isActive() { return active; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setActive(boolean active) { this.active = active; }

    @Override public int compareTo(CreateUser other) { return this.name.compareToIgnoreCase(other.name); }
    @Override public boolean equals(Object o) { return o instanceof CreateUser && Objects.equals(id, ((CreateUser)o).id); }
    @Override public int hashCode() { return Objects.hash(id); }
    @Override public String toString() { return role + "{" + id + ", " + name + ", username=" + username + ", email=" + email + "}"; }
}
