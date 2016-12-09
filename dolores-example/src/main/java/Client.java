import com.chaos.dolores.client.Dolores;

import java.io.IOException;

/**
 * Created by zcfrank1st on 09/12/2016.
 */
public class Client {
    public static void main(String[] args) {
        try {
            System.out.println(Dolores.call("com.chaos.dolores.api.Api", "helloworld", "chao"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
