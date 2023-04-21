public class Tools {
    public double calcEucDistance(Point a, Point b){
        double distance = 0;
        for(int i = 0; i < a.getVectors().size(); i++){
            distance += Math.pow((a.getVectors().get(i) - b.getVectors().get(i)),2);
        }
        return Math.sqrt(distance);
    }
    public Point randomPoint(Data data){
        Point centroid = new Point();
        int randomNumber = (int)(Math.random() * data.dataPoints.size());
        for(int i = 0; i < data.dataPoints.size(); i++){
            if(i == randomNumber) centroid = data.dataPoints.get(i);
        }
        return centroid;
    }
}
