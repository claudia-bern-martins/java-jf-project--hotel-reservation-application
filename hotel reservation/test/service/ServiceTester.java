package service;

public class ServiceTester {

    public static void main (String[] args) {
        testCustomerService();
    }

    private static void testCustomerService() {
        CustomerService cs = CustomerService.getInstance();
        System.out.println(cs.getAllCustomers());

        cs.addCustomer("jane@smith.org", "Jane", "Smith");
        System.out.println(cs.getCustomer("jane@smith.org"));

        cs.addCustomer("jdoe@something.com", "John", "Doe");
        System.out.println(cs.getAllCustomers());
    }
}
