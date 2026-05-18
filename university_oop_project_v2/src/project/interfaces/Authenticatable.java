package project.interfaces;
public interface Authenticatable {
    boolean login(String password);
    void logout();
}
