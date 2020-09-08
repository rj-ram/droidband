package in.droidparkz.droidband;

public class PairingselectionItem {

    private String name;
    private String macid;

    public PairingselectionItem(String name, String macid) {

        this.name = name;
        this.macid = macid;
    }

    public String getName() {
        return name;
    }

    public String getMacid() {
        return macid;
    }

}