package brugerautorisation.data;

public class Spiller {

    String studienr;
    String score;
    String password;

    public Spiller(){

    }

    public Spiller(String navn, String password, String score){
        this.studienr = navn;
        this.password = password;
        this.score = score;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudienr(){
        return studienr;
    }

    public String getScore(){
        return score;
    }

    public void setStudienr(String studienr) {
        this.studienr = studienr;
    }

    public void setScore(String score) {
        this.score = score;
    }
}