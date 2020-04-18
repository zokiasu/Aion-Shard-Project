import java.util.ArrayList;
import java.util.List;

public class Strings {
    private List<String> stringList = new ArrayList<String>();

    public Strings () {}

    public Strings(List<String> stringList) {
        this.stringList = stringList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public void addString(String string) {
        stringList.add(string);
    }
}
