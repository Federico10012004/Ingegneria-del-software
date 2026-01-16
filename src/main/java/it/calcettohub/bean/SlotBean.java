package it.calcettohub.bean;

import java.time.LocalTime;

public class SlotBean {
    private LocalTime start;
    private LocalTime end;

    public SlotBean(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
