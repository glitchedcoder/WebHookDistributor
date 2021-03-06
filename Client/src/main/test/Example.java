import org.slf4j.LoggerFactory;
import stream.flarebot.webhook_distributor.WebHookDistributor;
import stream.flarebot.webhook_distributor.WebHookDistributorBuilder;
import stream.flarebot.webhook_distributor.WebHookListener;
import stream.flarebot.webhook_distributor.events.WebHookReceiveEvent;

public class Example {

    public static void main(String[] args) {
        WebHookDistributor distributor = new WebHookDistributorBuilder("https://cool-webhooks.flarebot.stream", "example", 8181)
                .addEventListener(new Listener())
                .setStartingRetryTime(500)
                .setMaxConnectionAttempts(5)
                .build();
        distributor.start();
    }

    private static class Listener extends WebHookListener {

        public void onWebHookReceive(WebHookReceiveEvent e){
            if (e.getAuthorization() != null && e.getAuthorization().equals("secretKey1337")) {
                LoggerFactory.getLogger(Example.class).info(String.format("Received webhook from %s, data: %s",
                        e.getSender().toString(), e.getPayload().toString()));
            } else
                LoggerFactory.getLogger(Example.class)
                        .warn(String.format("Received webhook with invalid authorization from %s pretending to be %s",
                                e.getIP(), e.getSender().toString()));
        }
    }
}
