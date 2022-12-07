package ru.itis.utils;

import ru.itis.models.Question;

import java.util.*;

public class QuestionStorage {
    private Set<Integer> notAvailableIdSet = new HashSet<>();
    private List<Question> questionArray = initQuestionArray();
    private Random randomGenerator = new Random(questionArray.size());

    public Question getQuestion() {
        int newPosition = randomGenerator.nextInt();
        if (!notAvailableIdSet.add(newPosition)) {
            newPosition = randomGenerator.nextInt();
        }
        return questionArray.get(newPosition);
    }

    private List<Question> initQuestionArray() {
        return Arrays.asList(Question.builder().question("What’s the difference between == and === operators in Kotlin?").answers(new String[]{"yes", "no", "yes yes", "no no"}).correctAnsId(1).points(10).build(), Question.builder().question("-?").answers(new String[]{"--", "---", "----", "-"}).correctAnsId(1).points(10).build());
    }
}
