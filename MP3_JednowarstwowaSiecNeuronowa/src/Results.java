public class Results {
    private String type;
    private Double net;
    public Results(String type, Double net){
        this.type = type;
        this.net = net;
    }

    public String getType() {
        return type;
    }

    public Double getNet() {
        return net;
    }
}
