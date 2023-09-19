package webtoon.service.webtoon;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webtoon.dto.webtoon.WebtoonDto;
import webtoon.entity.webtoon.Webtoon;
import webtoon.repository.webtoon.WebtoonRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
    private final WebtoonRepository webtoonRepository;
    private final ModelMapper modelMapper; // ModelMapper 주입

    public List<WebtoonDto> searchTitle(String searchQuery) {
        List<Webtoon> webtoonList = webtoonRepository.findByTitle(searchQuery);

        return webtoonList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<WebtoonDto> searchWriter(String searchQuery) {
        List<Webtoon> webtoonList = webtoonRepository.findByUser_id(searchQuery);

        return webtoonList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<WebtoonDto> searchGenre(String searchQuery) {
        List<Webtoon> webtoonList = webtoonRepository.findByGenre(searchQuery);

        return webtoonList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<WebtoonDto> searchAll(String searchQuery) {
        List<Webtoon> webtoonList = webtoonRepository.findByAll(searchQuery);

        return webtoonList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private WebtoonDto convertToDto(Webtoon webtoon) {
        return modelMapper.map(webtoon, WebtoonDto.class);
    }
}
