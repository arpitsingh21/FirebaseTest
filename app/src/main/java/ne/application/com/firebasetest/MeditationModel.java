package ne.application.com.firebasetest;

/**
 * Created by Harikesh on 07/02/2019.
 */
public class MeditationModel {
    String text_medication, imageLink, mediaLink,doingNow;

    public MeditationModel(String text_medication, String imageLink, String mediaLink, String doingNow) {
        this.text_medication = text_medication;
        this.imageLink = imageLink;
        this.mediaLink = mediaLink;
        this.doingNow = doingNow;
    }

    public String getText_medication() {
        return text_medication;
    }

    public void setText_medication(String text_medication) {
        this.text_medication = text_medication;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }

    public String getDoingNow() {
        return doingNow;
    }

    public void setDoingNow(String doingNow) {
        this.doingNow = doingNow;
    }
}
