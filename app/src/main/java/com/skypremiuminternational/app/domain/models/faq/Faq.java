package com.skypremiuminternational.app.domain.models.faq;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandi on 10/1/17.
 */

public class Faq extends ExpandableGroup<Answer> {

  String question;
  List<Answer> answers = new ArrayList<>();

  public Faq(String question, List<Answer> answers) {
    super(question, answers);
    this.question = question;
    this.answers = answers;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<Answer> answers) {
    this.answers = answers;
  }
}
