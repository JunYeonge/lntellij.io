package webtoon.controller.webtoon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webtoon.api.TimeController;
import webtoon.dto.webtoon.WebtoonDto;
import webtoon.service.webtoon.SearchService;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class SearchController {
    TimeController timeController = new TimeController();
    private final SearchService searchService;

    @GetMapping(value = "/main/search")
    public String search(@RequestParam("searchQuery") String searchQuery, @RequestParam("searchType") String searchType, Model model) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            model.addAttribute("errorMessage", "검색어를 입력해주세요.");
            return "main/search";
        }
        model.addAttribute("searchWord", searchQuery);

        if ("all".equals(searchType)) {
            List<WebtoonDto> searchAll = searchService.searchAll(searchQuery);

            if (searchAll.isEmpty()) {
                model.addAttribute("errorMessage", "검색한 결과가 없습니다.");
            }
            if (searchQuery.trim().isEmpty()) {
                model.addAttribute("errorMessage", "검색어를 입력해주세요.");
            }
            model.addAttribute("searchType", "전체 검색 결과 입니다.");
            model.addAttribute("searchAll", searchAll);
            return "main/search";
        }

        if ("title".equals(searchType)) {
            List<WebtoonDto> searchTitle = searchService.searchTitle(searchQuery);
            if (searchTitle.isEmpty()) {
                model.addAttribute("errorMessage", "검색한 이름의 웹툰이 없습니다.");
            }
            if (searchQuery.trim().isEmpty()) {
                model.addAttribute("errorMessage", "검색어를 입력해주세요.");
            }
            model.addAttribute("searchType", "웹툰명 검색 결과 입니다.");
            model.addAttribute("searchTitle", searchTitle);

            return "main/search";
        }

        if ("writer".equals(searchType)) {
            List<WebtoonDto> searchWriter = searchService.searchWriter(searchQuery);
            if (searchWriter.isEmpty()) {
                model.addAttribute("errorMessage", "검색한 이름의 작가가 없습니다.");
            }
            if (searchQuery.trim().isEmpty()) {
                model.addAttribute("errorMessage", "검색어를 입력해주세요.");
            }
            model.addAttribute("searchType", "작가 검색 결과 입니다.");
            model.addAttribute("searchWriter", searchWriter);

            return "main/search";
        }


        if ("genre".equals(searchType)) {
            List<WebtoonDto> searchGenre = searchService.searchGenre(searchQuery);
            if (searchGenre.isEmpty()) {
                model.addAttribute("errorMessage", "검색한 장르에 해당하는 웹툰이 없습니다.");
            }
            if (searchQuery.trim().isEmpty()) {
                model.addAttribute("errorMessage", "검색어를 입력해주세요.");
            }
            model.addAttribute("searchType", "장르 검색 결과 입니다.");
            model.addAttribute("searchGenre", searchGenre);

            return "main/search";
        }


        return "main/search"; // 검색 결과를 보여줄 뷰 이름
    }
}
