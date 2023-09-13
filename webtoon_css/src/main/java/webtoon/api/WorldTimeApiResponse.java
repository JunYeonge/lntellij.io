package webtoon.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldTimeApiResponse {
    private String timezone;
    private String datetime;
    private String utc_offset;

}

