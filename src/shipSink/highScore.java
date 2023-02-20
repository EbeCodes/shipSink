public class highScore implements Comparable<Object>{
    private String name;
    private int score;

    public highScore(String n, int s) {
        this.name = n;
        this.score = s;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }   

    @Override
    public int compareTo(Object o) {
        highScore s = (highScore) o;
        return s.score - this.score; //Descending sorting
    }
}