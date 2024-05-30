package api.open_api_pratice.apiget;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Exam {
    private Response response;

    @Getter
    @Setter
    @ToString
    public static class Response {
        private Header header;
        private Body body;
    }

    @Getter
    @Setter
    @ToString
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    @ToString
    public static class Body {
        private String dataType;
        private Items items;
        private int pageNo;
        private int numOfRows;
        private int totalCount;
    }

    @Getter
    @Setter
    @ToString
    public static class Items {
        private List<Item> item;
    }

    @Getter
    @Setter
    @ToString
    public static class Item {
        private String baseDate;
        private String baseTime;
        private String category;
        private Integer nx;
        private Integer ny;
        private String obsrValue;
    }
}
