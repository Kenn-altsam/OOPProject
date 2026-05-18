package project.models;
import java.util.*;
import project.enums.UserRole;
import project.storage.AppData;

public class AdministerSystem extends CreateEmployee {
    private static final long serialVersionUID = 1L;
    public AdministerSystem(String id, String username, String password, String name, String email, String department) {
        super(id, username, password, name, email, department, UserRole.ADMIN);
    }
    public void addUser(AppData data, CreateUser user){ data.users.put(user.getId(), user); }
    public boolean removeUser(AppData data, String id){ return data.users.remove(id) != null; }
    public boolean updateUser(AppData data, String id, String newName, String newEmail){
        CreateUser u = data.users.get(id);
        if(u == null) return false;
        u.setName(newName); u.setEmail(newEmail); return true;
    }
    public List<AccessLog> viewLogs(AppData data){ return data.logs; }
}
