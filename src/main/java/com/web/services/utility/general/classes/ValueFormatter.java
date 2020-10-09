package com.web.services.utility.general.classes;

import com.web.services.Setting;
import com.web.services.utility.general.interfaces.Formatter;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValueFormatter implements Formatter {

    private StringBuilder details = new StringBuilder();
    private Map<String, Object> values = new LinkedHashMap<>();
    private int spaces = Setting.AOP_BLANK_SPACES;

    public ValueFormatter() {}

    public ValueFormatter(int spaces) {
        if (spaces > 0) {
            this.spaces = spaces;
        }
    }

    @Override
    public Formatter add(String key, Object value) {
        if ((value != null) && !(this.values.containsKey(key))) {
            this.values.put(key, value.toString());
        }
        return this;
    }

    @Override
    public String format() {
        int last = this.values.size();
        int current = 1;
        for (String key : this.values.keySet()) {
            this.details.append("[").append(key).append(": ").append(this.values.get(key)).append("]");
            if (current != last) {
                this.details.append(" ".repeat(this.spaces));
            }
            current++;
        }
        return this.details.toString();
    }
}
