import java.util.ArrayList;
import java.util.List;

public class Streamer {
    Streamer(String nickname){
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getLinks() {
        return links;
    }

    private String nickname;
    private List<String> links = new ArrayList<>();
}
