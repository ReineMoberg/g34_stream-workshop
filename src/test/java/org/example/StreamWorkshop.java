package org.example;

import org.example.model.Person;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class StreamWorkshop {

    private List<Person> persons;
    private List<Integer> numbers;
    private List<String> words;

    @Before
    public void setup() {
        persons = new ArrayList<>();
        persons.add(new Person(1, "Mehrdad", "Javan", LocalDate.parse("1989-01-01")));
        persons.add(new Person(2, "Fredrik", "Odin", LocalDate.parse("1976-01-01")));
        persons.add(new Person(3, "Erik", "Svensson", LocalDate.parse("1986-01-01")));
        persons.add(new Person(4, "Max", "Petersson", LocalDate.parse("1988-01-01")));

        numbers = Arrays.asList(1, 1, 2, 8, 7, 4, 4, 9);// 0 + 1 = 1 -> 1+1 = 2 ->

        words = Arrays.asList("abc", "", "bcd", "", "defg", "jk");

    }

    @Test
    public void ex1() {
        Optional<Person> result = persons.stream().min((o1, o2) -> o2.getBirthDate().compareTo(o1.getBirthDate()));
        result.ifPresent(System.out::println);
        Person expected = new Person(1, "Mehrdad", "Javan", LocalDate.parse("1989-01-01"));
        Person actual = result.orElse(null);
        assertEquals(expected, actual);
    }

    @Test
    public void ex2() {
        Optional<String> fullNameResult = persons.stream()
                .max(Comparator.comparing(Person::getBirthDate))
                .map(person -> person.getFirstName() + " " + person.getLastName());
        String expected = "Mehrdad Javan";
        String actual = fullNameResult.orElse(null);
        assertEquals(expected, actual);
    }

    @Test
    public void ex3() {
        List<Integer> uniqueNumbers = numbers.stream()
                .distinct()
                .collect(Collectors.toList());
        assertEquals(6, uniqueNumbers.size());
    }

    @Test
    public void ex4() {
        Set<Integer> setNumbers = numbers.stream().collect(Collectors.toSet());
        assertEquals(6, setNumbers.size());
    }

    @Test
    public void ex5() {
        List<Person> filterPersons = persons.stream()
                .filter(p -> p.getFirstName().toLowerCase().startsWith("m"))
                .collect(Collectors.toList());
        assertEquals(2, filterPersons.size());
    }

    @Test
    public void ex6() {
        List<String> customResult = persons.stream()
                .filter(person -> person.getBirthDate().isAfter(LocalDate.parse("1987-01-01")))
                .map(person -> person.getFirstName() + " " + person.getBirthDate())
                .collect(Collectors.toList());
        assertEquals(2, customResult.size());
    }

    @Test
    public void ex7() {
        List<String> filterPersonByName = persons.stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase("mehrdad"))
                .map(person -> new StringBuilder(person.getFirstName()).reverse().toString())
                .collect(Collectors.toList());
        System.out.println(filterPersonByName);
    }

    @Test
    public void ex8() {
        long result = numbers.stream().filter(i -> i == 4).count();
        assertEquals(2, result);
    }

    @Test
    public void ex9() {
        double result = persons.stream()
                .mapToInt(person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears())
                .average().orElse(0);
        System.out.println("result = " + result);
        int compareDoubleResult = Double.compare(36.25, result);
        assertEquals(0,compareDoubleResult);
    }

    @Test
    public void ex11() {
        long result = words.stream().filter(s -> s.isEmpty()).count();
        System.out.println("result = " + result);
        assertEquals(result, 2);
    }

    @Test
    public void ex12() {
        long result = words.stream().filter(s -> s.length() > 3).count();
        assertEquals(result, 1);
    }

    @Test
    public void ex13() {
        int result = numbers.stream().reduce(0, Integer::sum);
        System.out.println(result);
    }

    @Test
    public void ex14() {
        List<Integer> sortedNumbers = numbers.stream().sorted().collect(Collectors.toList());
        System.out.println("sortedNumbers = " + sortedNumbers);
    }

    @Test
    public void ex15() {
        IntSummaryStatistics statistics = numbers.stream().mapToInt(v -> v).summaryStatistics();
        System.out.println("statistics.getAverage() = " + statistics.getAverage());
        System.out.println("statistics.getMax() = " + statistics.getMax());
        System.out.println("statistics.getMin() = " + statistics.getMin());
        System.out.println("statistics.getCount() = " + statistics.getCount());
        System.out.println("statistics.getSum() = " + statistics.getSum());
    }

}
