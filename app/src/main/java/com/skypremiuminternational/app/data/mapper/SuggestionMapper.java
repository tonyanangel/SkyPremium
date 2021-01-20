package com.skypremiuminternational.app.data.mapper;

import com.skypremiuminternational.app.data.model.ean.suggestion.SuggestionResponse;
import com.skypremiuminternational.app.data.model.ean.suggestion.SuggestionsItem;
import com.skypremiuminternational.app.domain.models.ean.Suggestion;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SuggestionMapper {

  @Inject
  public SuggestionMapper() {

  }

  public List<Suggestion> map(SuggestionResponse response) {
    List<Suggestion> suggestions = new ArrayList<>();

    if (response.getSuggest() != null && response.getSuggest().getFound() > 0) {
      for (SuggestionsItem item : response.getSuggest().getSuggestions()) {
        suggestions.add(new Suggestion(item.getSuggestion()));
      }
    }
    return suggestions;
  }
}
