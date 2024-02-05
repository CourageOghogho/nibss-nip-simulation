package dot.ai.dotnibssmoc.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
@Data
public class QueryParam {

    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;


    private Integer pageNo;
    private Integer pageSize;


    public  Pageable buildPageable() {
        return PageRequest.of(pageNo != null ? pageNo : 0, pageSize != null ? pageSize : 10);
    }


}
