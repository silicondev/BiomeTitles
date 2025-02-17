package uk.phyre.biomeTitles.Models;

public class TitleInfo {
    public int FadeIn;
    public int Stay;
    public int FadeOut;

    public TitleInfo(int fadeIn, int stay, int fadeOut) {
        FadeIn = fadeIn;
        Stay = stay;
        FadeOut = fadeOut;
    }

    public TitleInfo() {}
}
