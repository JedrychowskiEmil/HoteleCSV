import com.sun.jdi.Value;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamProcessObject {
    public static <T> Map<?, Long> countByField(Collection<T> collection, Function<T, ?> function, Integer skip) {

        return collection.stream()
                .skip(skip)
                .collect(Collectors.groupingBy(function, Collectors.counting()));
    }

    public static <T> Map<?, Long> countByField(Collection<T> collection, Function<T, ?> function) {

        return countByField(collection, function, 0);
    }

    public static <T> Map<?, T> uniqueValuesInField(Collection<T> collection, Function<T, ?> function, Integer skip) {
        return collection.stream()
                .skip(skip)
                .collect(Collectors.toMap(function, p -> p, (key, sameNewKey) -> key));
    }

    public static <T> Map<?, T> uniqueValuesInField(Collection<T> collection, Function<T, ?> function) {
        return uniqueValuesInField(collection, function, 0);
    }

    public static <T> List<T> fillterByField(Collection<T> collection, Predicate<T> predicate, Integer skip) {
        return collection.stream()
                .skip(skip)
                .filter(predicate)
                .collect(Collectors.toList());
    }
    public static <T> List<T> fillterByField(Collection<T> collection, Predicate<T> predicate) {
        return fillterByField(collection, predicate, 0);
    }



    public static <T, U> List<T> sortByTwoFields(Collection<T> collection, Comparator<T> firstField, Comparator<T> secondField, Integer skip){
        return collection.stream()
                .skip(skip)
                .sorted(firstField.thenComparing(secondField))
                .collect(Collectors.toList());
    }

}
