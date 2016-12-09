import com.chaos.dolores.server.Arnold;

import java.io.IOException;

/**
 * Created by zcfrank1st on 09/12/2016.
 */
public class Server {
    public static void main(String[] args) {
        try {
            Arnold.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
