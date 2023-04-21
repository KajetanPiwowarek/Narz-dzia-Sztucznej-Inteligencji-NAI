public class Data {
    private String language;
    private double[] attributes;

    public Data(String language, String text) {
        this.language = language;
        this.attributes = countLetters(text);
    }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public double[] getAttributes() { return attributes; }

    public void setAttributes(double[] attributes) { this.attributes = attributes; }

    //Obliczamy miare euklidesowa
    private double calcEucDistance(double[] attributes) {
        double normalized = 0;

        for (double attribute : attributes)
            normalized += Math.pow(attribute, 2);

        return Math.sqrt(normalized);
    }

    //Zliczamy ilosc wystąpień liter w tekscie
    private double[] countLetters(String text) {
        double[] attributes = new double[26];

        int j = 0;
        for (int i = 'a'; i <= 'z'; i++) {
            char letter = (char) i;
            double counter = (double) text.chars().filter(character -> character == letter).count();
            attributes[j++] = counter;
        }

        double distance = calcEucDistance(attributes);

        //Normalizujemy czestotliwosc wystapienia liter
        double[] normalizedAttributes = new double[26];
        for (int i = 0; i < attributes.length; i++)
            normalizedAttributes[i] = attributes[i]/distance;

        return normalizedAttributes;
    }
}
