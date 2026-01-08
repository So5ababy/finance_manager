public class Transactions {
    private final double sum;
    private final String category;
    //private final LocalDateTime dateTime;
    private final String type;

    public Transactions(double sum, String category, String type) {
        this.sum = sum;
        this.category = category;
        this.type = type;
        //this.dateTime = LocalDateTime.now();
    }

    public double getSum() {
        return sum;
    }
    public String getCategory() {
        return category;
    }
    public String getType() {
        return type;
    }
}
