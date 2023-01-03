import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static List<Streamer> streamers = new ArrayList<>(){{
        add(new Streamer("welovegames"));
//        add(new Streamer("welovegames"));
//        add(new Streamer("welovegames"));
//        add(new Streamer("welovegames"));
//        add(new Streamer("welovegames"));
    }};

    public static void main(String[] args) {
        JSONArray data = new JSONArray();
        for (Streamer streamer : streamers) {
            JSONObject streamerObject = new JSONObject();
            JSONArray links = new JSONArray();

            String url = "https://www.twitch.tv/" + streamer.getNickname() + "/videos?filter=clips&range=30d";

            WebDriver driver = new ChromeDriver();
            driver.get(url);
            Document doc = Jsoup.parse(driver.getPageSource());

            Elements elements = doc.select("#offline-channel-main-content > div.Layout-sc-1xcs6mc-0.dvuOmf");
            for (Element element : elements.select("a")) {
                if (element.attributes().hasKey("lines")) {
                    String link = element.attributes().get("href");
                    streamer.getLinks().add("https://twitch.tv" + link.substring(0, link.indexOf("?filter")));

                }
            }

            for (String link : streamer.getLinks()) {
                links.put(link);
            }
            streamerObject.put("nickname", streamer.getNickname());
            streamerObject.put("links", links);
            data.put(streamerObject);
        }

        writeToFile(data.toString());
    }

    private static void writeToFile(String data) {
        File file = new File("/Users/slavebb/IdeaProjects/slavsproject/res/files/streamers.json");
        try {
            Files.writeString(Paths.get(file.toURI()), data, StandardOpenOption.CREATE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("done.");
    }
}
