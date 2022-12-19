package neriess.WebQuizEngine.model;

import java.util.ArrayList;
import java.util.List;

public class Answer {

    List<Integer> answer;

    public List<Integer> getAnswer() {
        if (answer == null) {
            return new ArrayList<>(){};
        }
        return answer;
    }

    public void setAnswers(List<Integer> answer) {
        this.answer = answer;
    }
}
