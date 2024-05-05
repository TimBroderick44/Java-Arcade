package trivia;

import java.util.List;

public class TriviaQuestions {
    private static final List<Pair> questions = List.of(
        new Pair("The capital of France is Paris.", "t"),
        new Pair("Cats have nine lives.", "f"),
        new Pair("Bananas are berries.", "t"),
        new Pair("The Great Wall of China is visible from space.", "f"),
        new Pair("Albert Einstein was awarded the Nobel Prize in Physics in 1921.", "t"),
        new Pair("Venus is the hottest planet in our solar system.", "t"),
        new Pair("Humans can distinguish between over a trillion different smells.", "t"),
        new Pair("Adult humans have fewer bones than babies do.", "t"),
        new Pair("Goldfish only have a memory of three seconds.", "f"),
        new Pair("There are more cells of bacteria in your body than there are human cells.", "t"),
        new Pair("The Earth is flat.", "f"),
        new Pair("The currency of Japan is the Yuan.", "f"),
        new Pair("The Statue of Liberty was a gift from France.", "t"),
        new Pair("The Great Wall of China is longer than the distance between London and Beijing.", "t"),
        new Pair("There are five different blood groups.", "f"),
        new Pair("The human skeleton is made up of less than 100 bones.", "f"),
        new Pair("The Atlantic Ocean is the biggest ocean on Earth.", "f"),
        new Pair("The human body has four lungs.", "f"),
        new Pair("Cats are lactose intolerant.", "t"),
        new Pair("The unicorn is the national animal of Scotland.", "t"),
        new Pair("The first tea bags were made of silk.", "t"),
        new Pair("Australia is wider than the moon.", "t"),
        new Pair("The first oranges weren't orange.", "t"),
        new Pair("The first computer was invented in the 20th century.", "f"),
        new Pair("Water boils at 90 degrees Celsius on Mount Everest.", "f"),
        new Pair("Sharks are mammals.", "f"),
        new Pair("The human eye can see more shades of green than any other color.", "t"),
        new Pair("The Eiffel Tower was originally intended for Barcelona.", "t"),
        new Pair("Lightning strikes the same place quite often.", "t"),
        new Pair("Dolphins sleep with one eye open.", "t"),
        new Pair("The king of hearts is the only king without a mustache in a standard playing card deck.", "t"),
        new Pair("A group of crows is known as a murder.", "t"),
        new Pair("Octopuses have three hearts.", "t"),
        new Pair("The Olympic Games were invented in France.", "f"),
        new Pair("Honey never spoils.", "t"),
        new Pair("The longest time between two twins being born is 87 days.", "t"),
        new Pair("Vatican City is the smallest country in the world.", "t"),
        new Pair("Peanuts are not nuts, they are legumes.", "t"),
        new Pair("Russia has a larger surface area than Pluto.", "t"),
        new Pair("An octopus has five arms.", "f"),
        new Pair("All the planets in the solar system could fit between the Earth and the moon.", "t"),
        new Pair("The heart of a shrimp is located in its head.", "t"),
        new Pair("A banana is a fruit.", "f"),
        new Pair("The inventor of the light bulb, Thomas Edison, was afraid of the dark.", "f"),
        new Pair("A day on Venus is longer than a year on Venus.", "t"),
        new Pair("Camels store water in their humps.", "f"),
        new Pair("A rhinoceros' horn is made of hair.", "t"),
        new Pair("The total weight of ants on earth once equaled the total weight of people.", "t"),
        new Pair("The fingerprints of koala bears are virtually indistinguishable from those of humans.", "t"),
        new Pair("The shortest war in history was between England and Zanzibar on August 27, 1896, and lasted 38 minutes.", "t")
    );

    public static List<Pair> getQuestions() {
        return questions;
    }

    public static class Pair {
        String question;
        String answer;

        public Pair(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }
    }
}
