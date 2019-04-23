package com.example.finalproject;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

/** A class for storing data related to one particular question. */
public class QuestionData {

    /** Minimum number of answers. */
    private final static int MIN_ANSWERS = 2;

    /** The question being asked. */
    private String question;

    /** The available answers to the question. */
    private List<String> possibleAnswers = new ArrayList<>();

    /** The user's choices of answers to the question. */
    private List<String> chosenAnswers = new ArrayList<>();

    /** The frequencies of each answer. */
    private List<Frequency> frequencies = new ArrayList<>();

    /** Empty constructor. */
    public QuestionData() {
        setFrequencies();
    }

    /** Constructor which sets questions and answers.
     *
     * @param setQuestion what the question should be
     * @param setAnswers what the answers should be
     */
    public QuestionData(final String setQuestion, final List<String> setAnswers) {
        if (setQuestion == null || setAnswers == null || setAnswers.contains(null)) {
            throw new IllegalArgumentException("inputs should not be null");
        }
        if (setAnswers.size() < MIN_ANSWERS) {
            throw new IllegalArgumentException("There should be at least " + MIN_ANSWERS + " answers");
        }
        question = setQuestion;
        possibleAnswers = setAnswers;
        setFrequencies();
    }

    /** Constructor which sets questions and answers.
     *
     * @param setQuestion what the question should be
     */
    public QuestionData(final String setQuestion) {
        if (setQuestion == null) {
            throw new IllegalArgumentException("input should not be null");
        }
        question = setQuestion;
        setFrequencies();
    }

    /** Set the answers to the question.
     *
     * @param setAnswers the answers
     */
    public void setAnswers(final List<String> setAnswers) {
        if (setAnswers == null || setAnswers.contains(null)) {
            throw new IllegalArgumentException("inputs should not be null");
        }
        if (setAnswers.size() < MIN_ANSWERS) {
            throw new IllegalArgumentException("There should be at least " + MIN_ANSWERS + " answers");
        }
        possibleAnswers = setAnswers;
        chosenAnswers = new ArrayList<>();
        setFrequencies();
    }

    /** Add a response.
     *
     * @param answer the response to add
     */
    public void addAnswer(final String answer) {
        if (!possibleAnswers.contains(answer)) {
            throw new IllegalArgumentException(answer + " is not a valid answer");
        }
        chosenAnswers.add(answer);
        for (Frequency frequency : frequencies) {
            if (frequency.getAnswer().equals(answer)) {
                frequency.bumpFrequency();
            }
        }
    }

    /** Public getter for question.
     *
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /** Public getter for answers.
     *
     * @return answers
     */
    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    /** Do a chi-squared test.
     *
     * @return the p-value
     */
    public double chiSquaredTest() {
        double expectedFrequency = averageFrequency();
        double chiSquaredStat = 0;
        for (Frequency frequency : frequencies) {
            chiSquaredStat += Math.pow((double) frequency.getFrequency() - expectedFrequency, 2);
        }
        chiSquaredStat /= expectedFrequency;
        ChiSquaredDistribution distribution = new ChiSquaredDistribution(possibleAnswers.size() - 1);
        return 1 - distribution.cumulativeProbability(chiSquaredStat);
    }

    /** Calculate the average frequency.
     *
     * @return the average frequency
     */
    private double averageFrequency() {
        double sum = 0;
        for (Frequency frequency : frequencies) {
            sum += frequency.getFrequency();
        }
        return sum / (double) frequencies.size();
    }

    /** Recalculate frequencies.
     *
     */
    private void setFrequencies() {
        frequencies = new ArrayList<>(possibleAnswers.size());
        for (int i = 0; i < possibleAnswers.size(); i++) {
            frequencies.add(new Frequency(possibleAnswers.get(i)));
        }
    }

    /** Public getter for frequencies.
     *
     * @return frequencies
     */
    public List<Frequency> getFrequencies() {
        return frequencies;
    }

    /** Interior class that stores answers and their frequencies.
     *
     */
    public class Frequency {
        /** The answer. */
        private String answer;

        /** The answer's frequency. */
        private int frequency;

        /** Constructor.
         *
         * @param setAnswer the answer
         */
        Frequency(final String setAnswer) {
            if (!possibleAnswers.contains(setAnswer)) {
                throw new IllegalArgumentException(setAnswer + " is not a valid answer");
            }
            answer = setAnswer;
            setFrequency();
        }

        /** Set the frequency for this answer. */
        void setFrequency() {
            frequency = 0;
            if (chosenAnswers.contains(answer)) {
                for (String chosenAnswer : chosenAnswers) {
                    if (chosenAnswer.equals(answer)) {
                        frequency++;
                    }
                }
            }
        }

        /** Increment the frequency for this answer by 1. */
        void bumpFrequency() {
            frequency++;
        }

        /** Public getter for answer.
         *
         * @return answer
         */
        String getAnswer() {
            return answer;
        }

        /** Public getter for frequency.
         *
         * @return frequency
         */
        int getFrequency() {
            return frequency;
        }
    }
}
