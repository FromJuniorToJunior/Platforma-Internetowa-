package Licencjat2.Licencjat2.supportiveClasses;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


public class PageIndex {
    private String addr;
    private int id;

    public PageIndex(String addr, int id) {
        this.addr = addr;
        this.id = id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
