package Utils.castin;

public class UpCasting {
    public static void main(String args[]) {

        User instanceOfUser1 = (User) new RegularUser();
        User instanceOfUser2 = (User) new RegularUser();
        instanceOfUser1.displayInformation();
        instanceOfUser2.displayInformation();
    }
}
