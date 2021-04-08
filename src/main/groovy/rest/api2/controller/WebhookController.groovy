package rest.api2.controller

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.model.response.BotApiResponse
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.http.annotation.*
import line.Textmessage

import java.util.concurrent.ExecutionException

@Controller("/webhook")
@ConfigurationProperties("lineApi")
class WebhookController {

    private String channelToken;


    @Post("/")
    public void handleDefaultMessageEvent(Map event) {
        final String type = event?.events[0]?.type
        if (type!="message") return
        final String messageType = event?.events[0]?.message?.type
        if (messageType!="text") return
        Textmessage request = new Textmessage(event)

        final LineMessagingClient client = LineMessagingClient.builder(this.channelToken).build()

        final TextMessage textMessage = new TextMessage("幹你娘");
        final PushMessage pushMessage = new PushMessage(
                request.userId,
                textMessage);

        final BotApiResponse botApiResponse;
        try {
            botApiResponse = client.pushMessage(pushMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }
        System.printf(botApiResponse)

    }

}