package line

class Textmessage {
    String userId
    String message
    String replyToken

    public Textmessage(Map rawjson){
        this.userId = rawjson?.events[0]?.source?.userId
        this.message = rawjson?.events[0]?.message?.text
        this.replyToken = rawjson?.events[0]?.replyToken
    }
}
