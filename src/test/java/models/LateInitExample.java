package models;

public class LateInitExample {
    private String value;
    private static String name;
    private static String city = "Moscow";
    private Integer peopleCount;
    private Integer manCount;
    private Integer womanCount;

     static {
         name = "threadqa";
     }

     public LateInitExample(){
         value = "some value";
         manCount = 10;
         womanCount = 10;
         peopleCount = manCount + womanCount;
     }

    public int getPeopleCount() {
        return manCount + womanCount;
    }
}
