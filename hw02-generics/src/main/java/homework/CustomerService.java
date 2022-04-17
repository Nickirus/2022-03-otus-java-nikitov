package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {
    private final NavigableMap<Customer, String> customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = customers.firstEntry();
        Customer key = entry.getKey();
        return Map.entry(new Customer(key.getId(), key.getName(), key.getScores()), entry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = customers.higherEntry(customer);
        if (entry == null) {
            return null;
        }
        Customer key = entry.getKey();
        return Map.entry(new Customer(key.getId(), key.getName(), key.getScores()), entry.getValue());
    }

    public void add(Customer customer, String data) {
        if (!customers.containsKey(customer)) {
            customers.put(customer, data);
        }
    }
}
