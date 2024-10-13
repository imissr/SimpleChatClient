package server.loginService;

public interface IFLogin {
    public boolean register(String username, String password);
    public boolean login(String username,String password);
}
