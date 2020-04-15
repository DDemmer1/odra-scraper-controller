package de.demmer.dennis.odrascrapercontroller.payload;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleListRequest {

    List<Integer> articleIds = new ArrayList<>();
}
