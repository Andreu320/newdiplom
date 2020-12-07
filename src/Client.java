public class Client {
    String сl; //last
    String cf; //first
    String stD;
    String finD;
    
    public Client(String сl, String cf, String stD, String finD) {
        this.сl = сl;
        this.cf = cf;
        this.stD = stD;
        this.finD = finD;
    }

    public String getLastName() {
        return сl;
    }

    public void setLastName(String сl) {
        this.сl = сl;
    }

    public String getFirstName() {
        return cf;
    }

    public void setFirstName(String cf) {
        this.cf = cf;
    }

    public String getStartDate() {
        return stD;
    }

    public void setStartDate(String stD) {
        this.stD = stD;
    }

    public String getFinDate() {
        return finD;
    }

    public void setFinDate(String finD) {
        this.finD = finD;
    }
    
    
    
}